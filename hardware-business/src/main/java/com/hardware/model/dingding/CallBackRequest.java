package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;

/**
 * <br>
 * 丁盯统一回调格式
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallBackRequest {
    /**
     * 事件名，在文档中有定义，目前用到的有
     * 1、deviceInstall 设备安装
     * 2、batteryAlarm 低电量提醒
     * 3、lockOfflineAlarm 门锁离线通知
     * 4、clearLockOfflineAlarm 门锁上线通知
     */
    private String event;
    /**
     * 设备的UUID
     */
    private String uuid;
    /**
     * 公寓ID
     */
    private String home_id;
    /**
     * 房间ID
     */
    private String room_id;
//    /**
//     * 时间戳，单位ms
//     */
//    private Long time;
    /**
     * 回调的详细信息，由于detail里面的内容不固定，所以采用map来转换保存
     * 针对不同的event有不同的传值
     * 1、deviceInstall，传值的key是"type",type类型为int，取值有：1（网关），4（门锁），5（电表），6（采集器）
     * 2、batteryAlarm，传值的key是"battery_level",类型是int，当前设备的电量
     * 以后有后续的事件再添加
     *
     */
    private Map<String,Object> detail;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

//    public Long getTime() {
//        return time;
//    }
//
//    public void setTime(Long time) {
//        this.time = time;
//    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "CallBackRequest{" +
                "event='" + event + '\'' +
                ", uuid='" + uuid + '\'' +
                ", home_id='" + home_id + '\'' +
                ", room_id='" + room_id + '\'' +
                ", detail=" + detail +
                '}';
    }
}
