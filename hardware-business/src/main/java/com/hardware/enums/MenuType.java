package com.hardware.enums;

/**
 * 会员性别类型枚举类<br>
 * INTERNAL("内部调用"), EXTERNAL("外部调用")
 * 
 * @author shenpeng
 * 
 */
public enum MenuType {

	INTERNAL("内部调用"), EXTERNAL("外部调用");

	// 枚举说明
	private String desc;

	/**
	 * 私有的构造方法
	 */
	private MenuType(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取说明内容
	 */
	public String getDesc() {
		return desc;
	}
}
