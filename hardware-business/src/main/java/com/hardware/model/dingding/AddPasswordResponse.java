package com.hardware.model.dingding;

/**
 * 新增密码返回实体<br>
 *
 * @author sunzhongshuai
 */
public class AddPasswordResponse extends BaseResponse{
    /**
     * 密码id，在此设备中唯一
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
