package com.hardware.enums;

public enum RentHouseAction {
	
	RENT("租房"), UNRENT("退租");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private RentHouseAction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
