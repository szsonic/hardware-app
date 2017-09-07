package com.hardware.model.dingding;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class SearchHomeInfoResponse extends BaseResponse {


    /**
     * errMsg :
     * errNo : 0
     * home_list : [{"block":"xx小区","city":"上海市","country":"中国","description":"","devices":[{"type":"gateway","sn":"cnjl0003160900049286","uuid":"3b283d78a1807ca9dd92102fee0e5826","description":"网关1","room_id":"58c2536f8bba3f1c8a16da8f","onoff":1},{"sn":"lkjl0007160900117293","center_uuid":"3b283d78a1807ca9dd92102fee0e5826","type":"lock","room_id":"58c2536f67df5d3251b0e458","uuid":"c23831bb5ecc90e660eec24fda36b40b","power":95,"onoff":1},{"sn":"emjl0001161000001178","center_uuid":"3b283d78a1807ca9dd92102fee0e5826","type":"elecollector","description":"采集器","room_id":"58c2536f8bba3f1c8a16da8f","uuid":"521827d392ea1b2f66a458fd95c4fe08","onoff":1},{"sn":"160920003362","center_uuid":"3b283d78a1807ca9dd92102fee0e5826","elecollector_uuid":"521827d392ea1b2f66a458fd95c4fe08","type":"elemeter","description":"","room_id":"58c2536f67df5d3251b0e458","uuid":"89db919ecc013505eac4d32973652df5","onoff":1,"amount":0.29}],"home_id":"58c2536f8bba3f1c8a16da8f","home_name":"00001","home_type":1,"location":"人民路199号","province":"上海市","rooms":[{"description":"","install_state":4,"room_id":"58c2536f8bba3f1c8a16da8f","room_name":"default","sp_state":1},{"description":"","install_state":4,"room_id":"58c2536f67df5d3251b0e458","room_name":"101","sp_state":1},{"description":"","install_state":4,"room_id":"58c2536f67df5d3251b0e459","room_name":"102","sp_state":1}],"user_id":"18016440125","zone":"静安区"}]
     * reqID : 55d00938e1
     */

    private List<HomeListBean> home_list;

    public List<HomeListBean> getHome_list() {
        return home_list;
    }

    public void setHome_list(List<HomeListBean> home_list) {
        this.home_list = home_list;
    }

    public static class HomeListBean {
        /**
         * block : xx小区
         * city : 上海市
         * country : 中国
         * description :
         * devices : [{"type":"gateway","sn":"cnjl0003160900049286","uuid":"3b283d78a1807ca9dd92102fee0e5826","description":"网关1","room_id":"58c2536f8bba3f1c8a16da8f","onoff":1},{"sn":"lkjl0007160900117293","center_uuid":"3b283d78a1807ca9dd92102fee0e5826","type":"lock","room_id":"58c2536f67df5d3251b0e458","uuid":"c23831bb5ecc90e660eec24fda36b40b","power":95,"onoff":1},{"sn":"emjl0001161000001178","center_uuid":"3b283d78a1807ca9dd92102fee0e5826","type":"elecollector","description":"采集器","room_id":"58c2536f8bba3f1c8a16da8f","uuid":"521827d392ea1b2f66a458fd95c4fe08","onoff":1},{"sn":"160920003362","center_uuid":"3b283d78a1807ca9dd92102fee0e5826","elecollector_uuid":"521827d392ea1b2f66a458fd95c4fe08","type":"elemeter","description":"","room_id":"58c2536f67df5d3251b0e458","uuid":"89db919ecc013505eac4d32973652df5","onoff":1,"amount":0.29}]
         * home_id : 58c2536f8bba3f1c8a16da8f
         * home_name : 00001
         * home_type : 1
         * location : 人民路199号
         * province : 上海市
         * rooms : [{"description":"","install_state":4,"room_id":"58c2536f8bba3f1c8a16da8f","room_name":"default","sp_state":1},{"description":"","install_state":4,"room_id":"58c2536f67df5d3251b0e458","room_name":"101","sp_state":1},{"description":"","install_state":4,"room_id":"58c2536f67df5d3251b0e459","room_name":"102","sp_state":1}]
         * user_id : 18016440125
         * zone : 静安区
         */

        private String block;
        private String city;
        private String country;
        private String description;
        private String home_id;
        private String home_name;
        private Integer home_type;
        private String location;
        private String province;
        private String user_id;
        private String zone;
        private List<DevicesBean> devices;
        private List<RoomsBean> rooms;

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

        public String getHome_name() {
            return home_name;
        }

        public void setHome_name(String home_name) {
            this.home_name = home_name;
        }

        public Integer getHome_type() {
            return home_type;
        }

        public void setHome_type(Integer home_type) {
            this.home_type = home_type;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public List<DevicesBean> getDevices() {
            return devices;
        }

        public void setDevices(List<DevicesBean> devices) {
            this.devices = devices;
        }

        public List<RoomsBean> getRooms() {
            return rooms;
        }

        public void setRooms(List<RoomsBean> rooms) {
            this.rooms = rooms;
        }

        public static class DevicesBean {
            /**
             * type : gateway
             * sn : cnjl0003160900049286
             * uuid : 3b283d78a1807ca9dd92102fee0e5826
             * description : 网关1
             * room_id : 58c2536f8bba3f1c8a16da8f
             * onoff : 1
             * center_uuid : 3b283d78a1807ca9dd92102fee0e5826
             * power : 95
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
        }

        public static class RoomsBean {
            /**
             * description :
             * install_state : 4
             * room_id : 58c2536f8bba3f1c8a16da8f
             * room_name : default
             * sp_state : 1
             */

            private String description;
            private Integer install_state;
            private String room_id;
            private String room_name;
            private Integer sp_state;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Integer getInstall_state() {
                return install_state;
            }

            public void setInstall_state(Integer install_state) {
                this.install_state = install_state;
            }

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }

            public String getRoom_name() {
                return room_name;
            }

            public void setRoom_name(String room_name) {
                this.room_name = room_name;
            }

            public Integer getSp_state() {
                return sp_state;
            }

            public void setSp_state(Integer sp_state) {
                this.sp_state = sp_state;
            }
        }
    }
}
