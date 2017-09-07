package com.hardware.enums;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public enum ThirdPartySyncAction {
    ADD("ADD"), UPDATE("UPDATE"),DELETE("DELETE");

    // 枚举说明
    private String value;

    /**
     * 私有的构造方法
     */
    private ThirdPartySyncAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
