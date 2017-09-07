package com.hardware.enums;

/**
 * 硬件监测工单状态
 * 
 */
public enum HardwareStatusCheckOrderStatus {

	CREATED("创建"), DISTRIBUTED("已分配"),
	//CONFIRMED("已确认"), CHECKING("监工中"), 
	DONE("完成");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareStatusCheckOrderStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}