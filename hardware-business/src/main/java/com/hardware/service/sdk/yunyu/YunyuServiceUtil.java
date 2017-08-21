package com.hardware.service.sdk.yunyu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hardware.business.client.HttpRequestUtils;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.utils.SpringContextUtil;
import com.hardware.service.sdk.yunyu.SignUtil.RequestParams;
import org.iframework.commons.utils.date.DatetimeUtils;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import org.iframework.support.spring.memcached.MemcachedManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class YunyuServiceUtil {
	
	private static String basicUrl = "http://api.hiyunyu.com/rest/";
	private static final String MEM_KEY_TOKEN = "YUNYU_ACCESS_TOKEN";
	private static final String appId = "1024";
	private static final String appKey = "0a2f653f4e87dd80c56e68c6d65c786c";
	
	private static MemcachedManager memcachedManager;
	
	static{
		memcachedManager = (MemcachedManager)SpringContextUtil.getBean("memcachedManager");
	}
	
	/*
	 * 获取超仪对外接口访问令牌
	 * 有效期为10 小时
	 */
	private static String getAccessToken(){
		String token = getTokenFromCache();
		
		if(ValidatorUtils.isEmpty(token)){
			//重新获得 token
			token = getApplyToken();
			
			if(ValidatorUtils.isNotEmpty(token)
					&& ValidatorUtils.isNotEmpty(memcachedManager)){
				memcachedManager.cacheObject(MEM_KEY_TOKEN, token, 60 * 60 * 9);
			}
		}
		
		return token;
	}
	
	//从缓存里拿 token
	private static String getTokenFromCache(){
		try{
			String token = memcachedManager.loadObject(MEM_KEY_TOKEN);
			return token;
		}catch(Exception e){
			Log.e(e.getMessage(), e);
		}
		
		return null;
	}
	
	/*
	 * 获取applyToken test
	 */
	private static String getApplyToken(){
		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			return null;
		}

		try{
			String pubUrl = basicUrl + "applyToken?" + buildParamstr(params);
			String responseStr = HttpRequestUtils.get(pubUrl);

			Log.i("response of getApplyToken:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			if(ValidatorUtils.isNotEmpty(jsonObject)
					&& ValidatorUtils.isNotEmpty(jsonObject.getString("isSuccess"))
					&& ValidatorUtils.isEquals("true", jsonObject.getString("isSuccess"))){
				return jsonObject.getString("token");
			}

			return null;
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			return null;
		}
	}

	@SuppressWarnings("unused")
	private static void clearCacheInMem(MemcachedManager memcachedManager){
		memcachedManager.cacheObject(MEM_KEY_TOKEN, "", 60 * 1);
	}

	public static String buildParamstr(Map<String, String> paramsMap) {
		StringBuilder bd = new StringBuilder();
		if (paramsMap != null) {
			Set<Entry<String, String>> entrySet = paramsMap.entrySet();
			if (entrySet != null) {
				boolean isNotFirst = false;
				for (Entry<String, String> en : entrySet) {
					if (isNotFirst) {
						bd.append("&");
					}
					bd.append(en.getKey()).append("=").append(en.getValue());
					isNotFirst = true;
				}
			}
		}
		return bd.toString();
	}

	private static String getErrorMsg(JSONObject jsonObject){
		if(ValidatorUtils.isNotEmpty(jsonObject)
				&& ValidatorUtils.isNotEmpty(jsonObject.getString("isSuccess"))){
			if(ValidatorUtils.isEquals("true", jsonObject.getString("isSuccess"))){
				//删除密码的返回逻辑
				if(ValidatorUtils.isNotEmpty(jsonObject.getString("removeStatus"))){
					if(ValidatorUtils.isEquals("succeed", jsonObject.getString("removeStatus"))){
						return null;
					}else{
						String msg = jsonObject.getString("msg");
						if(ValidatorUtils.isEmpty(msg)){
							msg = "删除密码失败！";
						}
						return msg;
					}
				}
				//冻结密码返回逻辑
				if(ValidatorUtils.isNotEmpty(jsonObject.getString("disableStatus"))){
					if(ValidatorUtils.isEquals("succeed", jsonObject.getString("disableStatus"))){
						return null;
					}else{
						String msg = jsonObject.getString("msg");
						if(ValidatorUtils.isEmpty(msg)){
							msg = "冻结密码失败！";
						}
						return msg;
					}
				}
				//解冻密码返回逻辑
				if(ValidatorUtils.isNotEmpty(jsonObject.getString("enableStatus"))){
					if(ValidatorUtils.isEquals("succeed", jsonObject.getString("enableStatus"))){
						return null;
					}else{
						String msg = jsonObject.getString("msg");
						if(ValidatorUtils.isEmpty(msg)){
							msg = "解冻密码失败！";
						}
						return msg;
					}
				}
				//开门返回逻辑
				if(ValidatorUtils.isNotEmpty(jsonObject.getString("openStatus"))){
					if(ValidatorUtils.isEquals("succeed", jsonObject.getString("openStatus"))){
						return null;
					}else{
						String msg = jsonObject.getString("msg");
						if(ValidatorUtils.isEmpty(msg)){
							msg = "远程开门失败！";
						}
						return msg;
					}
				}
				//清空密码返回逻辑
				if(ValidatorUtils.isNotEmpty(jsonObject.getString("clearStatus"))){
					if(ValidatorUtils.isEquals("succeed", jsonObject.getString("clearStatus"))){
						return null;
					}else{
						String msg = jsonObject.getString("msg");
						if(ValidatorUtils.isEmpty(msg)){
							msg = "清空密码失败！";
						}
						return msg;
					}
				}

				return null;
			}else{
				String msg = jsonObject.getString("msg");
				if(ValidatorUtils.isEmpty(msg)){
					msg = "调用云寓接口失败！";
				}

				return msg;
			}
		}else{
			return "调用云寓接口异常！";
		}
	}

	/**
	 * 同步房源信息
	 * @param list 房源信息的json列表
	 * @return
	 */
	public static Map<String, Object> houseSyns(List<JSONObject> list) {
		Map<String, Object> resultData = new HashMap<>();
		resultData.put("success", false);

		//获取token
		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			resultData.put("msg", "获取token失败");
			return resultData;
		}
		if(ValidatorUtils.isEmpty(list)){
			resultData.put("msg", "同步房源列表不能为空");
			return resultData;
		}

		try{
			// 1. 组装参数
	        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
	        params.param("token", token).param("roomInfo", JSON.toJSONString(list));

			// 2. 参数签名
			String sign = null;
			try {
				sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
				params.param("sign", sign);
			} catch (IOException e) {
				Log.e(e.getMessage(), e);
				resultData.put("msg", "签名方法异常！");
				return resultData;
			}

			String pubUrl = basicUrl + "smartLock/syncHouse?" + buildParamstr(params);
			Log.i("request of syncHouse:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of syncHouse:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				resultData.put("msg", errorMsg);
			}else{
				resultData.put("success", true);
			}
		}catch(Exception e){
			Log.i("调用接口出错");
			Log.e(e.getMessage(),e);
			resultData.put("msg", "调用接口出错");
		}

		return resultData;
	}

	//添加门锁密码接口
	public static Map<String, Object> addPassword(String doorLockDevId, Date startTime, Date endTime,
			String password) throws DataErrorException, ThirdPartyRequestException {

		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			throw new ThirdPartyRequestException("请求云寓获取token异常！");
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId).param("password", password);
        if(ValidatorUtils.isNotEmpty(startTime)){
        	params.param("effectTime", String.valueOf(startTime.getTime()));
        }
        if(ValidatorUtils.isNotEmpty(endTime)){
        	params.param("invalidTime", String.valueOf(endTime.getTime()));
        }

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			throw new ThirdPartyRequestException("签名方法异常！");
		}

		try{
			String pubUrl = basicUrl + "smartLock/addPwd?" + buildParamstr(params);
			Log.i("request of addPassword:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of addPassword:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				throw new ThirdPartyRequestException(errorMsg);
			}else{
				String passwordId = jsonObject.getString("lockUserId");
				if(ValidatorUtils.isEmpty(passwordId)){
					throw new ThirdPartyRequestException("添加云寓门锁密码失败！");
				}

				Map<String, Object> resultData = new HashMap<>();
				resultData.put("passwordId", passwordId);
				return resultData;
			}
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			String msg
			  = ValidatorUtils.isEmpty(e.getMessage()) ? "调用云寓接口异常！" : e.getMessage();
			throw new ThirdPartyRequestException(msg);
		}
	}

	/*
	 * 删除密码
	 */
	public static boolean delPassword(String doorLockDevId, String passwordId)
			throws DataErrorException,ThirdPartyRequestException {
		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			throw new ThirdPartyRequestException("请求云寓获取token异常！");
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId).param("lockUserId", passwordId);

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			throw new ThirdPartyRequestException("签名方法异常！");
		}

		try{
			String pubUrl = basicUrl + "smartLock/removePwd?" + buildParamstr(params);
			Log.i("request of removePwd:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of removePwd:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				throw new ThirdPartyRequestException(errorMsg);
			}else{
				return true;
			}
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			String msg
			  = ValidatorUtils.isEmpty(e.getMessage()) ? "调用云寓接口异常！" : e.getMessage();
			throw new ThirdPartyRequestException(msg);
		}
	}

	//修改门锁密码接口
	public static Map<String, Object> modifyPassword(String doorLockDevId, Date startTime, Date endTime,
			String password, String lockUserId) throws DataErrorException, ThirdPartyRequestException {

		if(ValidatorUtils.isEmpty(lockUserId)){
			throw new ThirdPartyRequestException("lockUserId 为空不可修改密码!");
		}
		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			throw new ThirdPartyRequestException("请求云寓获取token异常！");
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId)
        .param("password", password).param("lockUserId", lockUserId);
        if(ValidatorUtils.isNotEmpty(startTime)){
        	params.param("effectTime", String.valueOf(startTime.getTime()));
        }
        if(ValidatorUtils.isNotEmpty(endTime)){
        	params.param("invalidTime", String.valueOf(endTime.getTime()));
        }

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			throw new ThirdPartyRequestException("签名方法异常！");
		}

		try{
			String pubUrl = basicUrl + "smartLock/modifyPwd?" + buildParamstr(params);
			Log.i("request of modifyPwd:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of modifyPwd:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				throw new ThirdPartyRequestException(errorMsg);
			}else{
				String passwordId = jsonObject.getString("lockUserId");
				if(ValidatorUtils.isEmpty(passwordId)){
					throw new ThirdPartyRequestException("修改云寓门锁密码失败！");
				}

				Map<String, Object> resultData = new HashMap<>();
				resultData.put("passwordId", passwordId);
				return resultData;
			}
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			String msg
			  = ValidatorUtils.isEmpty(e.getMessage()) ? "调用云寓接口异常！" : e.getMessage();
			throw new ThirdPartyRequestException(msg);
		}
	}

	/*
	 * 冻结密码
	 */
	public static boolean disablePassword(String doorLockDevId, String passwordId)
			throws DataErrorException,ThirdPartyRequestException {
		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			throw new ThirdPartyRequestException("请求云寓获取token异常！");
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId).param("lockUserId", passwordId);

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			throw new ThirdPartyRequestException("签名方法异常！");
		}

		try{
			String pubUrl = basicUrl + "smartLock/disablePwd?" + buildParamstr(params);
			Log.i("request of disablePwd:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of disablePwd:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				throw new ThirdPartyRequestException(errorMsg);
			}else{
				return true;
			}
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			String msg
			  = ValidatorUtils.isEmpty(e.getMessage()) ? "调用云寓接口异常！" : e.getMessage();
			throw new ThirdPartyRequestException(msg);
		}
	}

	/*
	 * 解冻密码
	 */
	public static boolean enablePassword(String doorLockDevId, String passwordId)
			throws DataErrorException,ThirdPartyRequestException {
		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			throw new ThirdPartyRequestException("请求云寓获取token异常！");
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId).param("lockUserId", passwordId);

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			throw new ThirdPartyRequestException("签名方法异常！");
		}

		try{
			String pubUrl = basicUrl + "smartLock/enablePwd?" + buildParamstr(params);
			Log.i("request of enablePwd:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of enablePwd:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				throw new ThirdPartyRequestException(errorMsg);
			}else{
				return true;
			}
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			String msg
			  = ValidatorUtils.isEmpty(e.getMessage()) ? "调用云寓接口异常！" : e.getMessage();
			throw new ThirdPartyRequestException(msg);
		}
	}

	/*
	 * 一键开锁
	 */
	public static boolean openDoor(String doorLockDevId)
			throws DataErrorException,ThirdPartyRequestException {
		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			throw new ThirdPartyRequestException("请求云寓获取token异常！");
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId);

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			throw new ThirdPartyRequestException("签名方法异常！");
		}

		try{
			String pubUrl = basicUrl + "smartLock/open?" + buildParamstr(params);
			Log.i("request of openDoor:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of openDoor:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				throw new ThirdPartyRequestException(errorMsg);
			}else{
				return true;
			}
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			String msg
			  = ValidatorUtils.isEmpty(e.getMessage()) ? "调用云寓接口异常！" : e.getMessage();
			throw new ThirdPartyRequestException(msg);
		}
	}

	//门锁开门记录
	public static List<JSONObject> openLog(String doorLockDevId, Date startTime, Date endTime){
		List<JSONObject> result = Lists.newArrayList();

		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			Log.e("请求云寓获取token异常！");
			return result;
		}

        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId)
        .param("openTimeFrom", String.valueOf(startTime.getTime()))
        .param("openTimeTo", String.valueOf(endTime.getTime()));

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e("签名方法异常！");
			return result;
		}

		try{
			String pubUrl = basicUrl + "smartLock/history?" + buildParamstr(params);
			Log.i("request of openDoorHistory:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of openDoorHistory:" + responseStr);

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				throw new ThirdPartyRequestException(errorMsg);
			}else{
				JSONArray array = jsonObject.getJSONArray("openList");
				if(ValidatorUtils.isNotEmpty(array)){
					for(Object object : array){
						JSONObject openLog = (JSONObject) object;
						String openFlag = openLog.getString("openFlag"); //	开门标志（0门开，1门关）
						if(ValidatorUtils.isEquals("0", openFlag)){
							JSONObject temp = new JSONObject();
							Long openTime = openLog.getLong("openTime");
							if(openTime != null && openTime > 0){
								temp.put("opTime", DatetimeUtils.format(new Date(openTime), "yyyy-MM-dd HH:mm:ss"));
							}else{
								temp.put("opTime", "");
							}
			                //开门方式
							//1远程开门,2按键密码开门,3临时授权开门,4管理员开门,5刷卡开门,6内置用户开门
							String openType = openLog.getString("openType");
							//盈家硬件开门方式
							//接口开门 1，密码开门 2 ，第三方平台开门 3 ,一次性密码开门  4, 动态密码  5, 机械钥匙开门   6 ，刷卡开门  7 ,其他  8
							if(ValidatorUtils.isEquals("5", openType)){
								openType = "7";
							}else if(ValidatorUtils.isEquals("3", openType)){
								openType = "4";
							}else if(ValidatorUtils.isNotEquals("1", openType)
									&& ValidatorUtils.isNotEquals("2", openType)){
								openType = "8";
							}
			                temp.put("action", openType);
			                temp.put("passwordId", openLog.getString("lockUserId"));

			                result.add(temp);
						}
					}
				}
			}
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			return result;
		}

		return result;
	}

	//清空密码
	public static Map<String, Object> clearAllPassword(String doorLockDevId)
			throws ThirdPartyRequestException {
		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			throw new ThirdPartyRequestException("请求云寓获取token异常！");
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId);

		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			throw new ThirdPartyRequestException("签名方法异常！");
		}

		try{
			String pubUrl = basicUrl + "smartLock/clearAllUser?" + buildParamstr(params);
			Log.i("request of clearPwd:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of clearPwd:" + responseStr);

			Map<String, Object> result = new HashMap<String, Object>();

			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				result.put("success", "false");
			}else{
				result.put("success", "true");
			}

			return result;
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			String msg
			  = ValidatorUtils.isEmpty(e.getMessage()) ? "调用云寓接口异常！" : e.getMessage();
			throw new ThirdPartyRequestException(msg);
		}
	}

	//获取门锁状态
	public static Map<String, Object> status(String doorLockDevId){
		Map<String, Object> result = new HashMap<String, Object>();

		String token = getAccessToken();
		if(ValidatorUtils.isEmpty(token)){
			Log.e("请求云寓获取token异常！");
			return result;
		}

		// 1. 组装参数
        RequestParams params = new SignUtil.RequestParams(appId, SignUtil.SIGN_METHOD_MD5);
        params.param("token", token).param("lockId", doorLockDevId);
		
		// 2. 参数签名
		String sign = null;
		try {
			sign = SignUtil.signTjyRequest(params, appKey, SignUtil.SIGN_METHOD_MD5);
			params.param("sign", sign);
		} catch (IOException e) {
			Log.e(e.getMessage(), e);
			return result;
		}
		
		try{
			String pubUrl = basicUrl + "smartLock/readLockStatus?" + buildParamstr(params);
			Log.i("request of status:" + pubUrl);
			String responseStr = HttpRequestUtils.get(pubUrl);
			Log.i("response of status:" + responseStr);
			
			JSONObject jsonObject = JSONObject.parseObject(responseStr);
			String errorMsg = getErrorMsg(jsonObject);
			if(ValidatorUtils.isNotEmpty(errorMsg)){
				result.put("wifiOnlineStatus", "off");
			}else{
				//获取电压
				JSONObject lockStatus = (JSONObject) jsonObject.get("lockStatus");
				if(ValidatorUtils.isNotEmpty(lockStatus)){
					Double voltage = lockStatus.getDoubleValue("voltage");
					if(ValidatorUtils.isNotEmpty(voltage)){
						BigDecimal temp = new BigDecimal(voltage / 6); // 1.5 * 4 是标准电压
				        float percent = temp.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
				        
				        result.put("powerLeftValue", (int)(percent * 100));
					}
				}
				//在线状态
				if(ValidatorUtils.isEquals("online", jsonObject.getString("runStatus"))){
					result.put("wifiOnlineStatus", "on");
				}else{
					result.put("wifiOnlineStatus", "off");
				}
			}
			
			return result;
		}catch(Exception e){
			Log.e(e.getMessage(), e);
			return result;
		}
	}
	
	//电表接口
	public static void main(String[] args) throws Exception{
		getApplyToken();
	}
}
