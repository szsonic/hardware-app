package com.hardware.model.yunyou;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 * json转换实体-城市
 * @author sunzhongshuai
 */
public class City {

    @JsonProperty("city_id")
    private Integer city_id;
    private String name;
    private Integer pro_id;
    @JsonProperty("city_sort")
    private Integer city_sort;

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPro_id() {
        return pro_id;
    }

    public void setPro_id(Integer pro_id) {
        this.pro_id = pro_id;
    }

    public Integer getCity_sort() {
        return city_sort;
    }

    public void setCity_sort(Integer city_sort) {
        this.city_sort = city_sort;
    }
}
