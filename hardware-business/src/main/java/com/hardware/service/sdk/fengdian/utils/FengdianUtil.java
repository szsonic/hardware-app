package com.hardware.service.sdk.fengdian.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hardware.business.client.HttpRequestUtils;
import com.hardware.business.conf.FengdianConf;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.domain.AmmterUserBean;
import com.hardware.business.domain.DeviceRechargeRecordRes;
import com.hardware.business.domain.DeviceRechargeRecordResponse;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.utils.DataUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.http.HttpClientUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 蜂电电表的工具类
 * 
 * @author zhongqi
 *
 */
public class FengdianUtil {
	/**
	 * 登录导蜂电的saas平台
	 * 
	 * @param host
	 * @param userId
	 * @param pass
	 * @return
	 */
	public static Map<String, String> userLogin() {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/user/login/");
		url.append(FengdianConf.USERID).append("/");
		url.append(FengdianConf.PASS);
		String data = HttpClientUtils.get(url.toString());
		if (StringUtils.isNotBlank(data)) {
			AmmterUserBean ammterUserBean = JSON.parseObject(data, AmmterUserBean.class);
			Map<String, String> userData = new HashMap<>();
			userData.put("code", ObjectUtils.toString(ammterUserBean.getCode(), ""));
			userData.put("uid", ObjectUtils.toString(ammterUserBean.getData().getUuid(), ""));
			userData.put("token", ObjectUtils.toString(ammterUserBean.getExpand(), ""));
			userData.put("userId", ObjectUtils.toString(ammterUserBean.getData().getUserid(), ""));
			return userData;
		} else {
			throw new HardwareSdkException("蜂电 登录导蜂电的saas平台 出错");
		}
	}

	/**
	 * 检查电表是什么类型
	 * 
	 * @param token
	 * @param uid
	 *            平台账户的uid
	 * @param pid
	 *            电表的唯一ID
	 * @return 返回设备类型 {"success":true,"msg":"电表没有找到","data":1} ,0未知、1集中器、2节点
	 * 
	 */
	public static AmmeterResult checkType(String uid, String token, String pid) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/checktype/").append(pid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);

		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电检查电表是什么类型出错");
		}
	}

	/**
	 * 绑定电表导集中器
	 * 
	 * @param uid
	 *            平台账户的uid
	 * @param token
	 *            蜂电平台koten
	 * @param cid
	 *            电表唯一ID
	 * @param macId
	 *            默认密码
	 * @param userId
	 *            在蜂电平台的用户ID
	 * @return 返回成功或者失败 {"success":true,"msg":"电表没有找到"}
	 */
	public static AmmeterResult bindTerminal(String uid, String token, String cid, String macId) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/terminal/bind/");
		url.append(cid).append("/");
		url.append(macId).append("/");
		url.append(FengdianConf.USERID);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpRequestUtils.put(url.toString(), params);

		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电绑定电表导集中器出错");
		}
	}

	/**
	 * 绑定电表到节点
	 * 
	 * @param token
	 *            蜂电平台koten
	 * @param uid
	 *            平台账户的uid
	 * @param cid
	 *            电表唯一ID
	 * @param macId
	 *            默认密码
	 * @param userId
	 *            在蜂电平台的用户ID
	 * @return 返回成功或者失败 {"success":true,"msg":"电表没有找到"}
	 */
	public static AmmeterResult bindNode(String uid, String token, String cid, String nid) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/node/bind/");
		url.append(nid).append("/");
		url.append(cid).append("/");
		url.append(nid).append("/");
		url.append("0").append("/");
		url.append("0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpRequestUtils.put(url.toString(), params);

		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电绑定电表到节点出错");
		}
	}

	/**
	 * 获取集中器列表
	 * 
	 * @param token
	 * @param uid
	 *            平台账户的uid
	 * @return
	 */
	public static AmmeterResult terminalList(String uid, String token) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/terminal");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电获取集中器列表出错");
		}
	}

	/**
	 * 获取账户下面所有设备列表
	 * 
	 * @param token
	 * @param uid
	 *            平台账户的uid
	 * @return
	 */
	public static AmmeterResult ammeterList(String uid, String token, String devId) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/ammeter/").append(devId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电获取账户下面所有设备列表出错");
		}
	}

	/**
	 * 获取节点列表
	 * 
	 * @param token
	 * @param uid
	 *            平台账户的uid
	 * @return
	 */
	public static AmmeterResult nodeList(String uid, String token) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/node");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电获取节点列表出错");
		}
	}

	/**
	 * 获取电表报表
	 * 
	 * @param token
	 * @param uid
	 *            平台账户的uid
	 * @param uuid
	 *            设备ID
	 * @param bdate
	 *            开始时间
	 * @param edate
	 *            结束时间
	 * 
	 * @return
	 */
	public static AmmeterResult report(String uid, String token, String uuid, String bdate, String edate) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/ammeter/report/");
		url.append(uuid).append("/");
		url.append(bdate).append("/");
		url.append(edate).append("/");
		url.append("480");
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("uid", uid);
		requestParams.put("token", token);
		String data = HttpClientUtils.get(url.toString(), requestParams);
		if (StringUtils.isNotBlank(data)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(data, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电获取电表报表出错");
		}
	}

	/**
	 * 获取单个电表的用电量和剩余电量
	 * 
	 * @param uid
	 * @param token
	 * @param devId
	 */
	public static Map<String, Object> log(String uid, String token, String devId, String bdate, String edate) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/ammeter/report/");
		url.append(devId).append("/");
		url.append(bdate).append("/");
		url.append(edate).append("/");
		url.append("480");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);
		Map<String, Object> resultData = new HashMap<>();
		if (StringUtils.isNotBlank(result)) {
			JSONObject data = JSON.parseObject(result, JSONObject.class);
			if (data.getInteger("Code") == 0) {
				resultData.put("success", "true");
				JSONArray array = data.getJSONArray("Data");
				List<Map<String, Object>> datas = Lists.newArrayList();
				for (Object object : array) {
					Map<String, Object> d = Maps.newHashMap();
					JSONObject o = (JSONObject) object;
					if (devId.equals(o.getString("Devid"))) {
						// 当天耗电量
						d.put("Allpower", o.getString("Allpower"));
						// 最后电量
						d.put("Lastpower", o.getString("Lastpower"));
						d.put("Date", o.getString("Date"));
						datas.add(d);
					}
				}
				resultData.put("data", datas);
			} else {
				resultData.put("success", "false");
			}
			resultData.put("msg", data.get("Message"));
			return resultData;
		} else {
			throw new HardwareSdkException("获取电量历史出错");
		}
	}

	/**
	 * 对电表进行通电操作
	 * 
	 * @param uid
	 * @param token
	 * @param devId
	 * 			@return{"success":true,"msg":"通电成功","data":null}
	 */
	public static AmmeterResult switchOn(String uid, String token, String devId) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/switchon/").append(devId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);

		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电通电出错");
		}
	}

	/**
	 * 对电表进行断电操作
	 * 
	 * @param uid
	 * @param token
	 * @param devId
	 * 			@return{"success":true,"msg":"通电成功","data":null}
	 */
	public static AmmeterResult switchOff(String uid, String token, String devId) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/switchoff/").append(devId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("蜂电断电出错");
		}
	}

	/**
	 * 获取单个电表的在线状态和通电状态
	 * 
	 * @param uid
	 * @param token
	 * @param devId
	 * 			@return{"success":true,"msg":"通电成功","data":null}
	 */
	public static Map<String, Object> status(String uid, String token, String devId) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/ammeter/").append(devId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);
		Map<String, Object> resultData = new HashMap<>();
		if (StringUtils.isNotBlank(result)) {
			JSONObject data = JSON.parseObject(result, JSONObject.class);
			if (data.getInteger("Code") == 0) {
				resultData.put("success", "true");
				JSONArray array = data.getJSONArray("Data");
				for (Object object : array) {
					JSONObject o = (JSONObject) object;
					if (devId.equals(o.getString("Uuid"))) {
						if (o.getInteger("Value") == 0) {
							resultData.put("electricDoorSwitchStatus", "off");
						} else if (o.getInteger("Value") == 1) {
							resultData.put("electricDoorSwitchStatus", "on");
						}
						if (o.getInteger("Status") == 0) {
							resultData.put("wifiOnlineStatus", "on");
						} else if (o.getInteger("Status") == 1) {

							resultData.put("wifiOnlineStatus", "off");
						}
						resultData.put("price", new BigDecimal(o.getDoubleValue("Price"))
								.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						JSONObject paramJson = o.getJSONObject("Param");
						if (paramJson != null) {
							// mode=0后付费、1预付费
							resultData.put("mode", ObjectUtils.toString(paramJson.getString("mode"), "0"));
						}
					}
				}
			} else {
				resultData.put("success", "false");
			}
			resultData.put("msg", data.get("Message"));
			return resultData;
		} else {
			throw new HardwareSdkException("获取状态出错");
		}
	}

	/**
	 * 获取单个电表的状态
	 * 
	 * @param uid
	 * @param token
	 * @param pid
	 *            如果是集中器取cid，如果是节点取nid @return{"success":true,"msg":"通电成功",
	 *            "data": null}
	 * @return 电表唯一设备号
	 */
	public static String getUuid(String uid, String token, String pid) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/parent/").append(pid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpClientUtils.get(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			if (ammeterDataBean.getCode().equals("0")) {
				JSONArray data = (JSONArray) ammeterDataBean.getData();
				for (Object d : data) {
					JSONObject o = (JSONObject) d;
					String devtype = o.getString("Devtype");
					if (StringUtils.equals("64", devtype)) {
						return ObjectUtils.toString(o.getString("Uuid"));
					}
				}
			}
		} else {
			throw new HardwareSdkException("获取电表设备号失败：" + result);
		}
		return null;
	}

	/**
	 * 电表入住
	 * 
	 * @param uid
	 * @param token
	 * @param devId
	 *            电表唯一设备号
	 * @return
	 */
	public static AmmeterResult stayRoom(String uid, String token, String devId) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/ammeter/stayroom/").append(devId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpRequestUtils.put(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("电表入住失败：" + result);
		}
	}

	/**
	 * 电表退房
	 * 
	 * @param uid
	 * @param token
	 * @param devId
	 *            电表唯一设备号
	 * @return
	 */
	public static AmmeterResult recedeRoom(String uid, String token, String devId) {
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/ammeter/confirmrecederoom/").append(devId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpRequestUtils.put(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		} else {
			throw new HardwareSdkException("电表退房失败：" + result);
		}
	}
	/**
	 * 查询设备充值记录
	 * @throws ParseException 
	 */
	public static DeviceRechargeRecordResponse getDeviceRechargeRecord(String uid, String token, String devId, String bdate, String edate, BigDecimal price) throws ParseException {
		
		HashMap<String,Object> urlVariables=new HashMap<String,Object>();	
		urlVariables.put("uid", uid);	
		urlVariables.put("token",token);
		urlVariables.put("version", FengdianConf.VERSION);
		String url=FengdianConf.HOST+"/account/recharge/"+devId+"/"+bdate+"/"+edate;
		String result = HttpClientUtils.get(url.toString(),urlVariables);
		DeviceRechargeRecordRes deviceRechargeRecordRes=new DeviceRechargeRecordRes();
		DeviceRechargeRecordResponse dev=new DeviceRechargeRecordResponse();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		if (StringUtils.isNotBlank(result)) {
			JSONObject data = JSON.parseObject(result, JSONObject.class);
			dev.setCode(data.getInteger("Code")+"");
			if (data.getInteger("Code") == 0) {
				deviceRechargeRecordRes.setMsg("操作成功");
				JSONArray array = data.getJSONArray("Data");
				for (Object object : array) {
					Map<String,String> map=new HashMap<String,String>();
					JSONObject o = (JSONObject) object;
					BigDecimal val_amount=DataUtil.getValue(o.getString("Value"));
					map.put("amount",val_amount.toString());
				//   电表充值金额
					BigDecimal total_price=val_amount.multiply(price);
					BigDecimal money = total_price.setScale(2, total_price.ROUND_DOWN);
					//map.put("money", Integer.parseInt(money.toString().split("\\.")[0])+"");	
					//map.put("price",DataUtil.getValue(price.toString()).toString());
				    String strDate =  o.getString("Addtime") ;    
					map.put("time",strDate.substring(0, 19));
					list.add(map);
					
				}
				deviceRechargeRecordRes.setData(list);
				dev.setRes(deviceRechargeRecordRes);
			}else
			{
				deviceRechargeRecordRes.setMsg(data.getString("Message"));
				deviceRechargeRecordRes.setData(list);
				dev.setRes(deviceRechargeRecordRes);
			}
		}
		return dev;
		
	}
	/**
	 * 查询用户充值记录
	 */
	public static JSONObject getUserRechargeRecord(String uid, String token, String bdate, String edate)
	{
		HashMap<String,Object> urlVariables=new HashMap<String,Object>();	
		urlVariables.put("uid", uid);	
		urlVariables.put("token",token);
		urlVariables.put("version", FengdianConf.VERSION);
		String url=FengdianConf.HOST+"/account/recharge/"+bdate+"/"+edate;
		String result = HttpClientUtils.get(url.toString(),urlVariables);
		JSONObject json=JSONObject.parseObject(result);
		return json;	
	}

	/**
	 * 蜂电电量充值
	 * @param devId 设备devId
	 * @param amount 充值度数
	 *
	 */
	public static void chargeAmount(String uid,String token,String devId, Float amount) {
		amount=amount*100;
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/account/threshold/").append(devId).append("/").append(amount.intValue());
		//蜂电接口中充电度数的传参为0.01度，所以这里要乘以100。
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("token", token);
		String result = HttpRequestUtils.put(url.toString(), params);
		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterResult=JSON.parseObject(result, AmmeterResult.class);
			if(!"0".equals(ammeterResult.getCode())){
				throw new HardwareSdkException("`：" + result);
			}
		} else {
			throw new HardwareSdkException("蜂电电表电量充值失败：" + result);
		}
	}

}
