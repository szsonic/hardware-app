package com.hardware.enums;

/**
 * 门锁渠道类型枚举类<br>
 * 
 * @author zq
 * 
 */
public enum DoorlockChannelType {

	YUNYOU("云柚"), DINGDING("钉钉");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private DoorlockChannelType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}