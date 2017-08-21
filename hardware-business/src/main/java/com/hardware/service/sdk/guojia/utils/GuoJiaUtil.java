package com.hardware.service.sdk.guojia.utils;

import com.alibaba.fastjson.JSONObject;
import com.hardware.business.conf.GuoJiaConf;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.interceptor.LoggingRequestInterceptor;
import com.hardware.business.model.guojia.AccessTokenResponse;
import com.hardware.service.sdk.guojia.GJDoorLockSDK;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class GuoJiaUtil {
    /**
     * 通用请求路径
     */
//    protected static final String BASE_URL = "http://ops.huohetech.com:80";
//    private static final String KEY ="Tys5hwNo";
//    private static final String ACCOUNT="13641688076";
//    private static final String PASSWORD="a123456a";
//    protected static final String VERSION="1.0";
    /**
     * token过期时间
     */
    private static Date expirationTime;

    /**
     * 认证token
     */
    private static String token;

    protected final static RestTemplate restTemplate;
    static {
        restTemplate=new RestTemplate();
        ClientHttpRequestInterceptor clientHttpRequestInterceptor = new LoggingRequestInterceptor();
        restTemplate.getInterceptors().add(clientHttpRequestInterceptor);//添加RestTemplate日志拦截器
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    }

    /**
     * 根据丁盯提供的client_id和client_secret调接口获取token，
     * //todo:先从缓存中取token，如果没有再去调接口然后把新的token存在缓存上,过期时间设定的比对方提供的token有效时间少2分钟。
     * @return token
     */
    protected synchronized static String getAccessToken() throws ThirdPartyRequestException {
        if(StringUtils.isBlank(token)){
            AccessTokenResponse accessTokenResponse =getAccessTokenResponse();
            if(GJDoorLockSDK.SUCCESS.equals(accessTokenResponse.getRtlCode())){
                expirationTime=DateUtils.addHours(new Date(),24);
                token=accessTokenResponse.getData().getAccessToken();
            }else{
                throw new ThirdPartyRequestException("调用果加接口token失败:"+ JSONObject.toJSONString(accessTokenResponse));
            }
        }else{
            Date now= new Date();
            if(!now.before(expirationTime)){
                //如果token过期了需要重新取一次
                AccessTokenResponse accessTokenResponse =getAccessTokenResponse();
                if(GJDoorLockSDK.SUCCESS.equals(accessTokenResponse.getRtlCode())){
                    expirationTime=DateUtils.addHours(new Date(),24);
                    token=accessTokenResponse.getData().getAccessToken();
                }else{
                    throw new ThirdPartyRequestException("调用果加接口token失败:"+ JSONObject.toJSONString(accessTokenResponse));
                }
            }
        }
        return token;
    }

    private static AccessTokenResponse getAccessTokenResponse(){
        Map<String,Object> params=new HashMap<>();
        params.put("version", GuoJiaConf.VERSION);
        params.put("s_id", UUID.randomUUID().toString().replace("-",""));
        params.put("account", GuoJiaConf.ACCOUNT);
        DESEncrypt desPlus2 = new DESEncrypt(GuoJiaConf.KEY);
        params.put("password",desPlus2.encrypt(GuoJiaConf.PASSWORD));
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/login",params,AccessTokenResponse.class);
    }


    /**
     * 获取请求头部信息
     * @return
     */
    protected static HttpEntity getHttpEntity(Map<String,Object> params) throws ThirdPartyRequestException {
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("version", GuoJiaConf.VERSION);
        httpHeaders.add("access_token",getAccessToken());
        //由于token每天只能请求100次，所以这里请求到之后暂时写死，部署到服务器上再改回上面的
        //httpHeaders.set("access_token","3ccbcf795cdaa7f7379d776872396283d329022190b78e1d8886f82f995474f5");
        httpHeaders.set("s_id", UUID.randomUUID().toString().replace("-",""));
        return new HttpEntity<>(params,httpHeaders);
    }

}
