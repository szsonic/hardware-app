package com.hardware.model.guojia;

import java.util.List;

/**
 * <br>
 * 门锁详细信息，对应果加文档3.4
 * @author sunzhongshuai
 */
public class GetLockInfoResponse extends BaseResponse{




    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * 安装时间
         */
        private Long install_time;

        /**
         * 网关接收到的锁的信号强度
         */
        private Integer rssi;

        /**
         * 安装地址
         */
        private String address;

        /**
         * 门锁通信状态更新时间
         */
        private Long comu_status_update_time;

        /**
         * 网关通信状态，00：通信正常；01：通信异常（仅433门锁有效）
         */
        private String node_comu_status;

        /**
         * 门锁分类，0：一代433锁，1：蓝牙锁 3：二代协议433锁
         */
        private String lock_kind;

        /**
         *门锁编码
         */
        private String lock_no;

        /**
         *房间编码
         */
        private String room_code;

        /**
         *质保日期（起）
         */
        private Long guarantee_time_start;

        /**
         * 描述
         */
        private String description;

        /**
         *门锁通讯状态，00：通信正常；01：通信异常
         */
        private String comu_status;

        /**
         *电量更新时间
         */
        private Long power_update_time;

        /**
         * 质保日期（止）
         */
        private Long guarantee_time_end;

        /**
         * 房屋编码
         */
        private String house_code;

        /**
         * 网关编码（仅433锁有效）
         */
        private String node_no;

        /**
         * 电池电量
         */
        private Integer power;

        /**
         * 门锁安装地址（省市区等信息）
         */
        private List<RegionBean> region;

        public Long getInstall_time() {
            return install_time;
        }

        public void setInstall_time(Long install_time) {
            this.install_time = install_time;
        }

        public Integer getRssi() {
            return rssi;
        }

        public void setRssi(Integer rssi) {
            this.rssi = rssi;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Long getComu_status_update_time() {
            return comu_status_update_time;
        }

        public void setComu_status_update_time(Long comu_status_update_time) {
            this.comu_status_update_time = comu_status_update_time;
        }

        public String getNode_comu_status() {
            return node_comu_status;
        }

        public void setNode_comu_status(String node_comu_status) {
            this.node_comu_status = node_comu_status;
        }

        public String getLock_kind() {
            return lock_kind;
        }

        public void setLock_kind(String lock_kind) {
            this.lock_kind = lock_kind;
        }

        public String getLock_no() {
            return lock_no;
        }

        public void setLock_no(String lock_no) {
            this.lock_no = lock_no;
        }

        public String getRoom_code() {
            return room_code;
        }

        public void setRoom_code(String room_code) {
            this.room_code = room_code;
        }

        public Long getGuarantee_time_start() {
            return guarantee_time_start;
        }

        public void setGuarantee_time_start(Long guarantee_time_start) {
            this.guarantee_time_start = guarantee_time_start;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getComu_status() {
            return comu_status;
        }

        public void setComu_status(String comu_status) {
            this.comu_status = comu_status;
        }

        public Long getPower_update_time() {
            return power_update_time;
        }

        public void setPower_update_time(Long power_update_time) {
            this.power_update_time = power_update_time;
        }

        public Long getGuarantee_time_end() {
            return guarantee_time_end;
        }

        public void setGuarantee_time_end(Long guarantee_time_end) {
            this.guarantee_time_end = guarantee_time_end;
        }

        public String getHouse_code() {
            return house_code;
        }

        public void setHouse_code(String house_code) {
            this.house_code = house_code;
        }

        public String getNode_no() {
            return node_no;
        }

        public void setNode_no(String node_no) {
            this.node_no = node_no;
        }

        public Integer getPower() {
            return power;
        }

        public void setPower(Integer power) {
            this.power = power;
        }

        public List<RegionBean> getRegion() {
            return region;
        }

        public void setRegion(List<RegionBean> region) {
            this.region = region;
        }

        public static class RegionBean {
            /**
             * code : 0086
             * level : 0
             * name : 中国大陆
             */

            private String code;
            private Integer level;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
