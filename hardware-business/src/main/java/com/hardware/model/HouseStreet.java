package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 房源街道信息模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "house_street")
@org.hibernate.annotations.Table(appliesTo = "house_street", comment = "房源街道信息模型")
public class HouseStreet extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 省份
	 */
	@Column(columnDefinition = "varchar(16) comment '省份id'")
	private String provinceId;
	
	
	/**
	 * 省份
	 */
	@Column(columnDefinition = "varchar(56) comment '省份Name'")
	private String provinceName;
	/**
	 * 城市
	 */
	@Column(columnDefinition = "varchar(16) comment '城市id'")
	private String cityId;
	
	
	/**
	 * 城市
	 */
	@Column(columnDefinition = "varchar(56) comment '城市Name'")
	private String cityName;
	/**
	 * 区
	 */
	@Column(columnDefinition = "varchar(16) comment '区'")
	private String dist;
	
	/**
	 * 区
	 */
	@Column(columnDefinition = "varchar(16) comment '区Name'")
	private String distName;

	/**
	 * 街道名称
	 */
	@Column(columnDefinition = "varchar(512) comment '街道名称'")
	private String street;
	
	
	/**
	 * 街道地址
	 */
	@Column(columnDefinition = "varchar(512) comment '街道地址'")
	private String address;
	/**
	 * 签名
	 */
	@Column(columnDefinition = "varchar(512) comment '签名'")
	private String sign;
	
	/**
	 * 街道sourceCode
	 */
	@Column(columnDefinition = "varchar(50) comment '街道sourceCode'")
	private String sourceHouseStreetCode;
	
	/**
	 * 房源街道拥有的小区集合
	 */
	@OneToMany(mappedBy = "houseStreet", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<HouseDistrict> houseDistricts;
	
	
	
	
	public String getSourceHouseStreetCode() {
		return sourceHouseStreetCode;
	}

	public void setSourceHouseStreetCode(String sourceHouseStreetCode) {
		this.sourceHouseStreetCode = sourceHouseStreetCode;
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

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
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

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public List<HouseDistrict> getHouseDistricts() {
		return houseDistricts;
	}

	public void setHouseDistricts(List<HouseDistrict> houseDistricts) {
		this.houseDistricts = houseDistricts;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}