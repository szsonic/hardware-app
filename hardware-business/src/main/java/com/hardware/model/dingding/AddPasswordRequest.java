package com.hardware.model.dingding;

/**
 * 新增密码参数实体 <br>
 *
 * @author sunzhongshuai
 */
public class AddPasswordRequest {
    /**
     * 公寓ID
     */
    private String homeId;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 设备UUID
     */
    private String uuid;


    /**
     * 待更新的密码值（6位数字）
     */
    private String password;

    /**
     * 短信发送激活码是否携带公寓信息，默认不携带，在phoneNo不为空时生效
     */
    private Boolean isSendLocation;

    /**
     * 密码更新成功后要发送的目标电话号码
     */
    private String phoneNo;

    /**
     * 是否是管理员密码，0：非管理员密码，1：管理员密码
     */
    private Integer isDefault;

    /**
     * 密码的名称
     */
    private String name;

    /**
     * 密码有效期的开始时间戳，单位：秒，若为空则密码永久有效
     */
    private Long permissionBegin;

    /**
     * 密码有效期的结束时间戳，单位：秒，若为空则密码永久有效
     */
    private Long permissionEnd;

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSendLocation() {
        return isSendLocation;
    }

    public void setSendLocation(Boolean sendLocation) {
        isSendLocation = sendLocation;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPermissionBegin() {
        return permissionBegin;
    }

    public void setPermissionBegin(Long permissionBegin) {
        this.permissionBegin = permissionBegin;
    }

    public Long getPermissionEnd() {
        return permissionEnd;
    }

    public void setPermissionEnd(Long permissionEnd) {
        this.permissionEnd = permissionEnd;
    }
}
