package com.hardware.model.lianyu;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

//联寓接口请求参数 - 封装
public class LianYuParamMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	private static DateFormat daf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//默认携带序列号 和 请求时间戳
	public LianYuParamMap(){
		this.put("outSerialNumber", UUID.randomUUID());
		this.put("timestamp", daf.format(new Date()));
	}
	
	//服务名
	public LianYuParamMap service(String service){
		this.put("service", service);
		return this;
	}
	
	//接口登录名
	public LianYuParamMap partner(String partner){
		this.put("partner", partner);
		return this;
	} 
	
	//密码
	public LianYuParamMap pwd(String pwd){
		this.put("pwd", pwd);
		return this;
	}
	
	//认证令牌
	public LianYuParamMap token(String token){
		this.put("token", token);
		return this;
	}
	
	//回调地址
	public LianYuParamMap notifyUrl(String notifyUrl){
		this.put("notifyUrl", notifyUrl);
		return this;
	}
	
	public LianYuParamMap param(String paramKey, Object paramValue){
		this.put(paramKey, paramValue);
		return this;
	}
	
	
	//-------------- 以下可以作为返回时使用 -------------------
	public LianYuParamMap resultMessage(String resultMessage){
		this.put("resultMessage", resultMessage);
		return this;
	}
	public String getResultMessage(){
		return get("resultMessage") == null ? null : get("resultMessage").toString();
	}
	
	//标识 token 是从memcache 中获取的,还是重新请求联寓API获取的
	private boolean tokenFromMemCache = true;
	public LianYuParamMap tokenFromMemCache(boolean tokenFromMemCache){
		this.tokenFromMemCache = tokenFromMemCache;
		return this;
	}
	public boolean isTokenFromMemCache(){
		return this.tokenFromMemCache;
	}
	
	public String getToken(){
		return get("token") == null ? null : get("token").toString();
	}
}
