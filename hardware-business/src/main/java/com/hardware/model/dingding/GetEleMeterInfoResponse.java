package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 丁盯电表信息实体类，对应接口文档11.1<br>
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetEleMeterInfoResponse extends BaseResponse {


    /**
     * 电表uuid
     */
    private String uuid;
    /**
     * 采集器uuid
     */
    private String elecollector_uuid;
    /**
     * 设备mac地址
     */
    private String mac;
    /**
     * 设备序列号
     */
    private String sn;
    /**
     * 版本信息
     */
    private VersionsBean versions;
    /**
     * 电表状态（保留字段）
     */
    private Integer onoff_line;
    /**
     * 最近一次在线状态更新时间戳，单位S
     */
    private Long onoff_time;
    /**
     * 暂无说明
     */
    private Long time;
    /**
     * 设备类型
     */
    private String model;
    /**
     * 电表名字
     */
    private String name;
    /**
     * 设备注册时间时间戳，单位S
     */
    private Long bind_time;
    /**
     * 暂无说明
     */
    private Integer status;
    /**
     * 透支额度，-1为未知
     */
    private Double overdraft;
    /**
     * 透支额度刷新时间
     */
    private Long overdraft_time;
    /**
     * 额定功率，-1为未知
     */
    private Double capacity;
    /**
     * 额定功率刷新时间
     */
    private Long capacity_time;
    /**
     * 当前电表电量
     */
    private Double consume_amount;
    /**
     * 电表电量刷新时间
     */
    private Long consume_amount_time;
    /**
     * 总充电量，-1为未知
     */
    private Double power_total;
    /**
     * 总充电量刷新时间
     */
    private Long power_total_time;
    /**
     * 是否合闸，-1：未知，1：合闸，2：跳闸
     */
    private Integer enable_state;
    /**
     * 合闸刷新时间
     */
    private Long enable_state_time;
    /**
     * 操作类型
     * 1：充电操作
     * 2：修改透支额度
     * 3：电表清零
     * 4：修改电表的最大功率
     * 5：电表读
     * 6：跳闸
     * 7：合闸
     * 8：充电
     */
    private Integer operation;
    /**
     * 操作状态
     * 1：充电中
     * 2：失败
     * 3：成功
     */
    private Integer operation_stage;
    /**
     * 充电状态
     * 1：充电中
     * 2：失败
     * 3：成功
     */
    private Integer charge_stage;
    /**
     * 设置透支额度状态
     * 1：设置中
     * 2：失败
     * 3：成功
     */
    private Integer overdraft_stage;
    /**
     * 设置额定功率状态
     * 1：设置中
     * 2：失败
     * 3：成功
     */
    private Integer capacity_stage;
    /**
     * 通信状态
     * -1：未知
     * 1：正常
     * 2：失败
     */
    private Integer trans_status;
    /**
     * 通信刷新状态
     */
    private Long trans_status_time;
    /**
     * 暂无说明
     */
    private Integer switch_stage;
    /**
     * 暂无说明
     */
    private Long switch_stage_time;
    /**
     * 暂无说明
     */
    private Integer control_switch;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getElecollector_uuid() {
        return elecollector_uuid;
    }

    public void setElecollector_uuid(String elecollector_uuid) {
        this.elecollector_uuid = elecollector_uuid;
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

    public VersionsBean getVersions() {
        return versions;
    }

    public void setVersions(VersionsBean versions) {
        this.versions = versions;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
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

    public Long getBind_time() {
        return bind_time;
    }

    public void setBind_time(Long bind_time) {
        this.bind_time = bind_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Double overdraft) {
        this.overdraft = overdraft;
    }

    public Long getOverdraft_time() {
        return overdraft_time;
    }

    public void setOverdraft_time(Long overdraft_time) {
        this.overdraft_time = overdraft_time;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Long getCapacity_time() {
        return capacity_time;
    }

    public void setCapacity_time(Long capacity_time) {
        this.capacity_time = capacity_time;
    }

    public Double getConsume_amount() {
        return consume_amount;
    }

    public void setConsume_amount(Double consume_amount) {
        this.consume_amount = consume_amount;
    }

    public Long getConsume_amount_time() {
        return consume_amount_time;
    }

    public void setConsume_amount_time(Long consume_amount_time) {
        this.consume_amount_time = consume_amount_time;
    }

    public Double getPower_total() {
        return power_total;
    }

    public void setPower_total(Double power_total) {
        this.power_total = power_total;
    }

    public Long getPower_total_time() {
        return power_total_time;
    }

    public void setPower_total_time(Long power_total_time) {
        this.power_total_time = power_total_time;
    }

    public Integer getEnable_state() {
        return enable_state;
    }

    public void setEnable_state(Integer enable_state) {
        this.enable_state = enable_state;
    }

    public Long getEnable_state_time() {
        return enable_state_time;
    }

    public void setEnable_state_time(Long enable_state_time) {
        this.enable_state_time = enable_state_time;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getOperation_stage() {
        return operation_stage;
    }

    public void setOperation_stage(Integer operation_stage) {
        this.operation_stage = operation_stage;
    }

    public Integer getCharge_stage() {
        return charge_stage;
    }

    public void setCharge_stage(Integer charge_stage) {
        this.charge_stage = charge_stage;
    }

    public Integer getOverdraft_stage() {
        return overdraft_stage;
    }

    public void setOverdraft_stage(Integer overdraft_stage) {
        this.overdraft_stage = overdraft_stage;
    }

    public Integer getCapacity_stage() {
        return capacity_stage;
    }

    public void setCapacity_stage(Integer capacity_stage) {
        this.capacity_stage = capacity_stage;
    }

    public Integer getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(Integer trans_status) {
        this.trans_status = trans_status;
    }

    public Long getTrans_status_time() {
        return trans_status_time;
    }

    public void setTrans_status_time(Long trans_status_time) {
        this.trans_status_time = trans_status_time;
    }

    public Integer getSwitch_stage() {
        return switch_stage;
    }

    public void setSwitch_stage(Integer switch_stage) {
        this.switch_stage = switch_stage;
    }

    public Long getSwitch_stage_time() {
        return switch_stage_time;
    }

    public void setSwitch_stage_time(Long switch_stage_time) {
        this.switch_stage_time = switch_stage_time;
    }

    public Integer getControl_switch() {
        return control_switch;
    }

    public void setControl_switch(Integer control_switch) {
        this.control_switch = control_switch;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VersionsBean {

        private String elemeter_potocol_type;
        private String elemeter_potocol_version;

        public String getElemeter_potocol_type() {
            return elemeter_potocol_type;
        }

        public void setElemeter_potocol_type(String elemeter_potocol_type) {
            this.elemeter_potocol_type = elemeter_potocol_type;
        }

        public String getElemeter_potocol_version() {
            return elemeter_potocol_version;
        }

        public void setElemeter_potocol_version(String elemeter_potocol_version) {
            this.elemeter_potocol_version = elemeter_potocol_version;
        }
    }
}
