package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 丁盯接口返回的通用格式<br>
 *
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {

    /**
     * 请求ID
     */
    @JsonProperty(value = "ReqID")
    private String ReqID;

    /**
     * 错误码，定义如下
     * 0：成功
     * 101：token无效
     * 102：token过期
     * 11000：无法添加重复对象
     * 14001：参数有误
     * 14003：数据库操作错误
     * 15000：内部错误
     * 15006：记录不存在
     * 15007：方法未实现
     * 15008：该房间已存在绑定的门锁
     * 15009：该公寓已存在绑定的中心
     * 15010：设备记录已经存在
     * 15011：设备已被别人绑定
     * 15012：房间信息不存在
     * 15013：管理员密码不能被删除或冻结
     * 15014：权限受限
     *
     */
    @JsonProperty(value = "ErrNo")
    private Integer ErrNo;

    /**
     * 原因描述
     */
    @JsonProperty(value = "ErrMsg")
    private String ErrMsg;

    public String getReqID() {
        return ReqID;
    }

    public void setReqID(String reqID) {
        ReqID = reqID;
    }

    public Integer getErrNo() {
        return ErrNo;
    }

    public void setErrNo(Integer errNo) {
        ErrNo = errNo;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }
}
