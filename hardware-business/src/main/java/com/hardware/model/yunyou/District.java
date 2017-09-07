package com.hardware.model.yunyou;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <br>
 * json转换实体-区
 * @author sunzhongshuai
 */
public class District {
    @JsonProperty("dis_id")
    private Integer dis_id;
    private String name;
    @JsonProperty("city_id")
    private Integer city_id;
    @JsonProperty("dis_sort")
    private Object dis_sort;

    public Integer getDis_id() {
        return dis_id;
    }

    public void setDis_id(Integer dis_id) {
        this.dis_id = dis_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Object getDis_sort() {
        return dis_sort;
    }

    public void setDis_sort(Object dis_sort) {
        this.dis_sort = dis_sort;
    }
}
