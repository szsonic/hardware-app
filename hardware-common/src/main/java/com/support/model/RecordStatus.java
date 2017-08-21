package com.support.model;

/**
 * 通用记录状态列枚举类<br>
 * AVAILABLE("可用"), FORBID("禁止"), DELETE("删除")
 *
 * @author shenpeng
 *
 */
public enum RecordStatus {
    AVAILABLE("可用"), FORBID("禁止"), DELETE("删除");

    // 枚举说明
    private String desc;

    /**
     * 私有的构造方法
     */
    private RecordStatus(String desc) {
        this.desc = desc;
    }

    /**
     * 获取说明内容
     */
    public String getDesc() {
        return desc;
    }
}
