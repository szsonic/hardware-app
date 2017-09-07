package com.hardware.enums;

/**
 * 项目用户状态枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum ProjectsMembersStatus {

	NOOPERRATION("不可操作"), YOPERRATION("可操作");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private ProjectsMembersStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}