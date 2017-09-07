package com.hardware.model.guojia;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallBackInstallRequest implements Serializable{
    //校验码
    @JsonProperty("validate_code")
    private String validateCode;
    // 校验因子
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

    //网关编号
    @JsonProperty("node_no")
    private String nodeNo;

    //事件类型
    @JsonProperty("event")
    private String event;

    //操作时间
    @JsonProperty("time")
    private Long  time;

    //大门code
    @JsonProperty("house_code")
    private String houseCode;

    //小门房间code
    @JsonProperty("room_code")
    private String roomCode;

    //详细地址
    @JsonProperty("address")
    private String address;

    //门锁厂商
    @JsonProperty("producer")
    private String producer;

    //嵌入式版本
    @JsonProperty("software_version")
    private String softwareVersion;

    //当前拥有人手机
    @JsonProperty("curr_owner_mobil")
    private String currOwnerMobil;

    //当前拥有人姓名
    @JsonProperty("curr_owner_name")
    private String currOwnerName;

    //操作人手机号
    @JsonProperty("created_mobile")
    private String createdMobile;

    //操作人姓名
    @JsonProperty("created_name")
    private String created_name;

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

    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getCurrOwnerMobil() {
        return currOwnerMobil;
    }

    public void setCurrOwnerMobil(String currOwnerMobil) {
        this.currOwnerMobil = currOwnerMobil;
    }

    public String getCurrOwnerName() {
        return currOwnerName;
    }

    public void setCurrOwnerName(String currOwnerName) {
        this.currOwnerName = currOwnerName;
    }

    public String getCreatedMobile() {
        return createdMobile;
    }

    public void setCreatedMobile(String createdMobile) {
        this.createdMobile = createdMobile;
    }

    public String getCreated_name() {
        return created_name;
    }

    public void setCreated_name(String created_name) {
        this.created_name = created_name;
    }

    @Override
    public String toString() {
        return "CallBackInstallRequest{" +
                "validateCode='" + validateCode + '\'' +
                ", factor='" + factor + '\'' +
                ", pushTimes=" + pushTimes +
                ", businessId='" + businessId + '\'' +
                ", lockNo='" + lockNo + '\'' +
                ", nodeNo='" + nodeNo + '\'' +
                ", event='" + event + '\'' +
                ", time=" + time +
                ", houseCode='" + houseCode + '\'' +
                ", roomCode='" + roomCode + '\'' +
                ", address='" + address + '\'' +
                ", producer='" + producer + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", currOwnerMobil='" + currOwnerMobil + '\'' +
                ", currOwnerName='" + currOwnerName + '\'' +
                ", createdMobile='" + createdMobile + '\'' +
                ", created_name='" + created_name + '\'' +
                '}';
    }
}
