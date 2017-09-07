package com.hardware.enums;

/**
 * 硬件安装状态枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum InstallStatus {

	INSTALL_READY("待安装"), INSTALL_DONE("可使用");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private InstallStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}