package com.support.model;
/**
 * 排序枚举(ASC升序，DESC降序)
 *
 * @author shenpeng
 *
 */
public enum OrderSort {
    ASC("asc"), DESC("desc");

    private String sort;

    private OrderSort(String sort) {
        this.sort = sort;
    }

    public String getDesc() {
        return this.sort;
    }

}
