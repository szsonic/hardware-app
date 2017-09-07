package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken extends BaseResponse{

    private String access_token;
    private int expires_time;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(int expires_time) {
        this.expires_time = expires_time;
    }
}
