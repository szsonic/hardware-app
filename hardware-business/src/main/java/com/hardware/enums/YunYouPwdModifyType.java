package com.hardware.enums;

/**
 * <br>
 * 云柚更改门锁密码状态，0：解冻，1：冻结
 * @author sunzhongshuai
 */
public enum YunYouPwdModifyType {

    UNFROZEN(0), FROZEN(1);

    // 枚举说明
    private Integer value;

    /**
     * 私有的构造方法
     */
    private YunYouPwdModifyType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
