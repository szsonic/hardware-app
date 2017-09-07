package com.hardware.model.guojia.enums;

/**
 * <br>
 * 果加门锁类型枚举
 * @author sunzhongshuai
 */
public enum DoorLockType {
    First433("0"),//第一代433门锁
    BlueTooth("1"),//蓝牙门锁
    Second433("3");//第二代433门锁

    private String value;

    DoorLockType(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
