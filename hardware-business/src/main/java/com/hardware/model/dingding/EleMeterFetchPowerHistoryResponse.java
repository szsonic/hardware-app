package com.hardware.model.dingding;

import java.util.Date;
import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class EleMeterFetchPowerHistoryResponse extends BaseResponse{

    private List<HistoryBean> history;

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public static class HistoryBean {
        /**
         * home_id : 58c2536f8bba3f1c8a16da8f
         * room_id : 58c2536f67df5d3251b0e458
         * uuid : 89db919ecc013505eac4d32973652df5
         * consume_amount : 0
         * total_amount : 0
         * charge_pooling_amount : 0
         * pooling_amount : 0
         * time : 1494121929329
         * ctime : 1494121929329
         * date : 2017-05-07
         */

        private String home_id;
        private String room_id;
        private String uuid;
        private Float consume_amount;
        private Float total_amount;
        private Float charge_pooling_amount;
        private Float pooling_amount;
        private Long time;
        private Long ctime;
        private Date date;

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

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Float getConsume_amount() {
            return consume_amount;
        }

        public void setConsume_amount(Float consume_amount) {
            this.consume_amount = consume_amount;
        }

        public Float getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(Float total_amount) {
            this.total_amount = total_amount;
        }

        public Float getCharge_pooling_amount() {
            return charge_pooling_amount;
        }

        public void setCharge_pooling_amount(Float charge_pooling_amount) {
            this.charge_pooling_amount = charge_pooling_amount;
        }

        public Float getPooling_amount() {
            return pooling_amount;
        }

        public void setPooling_amount(Float pooling_amount) {
            this.pooling_amount = pooling_amount;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public Long getCtime() {
            return ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
