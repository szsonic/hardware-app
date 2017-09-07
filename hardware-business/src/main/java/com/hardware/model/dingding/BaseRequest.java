package com.hardware.model.dingding;

/**
 * 丁盯通用<br>
 *
 * @author sunzhongshuai
 */
public class BaseRequest {
    private String homeId;
    private String roomId;
    private String uuid;

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
}
