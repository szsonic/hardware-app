package com.hardware.model.houseCenter;

import java.io.Serializable;

public class FinalBuildingsIDModel implements Serializable{
	
	private static final long serialVersionUID = 701668267093048759L;

	/**
	 * 楼栋号
	 */
	public String buildingNumber;	
	/**
	 * 楼栋UUID
	 */
	public String buildingUUID;
	

	public String getBuildingNumber() {
		return buildingNumber;
	}
	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}
	public String getBuildingUUID() {
		return buildingUUID;
	}
	public void setBuildingUUID(String buildingUUID) {
		this.buildingUUID = buildingUUID;
	}	

}
