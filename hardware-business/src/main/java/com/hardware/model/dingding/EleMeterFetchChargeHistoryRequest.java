package com.hardware.model.dingding;

import java.util.Date;

/**
 * 电表充值历史记录请求参数<br>
 * 如果roomId唯一，可以不传homeId
 * roomId和uuid必须要有一个，优先取uuid
 * @author sunzhongshuai
 */
public class EleMeterFetchChargeHistoryRequest {
    /**
     * 公寓id
     */
    private String homeId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 电表uuid
     */
    private String uuid;
    /**
     * 偏移量，默认为0
     */
    private Integer offset;
    /**
     * 本次拉取条数，默认20
     */
    private Integer count;
    /**
     * 查询开始时间
     */
    private Date startTime;
    /**
     * 查询结束时间
     */
    private Date endTime;

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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
