package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * <br>
 * 丁盯获取门锁信息返回对象，对应文档6.3
 * @author sunzhongshuai
 */
public class GetLockInfoResponse extends BaseResponse{
    /**
     * 设备mac地址
     */
    private String mac;
    /**
     * 设备序列号
     */
    private String sn;
    /**
     * 门锁uuid
     */
    private String uuid;
    /**
     * 设备注册时间戳，单位S
     */
    private String bind_time;
    /**
     * 门锁在线状态，1：在线，2：离线
     */
    private Integer onoff_line;
    /**
     * 最近一次状态在线更新时间戳，单位S
     */
    private Long onoff_time;
    /**
     * 门锁的电量信息，“-1”标示未知
     */
    private Integer power;
    /**
     * 最近一次电量更新时间戳，单位S
     */
    private Long power_refreshtime;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 设备类型
     */
    private String model;
    /**
     * 门锁名字
     */
    private String name;
    /**
     * 门锁信号
     */
    private Integer lqi;
    /**
     * 门锁信号刷新
     */
    private Long lqi_refreshtime;
    /**
     * 门锁所绑定的网关，描述
     */
    private String center_description;
    /**
     * 门锁所绑定的网关uuid
     */
    private String center_uuid;
    /**
     * 门锁所绑定的网关设备码
     */
    private String center_sn;
    private Version versions;

    /**
     * 一些版本信息，文档上没有具体说明。
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Version{
        private String zigbee_version;
        private String protocol_version;
        private String hardware_version;
        private String app_version;

        public String getZigbee_version() {
            return zigbee_version;
        }

        public void setZigbee_version(String zigbee_version) {
            this.zigbee_version = zigbee_version;
        }

        public String getProtocol_version() {
            return protocol_version;
        }

        public void setProtocol_version(String protocol_version) {
            this.protocol_version = protocol_version;
        }

        public String getHardware_version() {
            return hardware_version;
        }

        public void setHardware_version(String hardware_version) {
            this.hardware_version = hardware_version;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBind_time() {
        return bind_time;
    }

    public void setBind_time(String bind_time) {
        this.bind_time = bind_time;
    }

    public Integer getOnoff_line() {
        return onoff_line;
    }

    public void setOnoff_line(Integer onoff_line) {
        this.onoff_line = onoff_line;
    }

    public Long getOnoff_time() {
        return onoff_time;
    }

    public void setOnoff_time(Long onoff_time) {
        this.onoff_time = onoff_time;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Long getPower_refreshtime() {
        return power_refreshtime;
    }

    public void setPower_refreshtime(Long power_refreshtime) {
        this.power_refreshtime = power_refreshtime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLqi() {
        return lqi;
    }

    public void setLqi(Integer lqi) {
        this.lqi = lqi;
    }

    public Long getLqi_refreshtime() {
        return lqi_refreshtime;
    }

    public void setLqi_refreshtime(Long lqi_refreshtime) {
        this.lqi_refreshtime = lqi_refreshtime;
    }

    public String getCenter_description() {
        return center_description;
    }

    public void setCenter_description(String center_description) {
        this.center_description = center_description;
    }

    public String getCenter_uuid() {
        return center_uuid;
    }

    public void setCenter_uuid(String center_uuid) {
        this.center_uuid = center_uuid;
    }

    public String getCenter_sn() {
        return center_sn;
    }

    public void setCenter_sn(String center_sn) {
        this.center_sn = center_sn;
    }

    public Version getVersions() {
        return versions;
    }

    public void setVersions(Version versions) {
        this.versions = versions;
    }
}

