package com.hardware.model.houseCenter;

import java.io.Serializable;

public class FinalUnitsIDModel implements Serializable{

	private static final long serialVersionUID = -6635999514356075421L;
	/**
	 * 单元号
	 */
	public String unitNumber;	
	/**
	 * 单元UUID
	 */
	public String unitUUID;
	
	public String getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getUnitUUID() {
		return unitUUID;
	}
	public void setUnitUUID(String unitUUID) {
		this.unitUUID = unitUUID;
	}
	

}
