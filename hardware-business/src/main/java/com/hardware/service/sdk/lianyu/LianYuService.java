package com.hardware.service.sdk.lianyu;

import com.alibaba.druid.support.json.JSONUtils;
import com.hardware.business.client.HttpRequestUtils;
import com.hardware.business.model.lianyu.*;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class LianYuService {
	private static final String URL = "http://columbus.unovo.com.cn/saas20/gateway";
	private static final String PARTNER = "yrupms";
	private static final String PWD = "ibulSiB9Wz";
	
	private static final String MEM_KEY_TOKEN = "LINAYU_ACCESS_TOKEN";
	
	@Resource(name = "memcachedManager")
	private MemcachedManager memcachedManager;
	
	/*
	 * 获取联寓对外接口访问令牌
	 * 联寓服务会对该令牌保存30分钟, 并且每调用一次API, 都会为重置该令牌有效时间为30分钟 
	 */
	private LianYuParamMap getAccessToken(){
		LianYuParamMap params = new LianYuParamMap();
		
		try{
			String token = getTokenFromCache();
			
			if(ValidatorUtils.isEmpty(token)){
				//重新请求令牌
				params.tokenFromMemCache(false);
				
				params.service("accessToken").partner(PARTNER).pwd(PWD);
				String post = HttpRequestUtils.post(URL, params);
				
				@SuppressWarnings("unchecked")
				Map<String, String> resultMap = (Map<String, String>) JSONUtils.parse(post);
				
				if(ValidatorUtils.isNotEmpty(resultMap)){
					if(ValidatorUtils.isNotEmpty(resultMap.get("resultCode"))
							&& ValidatorUtils.isEquals(resultMap.get("resultCode"), "SUCCESS")){
						token = resultMap.get("token");
					}
					
					if(ValidatorUtils.isEmpty(token)){
						String resultMessage = "获取认证令牌失败";
						if(ValidatorUtils.isNotEmpty(resultMap.get("subMessage"))){
							resultMessage = resultMap.get("subMessage");
						}else if(ValidatorUtils.isNotEmpty(resultMap.get("resultMessage"))){
							resultMessage = resultMap.get("resultMessage");
						}
						
						params.resultMessage(resultMessage);
					}
					
				}
				
			}
			
			params.token(token);
			memcachedManager.cacheObject(MEM_KEY_TOKEN, token, 60 * 29);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Log.i("get token from cache: " + params.isTokenFromMemCache() + ": " + params.getToken());
		
		return params;
	}
	
	//从缓存里拿 token
	private String getTokenFromCache(){
		try{
			String token = memcachedManager.loadObject(MEM_KEY_TOKEN);
			return token;
		}catch(Exception e){
			Log.e(e.getMessage());
		}
		
		return null;
	}
	
	private void clearCacheInMem(){
		memcachedManager.cacheObject(MEM_KEY_TOKEN, "", 60 * 1);
	}
	
	/* 获得日志
	 * @param firstTimeCall:是否第一次调用，防止递归调用导致死循环
	 */
	public LianYuResponseOfGetRoomlockLog getRoomlockLog(boolean firstTimeCall){
		Log.i("------------- getRoomlockLog start -------------");
		LianYuResponseOfGetRoomlockLog result = new LianYuResponseOfGetRoomlockLog();
		try{
			//获取token
			LianYuParamMap temp = getAccessToken();
			String token = temp.getToken();
			if(ValidatorUtils.isEmpty(token)){
				//获取 token 失败
				result.setResultMessage(ValidatorUtils.isEmpty(temp.getResultMessage())
						? "获取 token 失败" : temp.getResultMessage());
				return result;
			}
		
			LianYuParamMap params = new LianYuParamMap();
			params.service("getroomlocklog").token(token).partner(PARTNER)
			.param("startTime", "2016-06-21 11:20:12")
			.param("roomId", "496")
			//.param("deviceId", "000000001000000002")
			.param("page", 1).param("pageSize", 8);
			
			String responseStr = HttpRequestUtils.post(URL, params);
			
			result = com.alibaba.fastjson.JSONObject.parseObject(responseStr, 
					LianYuResponseOfGetRoomlockLog.class);
			Log.i(result.result());
			
			if(firstTimeCall && ValidatorUtils.isEquals(result.getResultCode(), "TOKEN_ERROR")
					&& temp.isTokenFromMemCache()){
				//从缓存里拿的 token 失效, 则需要重新获取 token
				//清空缓存里的 token
				clearCacheInMem();
				
				//再次处理该业务
				return getRoomlockLog(false);
			}
			
			//test code
			/*if(ValidatorUtils.isNotEmpty(result)){
				if(ValidatorUtils.isNotEmpty(result.getResultCode())
						&& ValidatorUtils.isEquals(result.getResultCode(), "SUCCESS")){
					RoomlockLogPage page = result.getPage();
					if(ValidatorUtils.isNotEmpty(page)){
						System.out.println("totalPage:" + page.getTotalPage());
						System.out.println("totalCount:" + page.getTotalCount());
						System.out.println("currentPage:" + page.getCurrentPage());
						
						List<RoomlockLog> logList = page.getList();
						if(ValidatorUtils.isNotEmpty(logList)){
							System.out.println("------------>size:" + logList.size());
							for(RoomlockLog roomlockLog : logList){
								System.out.println("deviceId: " + roomlockLog.getDeviceId());
								System.out.println("deviceLocation: " + roomlockLog.getDeviceLocation());
								System.out.println("errorReason: " + roomlockLog.getErrorReason());
								System.out.println("operatorName: " + roomlockLog.getOperatorName());
								System.out.println("operatorType: " + roomlockLog.getOperaTypeName());
							}
						}
					}
				}
				
			}*/
		}catch(Exception e){
			Log.e(e.getMessage());
		}
		
		Log.i("------------- getRoomlockLog end -------------");
		return result;
	}
	
	/* 获得用户门锁
	 * @param firstTimeCall:是否第一次调用，防止递归调用导致死循环
	 */
	public LianYuResponseOfGetRoomlock getRoomLock(boolean firstTimeCall){
		Log.i("------------- getRoomLock start -------------");
		LianYuResponseOfGetRoomlock result = new LianYuResponseOfGetRoomlock();
		try{
			//获取token
			LianYuParamMap temp = getAccessToken();
			String token = temp.getToken();
			if(ValidatorUtils.isEmpty(token)){
				//获取 token 失败
				result.setResultMessage(ValidatorUtils.isEmpty(temp.getResultMessage())
						? "获取 token 失败" : temp.getResultMessage());
				return result;
			}
		
			LianYuParamMap params = new LianYuParamMap();
			params.service("GetRoomLock").partner(PARTNER).token(token).param("roomId", "496");
			String responseStr = HttpRequestUtils.post(URL, params);
			
			result = com.alibaba.fastjson.JSONObject.parseObject(responseStr,LianYuResponseOfGetRoomlock.class);
            Log.i(result.result());
			
			if(ValidatorUtils.isEquals(result.getResultCode(), "TOKEN_ERROR")
					&& temp.isTokenFromMemCache() && firstTimeCall){
				//从缓存里拿的 token 失效, 则需要重新获取 token
				//清空缓存里的 token
				clearCacheInMem();
				
				//再次处理该业务
				return getRoomLock(false);
			}
			
			//test code
			/*if(ValidatorUtils.isNotEmpty(result)){
				if(ValidatorUtils.isNotEmpty(result.getResultCode())
						&& ValidatorUtils.isEquals(result.getResultCode(), "SUCCESS")){
					
					List<RoomLock> locks = result.getLocks();
					if(ValidatorUtils.isNotEmpty(locks)){
						for(RoomLock lock : locks){
							System.out.println("id:" + lock.getId());
							System.out.println("name:" + lock.getName());
							System.out.println("keepOpen:" + lock.getKeepOpen());
							System.out.println("leafRoom:" + lock.getLeafRoom());
							System.out.println("canBeLocked:" + lock.getCanBeLocked());
							System.out.println("lockModel:" + lock.getLockModel());
						}
					}
				}
			}*/
		}catch(Exception e){
			Log.e(e.getMessage());
		}
		
		Log.i("------------- getRoomLock end -------------");
		
		return result;
	}
	
	/* 获得客房用电量
	 * @param firstTimeCall:是否第一次调用，防止递归调用导致死循环
	 */
	public LianYuResponseOfGetRoomElecmeter getRoomElecmeter(boolean firstTimeCall){
		Log.i("------------- getRoomElecmeter start -------------");
		LianYuResponseOfGetRoomElecmeter result = new LianYuResponseOfGetRoomElecmeter();
		try{
			//获取token
			LianYuParamMap temp = getAccessToken();
			String token = temp.getToken();
			if(ValidatorUtils.isEmpty(token)){
				//获取 token 失败
				result.setResultMessage(ValidatorUtils.isEmpty(temp.getResultMessage())
						? "获取 token 失败" : temp.getResultMessage());
				return result;
			}
		
			LianYuParamMap params = new LianYuParamMap();
			params.service("getroomelecmeter").partner(PARTNER).token(token)
			.param("roomId", "351")
			.param("startDay", "2017-04-27")
			.param("endDay", "2017-04-29");
			
			String responseStr = HttpRequestUtils.post(URL, params);
			
			result = com.alibaba.fastjson.JSONObject.parseObject(responseStr,
					LianYuResponseOfGetRoomElecmeter.class);
            Log.i(result.result());
			
			if(ValidatorUtils.isEquals(result.getResultCode(), "TOKEN_ERROR")
					&& temp.isTokenFromMemCache() && firstTimeCall){
				//从缓存里拿的 token 失效, 则需要重新获取 token
				//清空缓存里的 token
				clearCacheInMem();
				
				//再次处理该业务
				return getRoomElecmeter(false);
			}
			
			if(ValidatorUtils.isNotEmpty(result)){
				if(ValidatorUtils.isNotEmpty(result.getResultCode())
						&& ValidatorUtils.isEquals(result.getResultCode(), "SUCCESS")){
					
					List<RoomElecmeter> datas = result.getData();
					if(ValidatorUtils.isNotEmpty(datas)){
						for(RoomElecmeter roomElecmeter : datas){
							System.out.println("date: " + roomElecmeter.getDate() + ", amount: " + roomElecmeter.getAmount());
						}
					}
				}
				
			}
		}catch(Exception e){
			Log.e(e.getMessage());
		}
		
		Log.i("------------- getRoomElecmeter end -------------");
		return result;
	}
	
}
