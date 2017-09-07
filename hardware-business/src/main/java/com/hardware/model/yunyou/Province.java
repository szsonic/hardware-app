package com.hardware.model.yunyou;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 * json转换实体-省
 * @author sunzhongshuai
 */
public class Province {

    @JsonProperty("pro_id")
    private Integer pro_id;
    private String name;
    @JsonProperty("pro_sort")
    private Integer pro_sort;
    @JsonProperty("pro_remark")
    private String pro_remark;

    public Integer getPro_id() {
        return pro_id;
    }

    public void setPro_id(Integer pro_id) {
        this.pro_id = pro_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPro_sort() {
        return pro_sort;
    }

    public void setPro_sort(Integer pro_sort) {
        this.pro_sort = pro_sort;
    }

    public String getPro_remark() {
        return pro_remark;
    }

    public void setPro_remark(String pro_remark) {
        this.pro_remark = pro_remark;
    }
}
