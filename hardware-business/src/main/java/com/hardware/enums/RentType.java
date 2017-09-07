package com.hardware.enums;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public enum RentType {

    ZHENGZU("整租"), HEZU("合租");

    // 枚举说明
    private String value;


    RentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
