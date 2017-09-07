package com.hardware.model.dingding;

/**
 * <br>
 * 管理员密码明文接口返回实体，对应接口文档6.5
 * @author sunzhongshuai
 */
public class GetDefaultPasswordPlaintextResponse extends BaseResponse{
    /**
     * 密码明文
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
