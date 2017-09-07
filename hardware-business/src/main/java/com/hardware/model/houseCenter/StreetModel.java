package com.hardware.model.houseCenter;

import java.io.Serializable;

/**
 * 街道数据模型
 * @author zhouyu
 * @date 2017-4-10
 *
 */
public class StreetModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3600132505298108926L;
	/**
	 * 街道id
	 */
	public String streetId;	
	/**
	 * 街道名称
	 */
	public String streetName;	
	/**
	 * 街道门牌号
	 */
	private String streetNumber;
	/**
	 * 街道后缀
	 */
	private String streetSuffix;
	/**
	 * 城市名称
	 */
	public String cityName;	
	/**
	 * 区域id
	 */
	private String areaId;
	/**
	 * 区县名称
	 */
	public String cityAreaName;
	/**
	 * 省份id
	 */
	private String provinceId;
	
	public String getStreetId() {
		return streetId;
	}
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getStreetSuffix() {
		return streetSuffix;
	}
	public void setStreetSuffix(String streetSuffix) {
		this.streetSuffix = streetSuffix;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getCityAreaName() {
		return cityAreaName;
	}
	public void setCityAreaName(String cityAreaName) {
		this.cityAreaName = cityAreaName;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	
}
