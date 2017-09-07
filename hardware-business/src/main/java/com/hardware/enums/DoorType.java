package com.hardware.enums;

/**
 * 房门类型枚举类<br>
 * 
 * @author shenpeng
 * 
 */
public enum DoorType {

	XIAOMEN("小门"), DAMEN("大门");

	// 枚举说明
	private String desc;
	
	
	private String Name;
	
	/**
	 * 私有的构造方法
	 */
	private DoorType(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取说明内容
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * 获取说明内容
	 */
	public String getName() {
		return this.name();
	}
}