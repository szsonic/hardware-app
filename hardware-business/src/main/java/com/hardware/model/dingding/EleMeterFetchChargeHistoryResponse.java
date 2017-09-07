package com.hardware.model.dingding;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class EleMeterFetchChargeHistoryResponse extends BaseResponse{
    private List<HistoryBean> history;

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public static class HistoryBean{
        private String home_id;
        private String room_id;
        private String uuid;
        private Float amount;
        private Float total_amount;
        private Long time;

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

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }

        public Float getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(Float total_amount) {
            this.total_amount = total_amount;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }
    }
}
