package com.hardware.domain;



import com.hardware.model.Ammeter;
import com.hardware.model.DoorLock;
import com.hardware.model.Hardware;
import com.hardware.model.House;

import java.io.Serializable;

/**
 * 创建电表信息时做中间数据传输。 包含（1、设备信息 2、房源信息 3、具体的电表信息）
 * 
 * @author zhongqi
 * 
 */
public class AmmeterHardDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Ammeter ammeter;
	private DoorLock doorLock;
	private Hardware hardware;
	private House house;

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

	public Hardware getHardware() {
		return hardware;
	}

	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

}
