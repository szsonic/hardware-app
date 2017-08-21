package com.hardware.business.service.impl;

import com.hardware.business.conf.DingDingConf;
import com.hardware.business.model.dingding.AccessToken;
import com.hardware.business.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Service("restService")
public class RestServiceImpl implements RestService {

    @Autowired
    private RestTemplate restTemplate;


    public String getAccessToken() {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("client_id", DingDingConf.CLIENT_ID);
        params.put("client_secret", DingDingConf.CLIENT_SECRET);
        AccessToken accessToken=restTemplate.postForObject(DingDingConf.BASE_URL +"access_token",params, AccessToken.class);
        return accessToken==null?null:accessToken.getAccess_token();
    }
}
