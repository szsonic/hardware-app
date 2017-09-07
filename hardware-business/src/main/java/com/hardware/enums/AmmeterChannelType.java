package com.hardware.enums;

/**
 * 电表渠道类型枚举类<br>
 * 
 * @author zq
 * 
 */
public enum AmmeterChannelType {

	FENGDIAN("蜂电"), DINGDING("丁盯");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private AmmeterChannelType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}