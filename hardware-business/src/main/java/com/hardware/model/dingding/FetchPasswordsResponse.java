package com.hardware.model.dingding;



import java.util.LinkedHashMap;


/**
 * 获取门锁密码列表返回实体，对应接口文档6.4 <br>
 *
 * @author sunzhongshuai
 */
public class FetchPasswordsResponse extends BaseResponse{



    private LinkedHashMap<String,Password> passwords;


    public LinkedHashMap<String,Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(LinkedHashMap<String,Password> passwords) {
        this.passwords = passwords;
    }

    public static  class  Password {
        /**
         * 密码id，密码id在此设备中唯一
         */
        private Integer id;
        /**
         * 密码名（如租户姓名）
         */
        private String name;
        /**
         * 密码创建时间时间戳，单位ms
         */
        private Long time;
        /**
         * 操作出错描述
         */
        private String description;
        /**
         * 是否管理员密码（文档没标注哪个数字代表管理员密码）
         */
        private Integer is_default;
        /**
         * 密码权限
         */
        private PermissionBean permission;

        private Integer notify;
        /**
         * 密码当前所处操作
         * 1：添加
         * 2：删除
         * 3：更新
         * 4：冻结
         * 5：解冻结
         */
        private Integer operation;
        /**
         * 当前操作所属的阶段
         * 1：进行中，等待设备反馈
         * 2：操作失败
         * 3：操作成功
         */
        private Integer operation_stage;
        /**
         * 密码当前权限状态
         * 1：正常
         * 2：有效期外
         */
        private Integer permission_state;

        private Integer status;
        /**
         * 密码状态（只有在状态2时，密码有效）
         * 1：初始状态
         * 2：已生效
         * 3：将生效
         * 4：已过期
         * 5：已冻结
         */
        private Integer pwd_state;
        /**
         * 密码生产时发送的对象（手机号等）
         */
        private String send_to;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getIs_default() {
            return is_default;
        }

        public void setIs_default(Integer is_default) {
            this.is_default = is_default;
        }

        public PermissionBean getPermission() {
            return permission;
        }

        public void setPermission(PermissionBean permission) {
            this.permission = permission;
        }

        public Integer getNotify() {
            return notify;
        }

        public void setNotify(Integer notify) {
            this.notify = notify;
        }

        public Integer getOperation() {
            return operation;
        }

        public void setOperation(Integer operation) {
            this.operation = operation;
        }

        public Integer getOperation_stage() {
            return operation_stage;
        }

        public void setOperation_stage(Integer operation_stage) {
            this.operation_stage = operation_stage;
        }

        public Integer getPermission_state() {
            return permission_state;
        }

        public void setPermission_state(Integer permission_state) {
            this.permission_state = permission_state;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getPwd_state() {
            return pwd_state;
        }

        public void setPwd_state(Integer pwd_state) {
            this.pwd_state = pwd_state;
        }

        public String getSend_to() {
            return send_to;
        }

        public void setSend_to(String send_to) {
            this.send_to = send_to;
        }
    }
    public static class PermissionBean {

        /**
         * 密码有效期的开始时间戳，单位S
         */
        private Long begin;
        /**
         * 密码有效期的结束时间戳，单位S
         */
        private Long end;
        /**
         * 密码权限类型
         * 1：永久密码
         * 2：时间有效密码
         */
        private Integer status;

        public Long getEnd() {
            return end;
        }

        public void setEnd(Long end) {
            this.end = end;
        }

        public Long getBegin() {
            return begin;
        }

        public void setBegin(Long begin) {
            this.begin = begin;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
