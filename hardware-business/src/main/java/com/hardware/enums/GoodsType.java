package com.hardware.enums;

/**
 * <br>
 * 商品类型
 * @author sunzhongshuai
 */
public enum GoodsType {
    PRODUCT("产品"),PARTS("配件");

    private String value;

    GoodsType(String value) {
        this.value = value;
    }


}
