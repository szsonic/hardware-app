package com.hardware.enums;

/**
 * <br>
 * 成功，失败，异常，等待回调
 * @author sunzhongshuai
 */
public enum PwdOpStatus {
    SUCCESS("SUCCESS"), FAIL("FAIL"), EXCEPTION("EXCEPTION");

    // 枚举说明
    private String value;


    PwdOpStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
