package com.hardware.model.houseCenter;

import java.io.Serializable;

/**
 * 小区数据模型 
 * @author zhouyu
 * @date 2017-4-10
 *
 */
public class VillageModel implements Serializable{
	/**
	 * 
	 */
	public static final long serialVersionUID = 211799623064432059L;
    /**
     * 小区id
     */
	public String villageId;	
	public String rowKey;
	/**
	 * 小区名称
	 */
	public String villageName;	
	/**
	 * 省份id
	 */
	private String provinceId;
	/**
	 * 省份名称
	 */
	public String provinceName;
	/**
	 * 城市id
	 */
	private String cityId;	
	/**
	 * 城市名称
	 */
	public String cityName;
	/**
	 * 城市区县表
	 */
	private String cityAreaId;
	/**
	 * 挂牌数量
	 */
	public String saleCount;
	/**
	 * 成交数量
	 */
	public String dealCount;
	/**
	 * 城市区县名称
	 */
	public String cityAreaName;
	/**
	 * 城市区域id
	 */
	private String areaId;	
	/**
	 * 镇id
	 */
	private String townId;	
	/**
	 * 街道id
	 */
	private String streetId;
	/**
	 * 地址
	 */
	private String address;	
	/**
	 * 经度
	 */
	private String longitude;	
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 所属商圈
	 */
	private String districtId;	
	/**
	 * 所属板块
	 */
	private String plateId;	
	/**
	 * 板块名称
	 */
	public String plateName;
	/**
	 * 小区类型
	 */
	private String villageType;
	
	
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(String saleCount) {
		this.saleCount = saleCount;
	}
	public String getDealCount() {
		return dealCount;
	}
	public void setDealCount(String dealCount) {
		this.dealCount = dealCount;
	}
	public String getCityAreaName() {
		return cityAreaName;
	}
	public void setCityAreaName(String cityAreaName) {
		this.cityAreaName = cityAreaName;
	}
	public String getPlateName() {
		return plateName;
	}
	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}
	public String getVillageId() {
		return villageId;
	}
	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}
	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getTownId() {
		return townId;
	}
	public void setTownId(String townId) {
		this.townId = townId;
	}
	public String getStreetId() {
		return streetId;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getPlateId() {
		return plateId;
	}
	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}
	public String getVillageType() {
		return villageType;
	}
	public void setVillageType(String villageType) {
		this.villageType = villageType;
	}	
	public String getCityAreaId() {
		return cityAreaId;
	}
	public void setCityAreaId(String cityAreaId) {
		this.cityAreaId = cityAreaId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
