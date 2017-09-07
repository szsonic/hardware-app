package com.hardware.model.houseCenter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 最终房源模型
 * 
 * @author zhouyu
 * @date 2017-4-9
 *
 */
public class FinalHouseSourceModel implements Serializable {

	public static final String TABALENAME = "final_house";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1934923853459496909L;
	/**
	 * 主键
	 */
	public String rowKey;
	/**
	 * 房源id
	 */
	public String houseId;
	/**
	 * 省
	 */
	public String provinceId;
	/**
	 * 省份名称
	 */
	public String provinceName;
	/**
	 * 市
	 */
	public String cityId;
	/**
	 * 城市名称
	 */
	public String cityName;
	/**
	 * 城市区县id
	 */
	public String cityAreaId;
	/**
	 * 城市区县名称
	 */
	public String cityAreaName;

	/**
	 * 街道id
	 */
	public String streetId;
	/**
	 * 街道
	 */
	public String streetName;

	/**
	 * 街道名称
	 */
	public String streetAddress;
	/**
	 * 镇
	 */
	public String townId;
	/**
	 * 小区id
	 */
	public String villageId;
	/**
	 * 小区名称
	 */
	public String villageName;

	/**
	 * 楼栋号
	 */
	public String buildingNumber;

	/**
	 * 楼栋ID
	 */
	public String buildingId;

	/**
	 * 单元号
	 */
	public String unitNumber;

	/**
	 * 单元号
	 */
	public String unitId;
	/**
	 * 总楼层
	 */
	public String floors;
	/**
	 * 所在楼层
	 */
	public String currentFloor;
	/**
	 * 房屋朝向
	 */
	public String houseDirection;
	/**
	 * 房号
	 */
	public String houseNumber;
	/**
	 * 房屋类型
	 */
	public String houseType;
	/**
	 * 公寓分布信息
	 */
	public String apartmentStyle;

	/**
	 * 房屋来源
	 */
	public String houseSource;
	
	private List<FinalHouseRoomModel> rooms;

	public List<FinalHouseRoomModel> getRooms() {
		return rooms;
	}

	public void setRooms(List<FinalHouseRoomModel> rooms) {
		this.rooms = rooms;
	}

	public String getHouseSource() {
		return houseSource;
	}

	public void setHouseSource(String houseSource) {
		this.houseSource = houseSource;
	}

	public String getApartmentStyle() {
		return apartmentStyle;
	}

	public void setApartmentStyle(String apartmentStyle) {
		this.apartmentStyle = apartmentStyle;
	}

	/**
	 * 状态[空闲、出租、发布等等]
	 */
	public String status;

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getStreetId() {
		return streetId;
	}

	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityAreaId() {
		return cityAreaId;
	}

	public void setCityAreaId(String cityAreaId) {
		this.cityAreaId = cityAreaId;
	}

	public String getCityAreaName() {
		return cityAreaName;
	}

	public void setCityAreaName(String cityAreaName) {
		this.cityAreaName = cityAreaName;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getVillageId() {
		return villageId;
	}

	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getFloors() {
		return floors;
	}

	public void setFloors(String floors) {
		this.floors = floors;
	}

	public String getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(String currentFloor) {
		this.currentFloor = currentFloor;
	}

	public String getHouseDirection() {
		return houseDirection;
	}

	public void setHouseDirection(String houseDirection) {
		this.houseDirection = houseDirection;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	// 获取房源key
	public String getHouseKey() {
		return UUID.randomUUID().toString();
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

}
