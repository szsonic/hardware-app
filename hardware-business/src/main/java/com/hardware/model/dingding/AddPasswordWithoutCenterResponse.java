package com.hardware.model.dingding;

/**
 * 下发激活码返回实体 <br>
 *
 * @author sunzhongshuai
 */
public class AddPasswordWithoutCenterResponse extends BaseResponse{
    /**
     * 密码id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
