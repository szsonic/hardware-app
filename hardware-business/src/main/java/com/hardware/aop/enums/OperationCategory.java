package com.hardware.aop.enums;

/**
 * <br>
 * 操作所属类别
 * @author sunzhongshuai
 */
public enum OperationCategory {
    AMMETER("电表"),DOORLOCK("门锁"),ROUTER("路由器");

    private String desc;

    OperationCategory(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
