package com.hardware.model.dingding;

/**
 * 丁盯新增公寓数据模型，只用于传输数据，不作映射操作<br>
 * 新增公寓时，以下所有属性都必填
 * @author sunzhongshuai
 */
public class AddHomeRequest {
    /**
     * 公寓类型，1：分散式，2：集中式。如果为空，默认为分散式
     */
    private Integer homeType;

    /**
     * 公寓所在国家
     */
    private String country;

    /**
     * 公寓所在城市
     */
    private String city;

    /**
     * 公寓所在区域
     */
    private String zone;

    /**
     * 公寓所在位置（最好精确到门牌号）
     */
    private String location;

    /**
     * 小区名
     */
    private String block;

    /**
     * 公寓唯一标示
     */
    private String homeId;

    /**
     * 公寓名称
     */
    private String homeName;

    /**
     * 公寓描述
     */
    private String description;

    public Integer getHomeType() {
        return homeType;
    }

    public void setHomeType(Integer homeType) {
        this.homeType = homeType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  enum HouseType {
        FENSAN(1), JIZHONG(2);

        // 枚举说明
        private Integer value;

        /**
         * 私有的构造方法
         */
        HouseType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
