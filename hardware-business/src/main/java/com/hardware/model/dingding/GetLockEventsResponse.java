package com.hardware.model.dingding;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * 开门历史记录返回实体 <br>
 *
 * @author sunzhongshuai
 */
public class GetLockEventsResponse extends BaseResponse{

    /**
     * 开门历史记录
     */
    private List<LockEventsBean> lock_events;




    public List<LockEventsBean> getLock_events() {
        return lock_events;
    }

    public void setLock_events(List<LockEventsBean> lock_events) {
        this.lock_events = lock_events;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LockEventsBean {

        /**
         * 事件发生的时间戳，单位ms
         */
        private Long time;
        /**
         * 时间id
         * 1：开门
         */
        private Integer eventid;
        /**
         * 事件触发源
         * 2:密码开锁
         */
        private Integer source;
        /**
         * 触发源id，密码开锁即为密码id
         */
        private Integer sourceid;
        /**
         * 触发源名字，如密码id的名字
         */
        private String source_name;

        private String extra;

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public Integer getEventid() {
            return eventid;
        }

        public void setEventid(Integer eventid) {
            this.eventid = eventid;
        }

        public Integer getSource() {
            return source;
        }

        public void setSource(Integer source) {
            this.source = source;
        }

        public Integer getSourceid() {
            return sourceid;
        }

        public void setSourceid(Integer sourceid) {
            this.sourceid = sourceid;
        }

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
    }
}
