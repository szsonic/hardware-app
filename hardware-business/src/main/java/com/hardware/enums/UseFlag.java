package com.hardware.enums;

/**
 * 正在使用的硬件状态枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum UseFlag {

	NORMAL("正常"), UNBIND("已解绑");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private UseFlag(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}