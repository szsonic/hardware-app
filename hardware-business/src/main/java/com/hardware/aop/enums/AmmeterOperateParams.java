package com.hardware.aop.enums;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public enum AmmeterOperateParams {

    ACTION("操作电表"),
    PRICE("价格"),
    CHARGE("电表充值");

    private String action;

    AmmeterOperateParams(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
