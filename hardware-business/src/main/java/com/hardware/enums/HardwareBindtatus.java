package com.hardware.enums;

/**
 * 硬件绑定状态枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum HardwareBindtatus {

	BIND("已绑定"), UNBIND("已解绑");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareBindtatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}