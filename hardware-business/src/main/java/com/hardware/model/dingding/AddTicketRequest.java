package com.hardware.model.dingding;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class AddTicketRequest {
    /**
     * 公寓ID
     */
    private String homeId;

    /**
     * 工单服务对象，1：网关，2：门锁，3：电表
     */
    private Integer serviceTarget;

    /**
     * 工单类型，1：预约安装，2：售后维修
     */
    private Integer serviceType;

    /**
     * 房间ID集合
     */
    private List<String> roomIds;

    /**
     * 预约相关信息
     */
    private AddTicketSubscribe subscribe;

    /**
     * 工单描述，当工单类型是2（售后维修）时该值必填
     */
    private String ticketDescription;

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public Integer getServiceTarget() {
        return serviceTarget;
    }

    public void setServiceTarget(Integer serviceTarget) {
        this.serviceTarget = serviceTarget;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public List<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    public AddTicketSubscribe getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(AddTicketSubscribe subscribe) {
        this.subscribe = subscribe;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }
}
