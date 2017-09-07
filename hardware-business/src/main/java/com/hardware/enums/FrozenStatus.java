package com.hardware.enums;

/**
 * <br>
 * 密码冻结状态，0：未冻结，1：冻结
 * @author sunzhongshuai
 */
public enum  FrozenStatus {
    UNFROZEN(0),FROZEN(1);

    // 枚举说明
    private Integer value;


    FrozenStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
