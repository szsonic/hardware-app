package com.hardware.model.guojia;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class OpenLogResponse extends BaseResponse {

    /**
     * data : {"total":5,"start":0,"total_page":1,"rows":[{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499849678000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499849343000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499848865000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499848780000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499848740000,"pwd_no":1}],"current_page":1,"page_size":10}
     * rlt_code : HH0000
     * rlt_msg : 成功
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 5
         * start : 0
         * total_page : 1
         * rows : [{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499849678000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499849343000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499848865000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499848780000,"pwd_no":1},{"op_way":"1","op_user_id":"1582cd1ada6054","user_mobile":"13817089683","op_time":1499848740000,"pwd_no":1}]
         * current_page : 1
         * page_size : 10
         */

        private Integer total;
        private Integer start;
        private Integer total_page;
        private Integer current_page;
        private Integer page_size;
        private List<OpenLog> rows;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getStart() {
            return start;
        }

        public void setStart(Integer start) {
            this.start = start;
        }

        public Integer getTotal_page() {
            return total_page;
        }

        public void setTotal_page(Integer total_page) {
            this.total_page = total_page;
        }

        public Integer getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(Integer current_page) {
            this.current_page = current_page;
        }

        public Integer getPage_size() {
            return page_size;
        }

        public void setPage_size(Integer page_size) {
            this.page_size = page_size;
        }

        public List<OpenLog> getRows() {
            return rows;
        }

        public void setRows(List<OpenLog> rows) {
            this.rows = rows;
        }

        public static class OpenLog {
            /**
             * op_way : 1
             * op_user_id : 1582cd1ada6054
             * user_mobile : 13817089683
             * op_time : 1499849678000
             * pwd_no : 1
             */

            private String op_way;
            private String op_user_id;
            private String user_mobile;
            private Long op_time;
            private Integer pwd_no;

            public String getOp_way() {
                return op_way;
            }

            public void setOp_way(String op_way) {
                this.op_way = op_way;
            }

            public String getOp_user_id() {
                return op_user_id;
            }

            public void setOp_user_id(String op_user_id) {
                this.op_user_id = op_user_id;
            }

            public String getUser_mobile() {
                return user_mobile;
            }

            public void setUser_mobile(String user_mobile) {
                this.user_mobile = user_mobile;
            }

            public Long getOp_time() {
                return op_time;
            }

            public void setOp_time(Long op_time) {
                this.op_time = op_time;
            }

            public Integer getPwd_no() {
                return pwd_no;
            }

            public void setPwd_no(Integer pwd_no) {
                this.pwd_no = pwd_no;
            }
        }
    }
}
