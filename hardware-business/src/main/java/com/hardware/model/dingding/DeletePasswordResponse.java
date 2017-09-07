package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 删除密码返回实体<br>
 *
 * @author sunzhongshuai
 */
public class DeletePasswordResponse extends BaseResponse{
    /**
     * 文档没标注，暂时用不到
     */
    @JsonProperty(value = "serviceid")
    private String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
