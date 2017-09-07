package com.hardware.model.dingding;

/**
 * 获取某个时间段的电量实体<br>
 *
 * @author sunzhongshuai
 */
public class EleMeterFetchPowerConsumptionResponse extends BaseResponse {


    /**
     * 用电量
     */
    private Double consumption;
    /**
     * 采集器采集电量的开始时间
     */
    private Long start_time;
    /**
     * 采集器采集电量的结束时间
     */
    private Long end_time;

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }
}
