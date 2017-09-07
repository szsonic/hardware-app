package com.hardware.model.guojia;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class AccessTokenResponse extends BaseResponse {

    private Token Data;

    public Token getData() {
        return Data;
    }

    public void setData(Token data) {
        Data = data;
    }

    public static class Token{
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_second")
        private Long expiresSecond;

        @JsonProperty("expires_times")
        private Integer expiresTimes;

        public String getAccessToken() {
            return accessToken;
        }



        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Long getExpiresSecond() {
            return expiresSecond;
        }

        public void setExpiresSecond(Long expiresSecond) {
            this.expiresSecond = expiresSecond;
        }

        public Integer getExpiresTimes() {
            return expiresTimes;
        }

        public void setExpiresTimes(Integer expiresTimes) {
            this.expiresTimes = expiresTimes;
        }

    }


}
