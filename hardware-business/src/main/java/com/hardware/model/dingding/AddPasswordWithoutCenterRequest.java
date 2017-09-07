package com.hardware.model.dingding;

import java.util.Date;

/**
 * 下发激活码请求实体 <br>
 *
 * @author sunzhongshuai
 */
public class AddPasswordWithoutCenterRequest {
    /**
     * 公寓ID
     */
    private String homeId;
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 设备uuid
     */
    private String uuid;
    /**
     * 生成成功后要发送短信的手机号
     */
    private String phoneNo;
    /**
     * 激活码类型
     * 1：租户激活码
     * 2：员工激活码
     * 3：管理员激活码
     */
    private Integer CMD;
    /**
     * 密码名
     */
    private String name;
    /**
     * 是否发送短信，默认不发
     */
    private Boolean isSendMsg;
    /**
     * 短信是否携带公寓信息，默认不携带，只有发送短信时有效
     */
    private Boolean isSendLocation;
    /**
     * 密码有效期的结束时间
     */
    private Date permissionEnd;

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getCMD() {
        return CMD;
    }

    public void setCMD(Integer CMD) {
        this.CMD = CMD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSendMsg() {
        return isSendMsg;
    }

    public void setSendMsg(Boolean sendMsg) {
        isSendMsg = sendMsg;
    }

    public Boolean getSendLocation() {
        return isSendLocation;
    }

    public void setSendLocation(Boolean sendLocation) {
        isSendLocation = sendLocation;
    }

    public Date getPermissionEnd() {
        return permissionEnd;
    }

    public void setPermissionEnd(Date permissionEnd) {
        this.permissionEnd = permissionEnd;
    }
}
