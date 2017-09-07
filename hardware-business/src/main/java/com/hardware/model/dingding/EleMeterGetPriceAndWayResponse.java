package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 电表电价计算方式返回实体<br>
 *
 * @author sunzhongshuai
 */
public class EleMeterGetPriceAndWayResponse extends BaseResponse {
    /**
     * 房间电价
     */
    @JsonProperty(value = "room_eleprice")
    private  Float roomElePrice;
    /**
     * 公寓统一电价
     */
    @JsonProperty(value = "client_eleprice")
    private  Float clientElePrice;
    /**
     * 电价方式，1：房间电价，2：公寓统一电价
     */
    @JsonProperty(value = "eleprice_way")
    private Integer elePriceWay;

    public Float getRoomElePrice() {
        return roomElePrice;
    }

    public void setRoomElePrice(Float roomElePrice) {
        this.roomElePrice = roomElePrice;
    }

    public Float getClientElePrice() {
        return clientElePrice;
    }

    public void setClientElePrice(Float clientElePrice) {
        this.clientElePrice = clientElePrice;
    }

    public Integer getElePriceWay() {
        return elePriceWay;
    }

    public void setElePriceWay(Integer elePriceWay) {
        this.elePriceWay = elePriceWay;
    }
}
