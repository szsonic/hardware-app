package com.hardware.enums;

/**
 * 电表类型枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum AmmeterType {

	TERMINAL("集中器"), NODE("节点");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private AmmeterType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}