package com.hardware.model.dingding;

/**
 * <br>
 * 新增房间返回实体类
 * @author sunzhongshuai
 */
public class AddRoomResponse extends BaseResponse {
    private String room_id;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
