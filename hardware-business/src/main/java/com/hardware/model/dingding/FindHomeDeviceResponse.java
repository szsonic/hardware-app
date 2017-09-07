package com.hardware.model.dingding;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class FindHomeDeviceResponse extends BaseResponse {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * type : gateway
         * sn : cnjl0003160900049286
         * uuid : 3b283d78a1807ca9dd92102fee0e5826
         * description : 网关1
         * room_id : 58c2536f8bba3f1c8a16da8f
         * onoff : 1
         * center_uuid : 3b283d78a1807ca9dd92102fee0e5826
         * power : 95
         * passwords : [{"id":999,"frozen":0},{"id":1011,"send_to":"18605167728","frozen":0}]
         * elecollector_uuid : 521827d392ea1b2f66a458fd95c4fe08
         * amount : 0.29
         */

        private String type;
        private String sn;
        private String uuid;
        private String description;
        private String room_id;
        private Integer onoff;
        private String center_uuid;
        private Integer power;
        private String elecollector_uuid;
        private Double amount;
        private List<PasswordsBean> passwords;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public Integer getOnoff() {
            return onoff;
        }

        public void setOnoff(Integer onoff) {
            this.onoff = onoff;
        }

        public String getCenter_uuid() {
            return center_uuid;
        }

        public void setCenter_uuid(String center_uuid) {
            this.center_uuid = center_uuid;
        }

        public Integer getPower() {
            return power;
        }

        public void setPower(Integer power) {
            this.power = power;
        }

        public String getElecollector_uuid() {
            return elecollector_uuid;
        }

        public void setElecollector_uuid(String elecollector_uuid) {
            this.elecollector_uuid = elecollector_uuid;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public List<PasswordsBean> getPasswords() {
            return passwords;
        }

        public void setPasswords(List<PasswordsBean> passwords) {
            this.passwords = passwords;
        }

        public static class PasswordsBean {
            /**
             * id : 999
             * frozen : 0
             * send_to : 18605167728
             */

            private Integer id;
            private Integer frozen;
            private String send_to;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getFrozen() {
                return frozen;
            }

            public void setFrozen(Integer frozen) {
                this.frozen = frozen;
            }

            public String getSend_to() {
                return send_to;
            }

            public void setSend_to(String send_to) {
                this.send_to = send_to;
            }
        }
    }
}
