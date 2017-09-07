package com.hardware.model.dingding;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class TicketResponse extends BaseResponse{

    /**
     * ReqID : 55dfc7b041
     * ErrNo : 0
     * ErrMsg :
     * ticket_info : {"ticket_id":"ISCN1705032099988","ticket_state":1,"service_type":1,"service_target":1,"public_auth":true,"relate_home":{"home_id":"59099cae56837f2c19066049","home_alias":"AA001","home_name":"测试新增集中式公寓1","user_id":"18016440125","client_id":"100477","province":"上海市","city":"上海","block":"普陀小区1","location":"中国上海外滩SOHO","region":21,"rooms":[{"room_id":"5909a96c56837f2c1906b139","room_name":"101"}]},"subscribe":{"name":"张三","phone":"18605167788","date":2017,"time":"101","note":"我是备注"}}
     */


    private TicketInfoBean ticket_info;


    public TicketInfoBean getTicket_info() {
        return ticket_info;
    }

    public void setTicket_info(TicketInfoBean ticket_info) {
        this.ticket_info = ticket_info;
    }

    public static class TicketInfoBean {
        /**
         * ticket_id : ISCN1705032099988
         * ticket_state : 1
         * service_type : 1
         * service_target : 1
         * public_auth : true
         * relate_home : {"home_id":"59099cae56837f2c19066049","home_alias":"AA001","home_name":"测试新增集中式公寓1","user_id":"18016440125","client_id":"100477","province":"上海市","city":"上海","block":"普陀小区1","location":"中国上海外滩SOHO","region":21,"rooms":[{"room_id":"5909a96c56837f2c1906b139","room_name":"101"}]}
         * subscribe : {"name":"张三","phone":"18605167788","date":2017,"time":"101","note":"我是备注"}
         */

        private String ticket_id;
        private int ticket_state;
        private int service_type;
        private int service_target;
        private boolean public_auth;
        private RelateHomeBean relate_home;
        private SubscribeBean subscribe;

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        public int getTicket_state() {
            return ticket_state;
        }

        public void setTicket_state(int ticket_state) {
            this.ticket_state = ticket_state;
        }

        public int getService_type() {
            return service_type;
        }

        public void setService_type(int service_type) {
            this.service_type = service_type;
        }

        public int getService_target() {
            return service_target;
        }

        public void setService_target(int service_target) {
            this.service_target = service_target;
        }

        public boolean isPublic_auth() {
            return public_auth;
        }

        public void setPublic_auth(boolean public_auth) {
            this.public_auth = public_auth;
        }

        public RelateHomeBean getRelate_home() {
            return relate_home;
        }

        public void setRelate_home(RelateHomeBean relate_home) {
            this.relate_home = relate_home;
        }

        public SubscribeBean getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(SubscribeBean subscribe) {
            this.subscribe = subscribe;
        }

        public static class RelateHomeBean {
            /**
             * home_id : 59099cae56837f2c19066049
             * home_alias : AA001
             * home_name : 测试新增集中式公寓1
             * user_id : 18016440125
             * client_id : 100477
             * province : 上海市
             * city : 上海
             * block : 普陀小区1
             * location : 中国上海外滩SOHO
             * region : 21
             * rooms : [{"room_id":"5909a96c56837f2c1906b139","room_name":"101"}]
             */

            private String home_id;
            private String home_alias;
            private String home_name;
            private String user_id;
            private String client_id;
            private String province;
            private String city;
            private String block;
            private String location;
            private int region;
            private List<RoomsBean> rooms;

            public String getHome_id() {
                return home_id;
            }

            public void setHome_id(String home_id) {
                this.home_id = home_id;
            }

            public String getHome_alias() {
                return home_alias;
            }

            public void setHome_alias(String home_alias) {
                this.home_alias = home_alias;
            }

            public String getHome_name() {
                return home_name;
            }

            public void setHome_name(String home_name) {
                this.home_name = home_name;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getClient_id() {
                return client_id;
            }

            public void setClient_id(String client_id) {
                this.client_id = client_id;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getBlock() {
                return block;
            }

            public void setBlock(String block) {
                this.block = block;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getRegion() {
                return region;
            }

            public void setRegion(int region) {
                this.region = region;
            }

            public List<RoomsBean> getRooms() {
                return rooms;
            }

            public void setRooms(List<RoomsBean> rooms) {
                this.rooms = rooms;
            }

            public static class RoomsBean {
                /**
                 * room_id : 5909a96c56837f2c1906b139
                 * room_name : 101
                 */

                private String room_id;
                private String room_name;

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
            }
        }

        public static class SubscribeBean {
            /**
             * name : 张三
             * phone : 18605167788
             * date : 2017
             * time : 101
             * note : 我是备注
             */

            private String name;
            private String phone;
            private int date;
            private String time;
            private String note;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }
        }
    }
}
