package com.hardware.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <br>
 * 批量新增订单接口请求参数
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddOrdersRequest {

        private String houseOpenId;//大门openId
        private String roomOpenId;//小门openId
        private String projectCode;//项目编码
        private String rentType;//租房类型
        private Boolean ammeterInstallFlag;//是否安装电表
        private Boolean doorLockInstallFlag;//是否安装门锁


        public Boolean getAmmeterInstallFlag() {
            return ammeterInstallFlag;
        }

        public void setAmmeterInstallFlag(Boolean ammeterInstallFlag) {
            this.ammeterInstallFlag = ammeterInstallFlag;
        }

        public Boolean getDoorLockInstallFlag() {
            return doorLockInstallFlag;
        }

        public void setDoorLockInstallFlag(Boolean doorLockInstallFlag) {
            this.doorLockInstallFlag = doorLockInstallFlag;
        }
        public String getHouseOpenId() {
            return houseOpenId;
        }

        public void setHouseOpenId(String houseOpenId) {
            this.houseOpenId = houseOpenId;
        }

        public String getRoomOpenId() {
            return roomOpenId;
        }

        public void setRoomOpenId(String roomOpenId) {
            this.roomOpenId = roomOpenId;
        }


        public String getProjectCode() {
            return projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public String getRentType() {
            return rentType;
        }

        public void setRentType(String rentType) {
            this.rentType = rentType;
        }

    @Override
    public String toString() {
        return "AddOrdersRequest{" +
                "houseOpenId='" + houseOpenId + '\'' +
                ", roomOpenId='" + roomOpenId + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", rentType='" + rentType + '\'' +
                ", ammeterInstallFlag=" + ammeterInstallFlag +
                ", doorLockInstallFlag=" + doorLockInstallFlag +
                '}';
    }
}
