package com.hardware.service.sdk.dingding.utils;

import com.hardware.business.conf.DingDingConf;
import com.hardware.business.model.dingding.AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 丁盯通用工具类，定义一些通用的属性和方法<br>
 *
 * @author sunzhongshuai
 *
 */
@Component
public class DingDingUtil {
    /**
     * 通用请求路径
     */
    protected static final String BASE_URL = DingDingConf.BASE_URL;
    private static final String CLIENT_ID = DingDingConf.CLIENT_ID;
    private static final String CLIENT_SECRET= DingDingConf.CLIENT_SECRET;


    protected static RestTemplate restTemplate=new RestTemplate();

    /**
     * 根据丁盯提供的client_id和client_secret调接口获取token，
     * //todo:先从缓存中取token，如果没有再去调接口然后把新的token存在缓存上,过期时间设定的比对方提供的token有效时间少2分钟。
     * @return token
     */
    protected static String getAccessToken() {
        Map<String,Object> params=new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        AccessToken accessToken=restTemplate.postForObject(BASE_URL +"access_token",params, AccessToken.class);
        return accessToken.getAccess_token();

    }




}
