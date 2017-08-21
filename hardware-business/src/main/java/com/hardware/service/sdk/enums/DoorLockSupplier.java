package com.hardware.service.sdk.enums;

/**
 * <br>
 * 门锁供应商枚举类型
 * @author sunzhongshuai
 */
public enum DoorLockSupplier {
    DINGDING("DINGDING"),GUOJIA("GUOJIA"),YUNYOU("YUNYOU"),YUNYU("YUNYU");
    private String code;

    DoorLockSupplier(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
