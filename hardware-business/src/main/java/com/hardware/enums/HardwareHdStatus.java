package com.hardware.enums;

/**
 * 硬件状态枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum HardwareHdStatus {

	AMMETER_ON("已通电"), AMMETER_OFF("已断电"), DOORLOCK_OPEN("已开锁"), DOORLOCK_CLOSE("已关锁");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareHdStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}