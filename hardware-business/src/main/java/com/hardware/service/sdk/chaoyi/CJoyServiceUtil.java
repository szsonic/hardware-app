package com.hardware.service.sdk.chaoyi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hardware.business.client.HttpRequestUtils;
import com.hardware.business.conf.CJoyConf;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.domain.DeviceRechargeRecordRes;
import com.hardware.business.domain.DeviceRechargeRecordResponse;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.utils.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.date.DatetimeUtils;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import org.iframework.support.spring.memcached.MemcachedManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CJoyServiceUtil {
	
	private static final String URL = CJoyConf.URL;
	private static final String CLINET_ID = CJoyConf.CLINET_ID;
	private static final String MEM_KEY_TOKEN = "CJOY_ACCESS_TOKEN";
	
	/*
	 * 获取超仪对外接口访问令牌
	 * 有效期为10分钟
	 */
	private static String getAccessToken(MemcachedManager memcachedManager){
		String token = getTokenFromCache(memcachedManager);
		
		if(ValidatorUtils.isEmpty(token)){
			//重新生成 token
			token = RSACoder.genAccessToken(CLINET_ID);
			
			if(ValidatorUtils.isNotEmpty(token)
					&& ValidatorUtils.isNotEmpty(memcachedManager)){
				memcachedManager.cacheObject(MEM_KEY_TOKEN, token, 60 * 9);
			}
		}
		
		return token;
	}
	
	//从缓存里拿 token
	private static String getTokenFromCache(MemcachedManager memcachedManager){
		try{
			String token = memcachedManager.loadObject(MEM_KEY_TOKEN);
			return token;
		}catch(Exception e){
			Log.e(e.getMessage(), e);
		}
		
		return null;
	}
	
	private static void clearCacheInMem(MemcachedManager memcachedManager){
		memcachedManager.cacheObject(MEM_KEY_TOKEN, "", 60 * 1);
	}
	
	/* 
	 * 控制电表开闸 / 关闸
	 * @param meterNo: 电表出厂编号
	 * @param action : 1 开闸， 0 关闸
	 */
	public static AmmeterResult control(MemcachedManager memcachedManager,
                                        String meterNo, String action){
		AmmeterResult result = new AmmeterResult();
		result.setCode("1"); //默认失败
		try{
			//获取token
			String token = getAccessToken(memcachedManager);
			if(ValidatorUtils.isEmpty(token)){
				result.setMessage("获取 token 失败");
				return result;
			}
			
			if("on".equals(action)){
				action = "1";
			}else if("off".equals(action)){
				action = "0";
			}
			
			if(!"0".equals(action)
					&& !"1".equals(action)){
				result.setMessage("action 参数类型不正确");
				return result;
			}
			
			Map<String, Object> params = new HashMap<>();
			params.put("meterNo", meterNo);
			params.put("action", action);
			params.put("access_token", token);
			
			Log.i(JSON.toJSONString(params));
			
		    String apiUrl = URL + "mbusControlByMeterNo.do";
			String responseStr = HttpRequestUtils.post(apiUrl, params);
			
			Log.i("response of control() : " + responseStr);
			
			JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(responseStr);
			if(ValidatorUtils.isNotEmpty(jsonObject)){
				//{"status":1,"data":null}
				//status: 0 失败, 1 成功, 2 认证错误
				String code = jsonObject.getString("status");
				String message = jsonObject.getString("data");
				transferResult(memcachedManager, result, code, message);
			}
			
		}catch(Exception e){
			result.setMessage("调用接口出错");
			Log.i("调用接口出错");
			Log.e(e.getMessage(), e);
		}
		
		return result;
	}
	
	/*
	 * 根据表号清空余额
     * 请求地址：http://ip:port/path/joy/clearBalanceByMeterNo.do
     * @param meterNo : 电表出厂编号
	 */
	public static AmmeterResult clearBalanceByMeterNo(MemcachedManager memcachedManager,
                                                      String meterNo) throws Exception{
		AmmeterResult result = new AmmeterResult();
		result.setCode("1"); //默认失败
		
		try{
			String token = getAccessToken(memcachedManager);
			if(ValidatorUtils.isEmpty(token)){
				result.setMessage("获取 token 失败");
				return result;
			}
			
			Map<String, Object> params = new HashMap<>();
			params.put("meterNo", meterNo);
			params.put("access_token", token);
			
			Log.i(JSON.toJSONString(params));
			
		    String apiUrl = URL + "clearBalanceByMeterNo.do";
			String responseStr = HttpRequestUtils.post(apiUrl, params);
			
			Log.i("response of clearBalanceByMeterNo() : " + responseStr);
			
			JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(responseStr);
			if(ValidatorUtils.isNotEmpty(jsonObject)){
				//{"status":1,"data":{"meterNo":"201603117852","money":100,"balance":0}}
				//status：0 失败, 1 成功, 2 认证错误, 3 查询到多个表计，拒绝退费, 4 用户不存在
				//money: 退费金额; balance: 账户余额
				String code = jsonObject.getString("status");
				String message = jsonObject.getString("data");
				transferResult(memcachedManager, result, code, message);
				if(ValidatorUtils.isEquals("3", code)){
					result.setCode("1");
					result.setMessage("查询到多个表计，拒绝退费");
				}else if(ValidatorUtils.isEquals("4", code)){
					result.setCode("1");
					result.setMessage("用户不存在");
				}
			}else{
				throw new HardwareSdkException("电表余额清空失败");
			}
			
			return result;
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			throw new HardwareSdkException("电表余额清空失败");
		}
	}
	
	
	/* 
	 * 获取电表状态
	 * @param meterNo: 电表出厂编号
	 */
	public static Map<String, Object> status(MemcachedManager memcachedManager, 
			String meterNo){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "false");
		try{
			//获取token
			String token = getAccessToken(memcachedManager);
			if(ValidatorUtils.isEmpty(token)){
				result.put("message", "获取 token 失败");
				return result;
			}
			
			Map<String, Object> params = new HashMap<>();
			params.put("meterNo", meterNo);
			params.put("access_token", token);
			
			Log.i(JSON.toJSONString(params));
			
		    String apiUrl = URL + "queryMeterStatus.do";
			String responseStr = HttpRequestUtils.post(apiUrl, params);
			
			Log.i("response of status() : " + responseStr);
			
			JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(responseStr);
			if(ValidatorUtils.isNotEmpty(jsonObject)){
				//{"status":1,"data":{"net_state":"0","elec_state":"0"}}
				//status: 0 失败, 1 成功, 2 认证错误
				//net_state：0 在线, 1 离线
				//elec_state：0 通电, 1 断电
				
				String code = jsonObject.getString("status");
				//status：0 失败, 1 成功, 2 认证错误
				if(ValidatorUtils.isEquals("1", code)){
					result.put("success", "true");
					JSONObject statusInfo = jsonObject.getJSONObject("data");
					String netState = statusInfo.getString("net_state");//在线状态
					String elecState = statusInfo.getString("elec_state");//通电状态
					if(ValidatorUtils.isEquals("0", netState)){
						result.put("wifiOnlineStatus","on");
					}else{
						result.put("wifiOnlineStatus","off");
					}
					if(ValidatorUtils.isEquals("0", elecState)){
						result.put("electricDoorSwitchStatus","on");
					}else{
						result.put("electricDoorSwitchStatus","off");
					}
		            
				}else if(ValidatorUtils.isEquals("0", code)){
					result.put("message", "获取电表状态失败");
				}else if(ValidatorUtils.isEquals("2", code)){
					result.put("message", "认证失败!");
					
					clearCacheInMem(memcachedManager);
					Log.i("认证失败!");
				}
			}
			
		}catch(Exception e){
			result.put("message", "调用接口出错");
			Log.i("调用接口出错");
			Log.e(e.getMessage(), e);
		}
		
		return result;
	}
	
	/* 
	 * 获取区间内电表的每天用电量
	 * @param meterNo: 电表出厂编号
	 * @param startDate 查询开始时间
 	 * @param endDate 查询结束时间
	 */
	public static Map<String, Object> findReadInfoByDate(MemcachedManager memcachedManager, 
			String meterNo, String startDate, String endDate){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", "false");
		try{
			//获取token
			String token = getAccessToken(memcachedManager);
			if(ValidatorUtils.isEmpty(token)){
				result.put("msg", "获取 token 失败");
				return result;
			}
			
			Map<String, Object> params = new HashMap<>();
			params.put("meterNo", meterNo);
			params.put("start_time", startDate);
			params.put("end_time", endDate);
			params.put("access_token", token);
			
			Log.i(JSON.toJSONString(params));
			
		    String apiUrl = URL + "queryPowerCostPerDay.do";
			String responseStr = HttpRequestUtils.post(apiUrl, params);
			
			Log.i("response of findReadInfoByDate() : " + responseStr);
			
			if(ValidatorUtils.isNotEmpty(responseStr)){
				JSONObject jsonObject = JSONObject.parseObject(responseStr);
				//{"status":1,"data":{"onlineSyncCode":"12345","meterNo":"123456",
	            //   "datas":[{"data":"100","date":"2017-07-18"}]}}
				//status: 0 失败, 1 成功, 2 认证错误
				String code = jsonObject.getString("status");
				
				if(ValidatorUtils.isEquals("1", code)){
					result.put("success", "true");
					JSONObject powerCostInfo = jsonObject.getJSONObject("data");
					JSONArray array = powerCostInfo.getJSONArray("datas");
					String meterNoResponse = powerCostInfo.getString("meterNo");
					if(ValidatorUtils.isNotEmpty(array)
							&& ValidatorUtils.isEquals(meterNo, meterNoResponse)){
						List<Map<String, Object>> datas = Lists.newArrayList();
						for (Object object : array) {
							Map<String, Object> d = Maps.newHashMap();
							JSONObject o = (JSONObject) object;
							d.put("Allpower", o.getString("data")); //当天耗电量
							d.put("Lastpower", o.getString("balance") == null ? "" : o.getString("balance")); //最后电量
							d.put("Date", o.getString("date")); //yyyy-MM-dd
							datas.add(d);
						}
						result.put("data", datas);
					}
				}else if(ValidatorUtils.isEquals("0", code)){
					result.put("msg", "获取用电信息失败");
				}else if(ValidatorUtils.isEquals("2", code)){
					result.put("msg", "认证失败!");
					
					clearCacheInMem(memcachedManager);
					Log.i("认证失败!");
				}
			}
			
		}catch(Exception e){
			result.put("msg", "调用接口出错");
			Log.i("调用接口出错");
			Log.e(e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * 电量充值
	 * @param meterNo 设备id
	 * @param ammout 充值电量
	 */
	public static void chargeAmount(MemcachedManager memcachedManager,
			String meterNo, Float ammout) {
		//获取token
		String token = getAccessToken(memcachedManager);
		if(ValidatorUtils.isEmpty(token)){
			throw new HardwareSdkException("超仪电表电量充值失败(获取token失败)");
		}
		
		try{
			Map<String, Object> params = new HashMap<>();
			params.put("meterNo", meterNo);
			params.put("access_token", token);
			params.put("money", ammout);
			
			Log.i(JSON.toJSONString(params));
			
		    String apiUrl = URL + "rechargeByMeterNo.do";
			String responseStr = HttpRequestUtils.post(apiUrl, params);
			
			Log.i("response of chargeAmount() : " + responseStr);
			
			if(ValidatorUtils.isNotEmpty(responseStr)){
				JSONObject jsonObject = JSONObject.parseObject(responseStr);
				//{"status":1,"data":{"meterNo":"201603117852","money":100,"balance":100}}
				//status: 0 失败, 1 成功, 2 认证错误
				String code = jsonObject.getString("status");
				String message = "充值失败";
				AmmeterResult result = new AmmeterResult();
				result.setCode("1");
				transferResult(memcachedManager, result, code, message);
				
				if(ValidatorUtils.isNotEquals("0", result.getCode())){
					throw new HardwareSdkException("超仪电表电量充值失败");
				}
			}else{
				throw new HardwareSdkException("超仪电表电量充值失败");
			}
		}catch(HardwareSdkException ex){
			throw ex;
		}catch(Exception e){
			Log.i("调用接口出错");
			Log.e(e.getMessage(), e);
			throw new HardwareSdkException("超仪电表电量充值失败(调用接口出错)");
		}
	}
	
	/**
	 * 查询电表电费充值记录
	 *
	 * @param devId
	 * @param bdate
	 * @param edate
	 * @return
	 * @throws Exception
	 */
	public static DeviceRechargeRecordResponse getDeviceRechargeRecord(MemcachedManager memcachedManager, String meterNo,
                                                                       String bdate, String edate) throws Exception {
		DeviceRechargeRecordResponse dev = new DeviceRechargeRecordResponse();
		DeviceRechargeRecordRes deviceRechargeRecordRes = new DeviceRechargeRecordRes();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		deviceRechargeRecordRes.setData(list);
		dev.setRes(deviceRechargeRecordRes);
		dev.setCode("1");
		
		String token = getAccessToken(memcachedManager);
		if(ValidatorUtils.isEmpty(token)){
			deviceRechargeRecordRes.setMsg("获取 token 失败");
			return dev;
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("meterNo", meterNo);
		params.put("access_token", token);
		params.put("start_time", bdate);
		params.put("end_time", edate);
		
		Log.i(JSON.toJSONString(params));
		
	    String apiUrl = URL + "queryRechargeInfo.do";
		String responseStr = HttpRequestUtils.post(apiUrl, params);
		
		Log.i("response of getDeviceRechargeRecord() : " + responseStr);
		
		if (StringUtils.isNotBlank(responseStr)) {
			//JSONObject data = JSON.parseObject(result, JSONObject.class);
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			
			//{"status":1,"data":{"onlineSyncCode":"12345","meterNo":"123456",
			     //"datas":[{"data":"100","date":"2017-07-18"}]}}
			//status: 0 失败, 1 成功, 2 认证错误
			String code = jsonObject.getString("status");
			
			if(ValidatorUtils.isEquals("1", code)){
				dev.setCode("0");
				deviceRechargeRecordRes.setMsg("操作成功");
				
				JSONObject chargeInfo = jsonObject.getJSONObject("data");
				JSONArray array = chargeInfo.getJSONArray("datas");
				for (Object object : array) {
					Map<String, String> map = new HashMap<String, String>();
					JSONObject o = (JSONObject) object;
                    //电表充值电量
					map.put("amount",  DataUtil.getValue(o.getString("data")).toString());
					//map.put("time", o.getString("date"));
					//2017-07-19 16:54:08.0 -> 2017-07-19T16:54:08
					map.put("time", getFormatTime(o.getString("date")));
					list.add(map);
				}
				
			}else if(ValidatorUtils.isEquals("2", code)){
				dev.setCode("1");
				deviceRechargeRecordRes.setMsg("认证错误");
			}else{
				deviceRechargeRecordRes.setMsg("查询电表充值记录失败!");
			}

		}
		
		return dev;
	}
	
	/*
	 * 获取电表剩余电量
	 * @param meterNo 设备id
	 * @param amount 充值度数
	 */
	public static Map<String,String> getSurplusElectricity(MemcachedManager memcachedManager,
			String meterNo) {
		
		Map<String,String> result = new HashMap<String, String>();
		result.put("success", "false");
		//获取token
		String token = getAccessToken(memcachedManager);
		if(ValidatorUtils.isEmpty(token)){
			result.put("msg", "获取token失败");
			return result;
		}
		
		try{
			Map<String, Object> params = new HashMap<>();
			params.put("meterNo", meterNo);
			params.put("access_token", token);
			
			Log.i(JSON.toJSONString(params));
			
		    String apiUrl = URL + "queryMeterBalance.do";
			String responseStr = HttpRequestUtils.post(apiUrl, params);
			
			Log.i("response of getSurplusElectricity() : " + responseStr);
			
			String errorMsg = null;
			if(ValidatorUtils.isNotEmpty(responseStr)){
				JSONObject jsonObject = JSONObject.parseObject(responseStr);
				//{"status":1,"data":{"onlineSyncCode":"12345","meterNo":"123456",
				   //"data":"100","date":"2017-07-18"}}
				//status: 0 失败, 1 成功, 2 认证错误
				String code = jsonObject.getString("status");
				
				if(ValidatorUtils.isEquals("1", code)){
					JSONObject elecInfo = jsonObject.getJSONObject("data");
					String surplus = elecInfo.getString("data");
					if(ValidatorUtils.isNotEmpty(surplus)){
						result.put("success", "true");
						result.put("surplus", surplus);
					}
					
				}else if(ValidatorUtils.isEquals("0", code)){
					errorMsg = "获取剩余电量失败";
				}else if(ValidatorUtils.isEquals("2", code)){
					errorMsg = "认证失败!";
					
					clearCacheInMem(memcachedManager);
					Log.i("认证失败!");
				}
				
				if(ValidatorUtils.isNotEmpty(errorMsg)){
					result.put("msg", errorMsg);
				}
			}
			
			return result;
		}catch(Exception e){
			Log.i("调用接口出错");
			Log.e(e.getMessage(), e);
			throw new HardwareSdkException("超仪电表获取剩余电量(调用接口出错)");
		}
	}
	
	private static void transferResult(MemcachedManager memcachedManager,
                                       AmmeterResult result, String code, String message){
		//status：0 失败, 1 成功, 2 认证错误
		if(ValidatorUtils.isEquals("1", code)){
			result.setCode("0");
		}else if(ValidatorUtils.isEquals("0", code)){
			result.setCode("1");
			if(ValidatorUtils.isNotEmpty(message)){
				result.setMessage(message);
			}
		}else if(ValidatorUtils.isEquals("2", code)){
			result.setCode("1");
			result.setMessage("认证失败!");
			
			clearCacheInMem(memcachedManager);
			Log.i("认证失败!");
		}
	}
	
	//yyMMdd -> yyyy-MM-dd
	@SuppressWarnings("unused")
	private static String dateStrTransfer(String dateStr,
			String oldFormat, String newFormat){
		return DatetimeUtils.format(DatetimeUtils.string2date(dateStr, oldFormat), newFormat);
	}
	
	//2017-07-19 05:03:43.0 -> 2017-07-19T05:03:43
	private static String getFormatTime(String oldTime){
		int index = oldTime.indexOf(".");
		if(index > 0){
			oldTime = oldTime.substring(0, index);
		}
		
		return oldTime.replace(" ", "T");
	}
	
	/**
	 * 同步房源信息
	 * 
	 * @param list 房源信息的json列表
	 * @return
	 */
	public static Map<String, Object> houseSyns(List<JSONObject> list, MemcachedManager memcachedManager) {
		Map<String, Object> resultData = new HashMap<>();
		resultData.put("success", false);
		
		//获取token
		String token = getAccessToken(memcachedManager);
		if(ValidatorUtils.isEmpty(token)){
			resultData.put("msg", "获取token失败");
			return resultData;
		}
		if(ValidatorUtils.isEmpty(list)){
			resultData.put("msg", "同步房源列表不能为空");
			return resultData;
		}
		
		try{
			//超仪需要传递额外回调地址信息
			for(JSONObject houseSourceInfo : list){
				houseSourceInfo.put("callBackUrl", CJoyConf.CALLBACK_URL);
			}
			
			Map<String, Object> params = new HashMap<>();
			params.put("access_token", token);
			params.put("roomInfo", JSON.toJSONString(list));
			Log.i(JSON.toJSONString(params));
			
		    String apiUrl = URL + "registRoomInfo.do";
			String responseStr = HttpRequestUtils.post(apiUrl, params);
			
			String errorMsg = null;
			if(ValidatorUtils.isNotEmpty(responseStr)){
				JSONObject jsonObject = JSONObject.parseObject(responseStr);
				//{"status":1,"data":null}
				//status: 0 失败, 1 成功, 2 认证错误
				String code = jsonObject.getString("status");
				String message = jsonObject.getString("data");
				if(ValidatorUtils.isEquals("1", code)){
					resultData.put("success", true);
				}else if(ValidatorUtils.isEquals("0", code)){
					if(ValidatorUtils.isNotEmpty(message)){
						errorMsg = message;
					}else{
						errorMsg = "操作失败";
					}
				}else if(ValidatorUtils.isEquals("2", code)){
					errorMsg = "认证失败!";
					clearCacheInMem(memcachedManager);
					Log.i("认证失败!");
				}
				
				if(ValidatorUtils.isNotEmpty(errorMsg)){
					resultData.put("msg", errorMsg);
				}
			}
			
		}catch(Exception e){
			Log.i("调用接口出错");
			Log.e(e.getMessage(),e);
			resultData.put("msg", "调用接口出错");
		}
		
		return resultData;
	}
	
	public static void main(String[] args) throws Exception{
		control(null, "201603162194", "1");
		//status(null, "201603162194");
		//findReadInfoByDate(null,"201603162194","2016-07-01","2017-07-21");
		
		//chargeAmount(null, "123456789", 10.5f);
		//getDeviceRechargeRecord(null, "201603162194", "2016-07-01","2017-07-21");
		
		//getSurplusElectricity(null, "201603162194");
		
		//clearBalanceByMeterNo(null, "123456789");
		
		//System.out.println(TimeUtil.stampToDate(System.currentTimeMillis() + ""));
		//System.out.println(getFormatTime("2017-07-19 16:54:08.0"));
	}
}
