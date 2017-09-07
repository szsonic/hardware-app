package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 * 丁盯密码操作回调格式
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallBackPwdRequest {
    @JsonProperty(value = "uuid")
    private String uuid;
    @JsonProperty(value = "serviceid")
    private String serviceId;

    private Result result;
    @JsonProperty(value = "service")
    private String event;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public  static  class Result{
        private String id;
        @JsonProperty(value = "ErrNo")
        private String errNo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getErrNo() {
            return errNo;
        }

        public void setErrNo(String errNo) {
            this.errNo = errNo;
        }
    }

}
