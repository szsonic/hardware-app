package com.hardware.enums;

public enum HardwareUnInstallStatus {


 WAIT("待卸除确认"), DONE("卸除完成"),FAIL("失败");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareUnInstallStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
