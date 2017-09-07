package com.hardware.enums;

/**
 * 房源类型枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum HouseStructureType {

	FENSAN("分散式"), JIZHONG("集中式");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HouseStructureType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}