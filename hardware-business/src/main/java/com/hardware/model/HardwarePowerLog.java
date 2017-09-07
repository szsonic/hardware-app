package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "hardware_power_log")
@org.hibernate.annotations.Table(appliesTo = "hardware_power_log", comment = "硬件电量日志表")
public class HardwarePowerLog extends BaseModel {
	private static final long serialVersionUID = 1L;

	private String powerPercent;

	/**
	 * 所属电表信息（ammeterId非必须）
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH },  fetch = FetchType.LAZY)
	@JoinColumn(name = "ammeterId")
	private Ammeter ammeter;
	
	
	/**
	 * 所属门锁信息（ammeterId非必须）
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH },  fetch = FetchType.LAZY)
	@JoinColumn(name = "doorLockId")
	private DoorLock doorLock;

	public String getPowerPercent() {
		return powerPercent;
	}

	public void setPowerPercent(String powerPercent) {
		this.powerPercent = powerPercent;
	}

	public Ammeter getAmmeter() {
		return ammeter;
	}

	public void setAmmeter(Ammeter ammeter) {
		this.ammeter = ammeter;
	}

	public DoorLock getDoorLock() {
		return doorLock;
	}

	public void setDoorLock(DoorLock doorLock) {
		this.doorLock = doorLock;
	}
	
	
	
}
