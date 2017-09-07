package com.hardware.enums;

/**
 * 丁盯设备安装type类型<br>
 *
 * @author sunzhongshuai
 */
public enum DingDingHardwareType {
    GATEWAY(1), LOCK(4),AMMETER(5),COLLECTOR(6);

    // 枚举说明
    private Integer value;

    /**
     * 私有的构造方法
     */
    DingDingHardwareType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
