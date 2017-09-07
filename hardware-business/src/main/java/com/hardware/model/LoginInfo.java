package com.hardware.model;

/**
 * <br>
 * 登录信息
 * @author sunzhongshuai
 */
public class LoginInfo {

    /**
     * 用户信息
     */
    private User user;

    /**
     * 登录ip
     */
    private String ip;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
