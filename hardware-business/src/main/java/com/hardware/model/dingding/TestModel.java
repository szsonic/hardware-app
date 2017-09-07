package com.hardware.model.dingding;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class TestModel {
    private String uuid;
    private String time;
    private Detail event;

    public static class Detail{
        private Double consumeAmount;
        private Long consumeAmountTime;

        public Double getConsumeAmount() {
            return consumeAmount;
        }

        public void setConsumeAmount(Double consumeAmount) {
            this.consumeAmount = consumeAmount;
        }

        public Long getConsumeAmountTime() {
            return consumeAmountTime;
        }

        public void setConsumeAmountTime(Long consumeAmountTime) {
            this.consumeAmountTime = consumeAmountTime;
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Detail getEvent() {
        return event;
    }

    public void setEvent(Detail event) {
        this.event = event;
    }
}
