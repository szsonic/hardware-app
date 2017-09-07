package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 获取动态密码明文返回实体，对应接口文档6.6 <br>
 *
 * @author sunzhongshuai
 */
public class GetDynamicPasswordPlaintextResponse extends BaseResponse {
    /**
     * 密码明文
     */
    private String password;
    /**
     * 过期时间
     */
    @JsonProperty(value = "invalid_time")
    private Long invalidTime;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Long invalidTime) {
        this.invalidTime = invalidTime;
    }
}
