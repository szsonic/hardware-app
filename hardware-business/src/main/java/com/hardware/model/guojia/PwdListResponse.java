package com.hardware.model.guojia;

import java.util.List;

/**
 * <br>
 * 果加密码列表返回实体
 * @author sunzhongshuai
 */
public class PwdListResponse extends BaseResponse{



    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {


        private String pwd_user_mobile;
        private String pwd_text;
        private String lock_no;
        private Long valid_time_start;
        private String name;
        private Integer pwd_no;
        private Long valid_time_end;
        private String status;

        public String getPwd_user_mobile() {
            return pwd_user_mobile;
        }

        public void setPwd_user_mobile(String pwd_user_mobile) {
            this.pwd_user_mobile = pwd_user_mobile;
        }

        public String getPwd_text() {
            return pwd_text;
        }

        public void setPwd_text(String pwd_text) {
            this.pwd_text = pwd_text;
        }

        public String getLock_no() {
            return lock_no;
        }

        public void setLock_no(String lock_no) {
            this.lock_no = lock_no;
        }

        public Long getValid_time_start() {
            return valid_time_start;
        }

        public void setValid_time_start(Long valid_time_start) {
            this.valid_time_start = valid_time_start;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPwd_no() {
            return pwd_no;
        }

        public void setPwd_no(Integer pwd_no) {
            this.pwd_no = pwd_no;
        }

        public Long getValid_time_end() {
            return valid_time_end;
        }

        public void setValid_time_end(Long valid_time_end) {
            this.valid_time_end = valid_time_end;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
