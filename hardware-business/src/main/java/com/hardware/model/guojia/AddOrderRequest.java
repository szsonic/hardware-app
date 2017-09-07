package com.hardware.model.guojia;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 新增订单请求实体<br>
 *
 * @author sunzhongshuai
 */
public class AddOrderRequest implements Serializable {

    @JsonProperty("rcv_order_no")
    private String orderNo;

    @JsonProperty("house_code")
    private String HouseCode;

    @JsonProperty("inst_name")
    private String instName;

    @JsonProperty("inst_phone")
    private String instMobile;

    @JsonProperty("inst_address")
    private String address;

    @JsonProperty("inst_time")
    private Long instTime;

    @JsonProperty("goods_list")
    private List<Good> goodsList;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHouseCode() {
        return HouseCode;
    }

    public void setHouseCode(String houseCode) {
        HouseCode = houseCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstMobile() {
        return instMobile;
    }

    public void setInstMobile(String instMobile) {
        this.instMobile = instMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getInstTime() {
        return instTime;
    }

    public void setInstTime(Long instTime) {
        this.instTime = instTime;
    }

    public List<Good> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Good> goodsList) {
        this.goodsList = goodsList;
    }

    public static class Good{
        @JsonProperty("goods_id")
        private String goodId;

        @JsonProperty("goods_num")
        private Integer goodNum;

        @JsonProperty("room_code")
        private String roomCode;

        public String getGoodId() {
            return goodId;
        }

        public void setGoodId(String goodId) {
            this.goodId = goodId;
        }

        public Integer getGoodNum() {
            return goodNum;
        }

        public void setGoodNum(Integer goodNum) {
            this.goodNum = goodNum;
        }

        public String getRoomCode() {
            return roomCode;
        }

        public void setRoomCode(String roomCode) {
            this.roomCode = roomCode;
        }
    }




}
