package com.hardware.enums;

/**
 * 操作日志动作枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum OperationLogAction {

	DUANDIAN("断电"), TONGDIAN("通电");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private OperationLogAction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}