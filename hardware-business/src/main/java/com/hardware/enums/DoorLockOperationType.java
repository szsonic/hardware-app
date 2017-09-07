package com.hardware.enums;

/**
 * <br>
 * 门锁操作记录类型
 * @author sunzhongshuai
 */
public enum DoorLockOperationType {
    //冻结(回调)，解冻(回调)，新增密码(回调)，删除密码(回调),修改密码（回调）
    FROZEN("FROZEN"), UNFROZEN("UNFROZEN"),ADDPASSWORD("ADDPASSWORD"),DELPASSWORD("DELPASSWORD"),MODIFYPASSWORD("MODIFYPASSWORD"),
    FROZEN_CALLBACK("FROZEN_CALLBACK"), UNFROZEN_CALLBACK("UNFROZEN_CALLBACK"),ADDPASSWORD_CALLBACK("ADDPASSWORD_CALLBACK"),DELPASSWORD_CALLBACK("DELPASSWORD_CALLBACK"),MODIFYPASSWORD_CALLBACK("MODIFYPASSWORD_CALLBACK");
    // 枚举说明
    private String value;


    DoorLockOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
