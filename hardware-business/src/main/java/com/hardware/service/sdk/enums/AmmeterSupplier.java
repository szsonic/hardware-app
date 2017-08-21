package com.hardware.service.sdk.enums;

/**
 * <br>
 * 电表供应商枚举类型
 * @author sunzhongshuai
 */
public enum AmmeterSupplier {
    DINGDING("DINGDING"),CHAOYI("CHAOYI"),FENGDIAN("FENGDIAN");

    private String code;

    AmmeterSupplier(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
