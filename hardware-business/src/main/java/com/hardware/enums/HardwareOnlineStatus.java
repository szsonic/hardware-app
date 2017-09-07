package com.hardware.enums;

/**
 * 硬件在线状态枚举类<br>
 * 
 * @author fanjunjian
 * 
 */
public enum HardwareOnlineStatus {

	ON("在线"), OFF("离线");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareOnlineStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}