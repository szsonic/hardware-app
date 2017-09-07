package com.hardware.model.guojia;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * <br>
 * 果加密码回调返回结果实体类
 * @author sunzhongshuai
 */
public class CallBackPwdResponse implements Serializable{

    //业务流水ID
    @JsonProperty("business_id")
    private String businessId;

    //推送结果 Y:成功，N：失败
    @JsonProperty("flag")
    private String flag;

    //失败信息，推送成功时为空
    @JsonProperty("error_msg")
    private String errorMsg;

    public CallBackPwdResponse() {
    }

    public CallBackPwdResponse(String businessId, String flag, String errorMsg) {
        this.businessId = businessId;
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
