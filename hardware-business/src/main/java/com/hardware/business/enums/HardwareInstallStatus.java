package com.hardware.business.enums;

public enum HardwareInstallStatus {


	NO_APPLY("未申请"),WAIT("待安装"),APPLIED("申请通过"), DONE("安装完成"),FAIL_ALREADY("失败,该设备已安装"),FAIL("失败");


	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareInstallStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}