package com.hardware.enums;


public enum PwdStatus {
WAIT_CALLBACK("等待回调"),CALLBACK_SUCCESS("回调成功"),CALLBACK_FAIL("回调失败");

    // 枚举说明
    private String value;


    PwdStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
