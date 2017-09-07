package com.hardware.model.guojia;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 * 果加密码回调请求RequestBody
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallBackPwdRequest {
    //校验码
    @JsonProperty("validate_code")
    private String validateCode;

    //校验因子
    @JsonProperty("factor")
    private String factor;

    //推送次数
    @JsonProperty("push_times")
    private Integer pushTimes;

    //业务流水单号
    @JsonProperty("business_id")
    private String businessId;

    //门锁编号
    @JsonProperty("lock_no")
    private String lockNo;

    //密码类型（其实就是密码编号）
    @JsonProperty("pwd_no")
    private Integer pwdNo;

    //密码使用人手机号
    @JsonProperty("pwd_user_mobile")
    private String pwdUserMobile;

    //事件描述
    @JsonProperty("event")
    private String event;


    //辅助信息
    @JsonProperty("extra")
    private String extra;


    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public Integer getPushTimes() {
        return pushTimes;
    }

    public void setPushTimes(Integer pushTimes) {
        this.pushTimes = pushTimes;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getLockNo() {
        return lockNo;
    }

    public void setLockNo(String lockNo) {
        this.lockNo = lockNo;
    }

    public Integer getPwdNo() {
        return pwdNo;
    }

    public void setPwdNo(Integer pwdNo) {
        this.pwdNo = pwdNo;
    }

    public String getPwdUserMobile() {
        return pwdUserMobile;
    }

    public void setPwdUserMobile(String pwdUserMobile) {
        this.pwdUserMobile = pwdUserMobile;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "CallBackPwdRequest{" +
                "validateCode='" + validateCode + '\'' +
                ", factor='" + factor + '\'' +
                ", pushTimes=" + pushTimes +
                ", businessId='" + businessId + '\'' +
                ", lockNo='" + lockNo + '\'' +
                ", pwdNo=" + pwdNo +
                ", pwdUserMobile='" + pwdUserMobile + '\'' +
                ", event='" + event + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
