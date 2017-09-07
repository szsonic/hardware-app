package com.hardware.enums;

/**
 * 门的材质
 */
public enum DoorMaterial {

	DOOR_WOOD("木质门"), DOOR_IRON("铁质门");

	// 枚举说明
	private String desc;


	private String Name;

	/**
	 * 私有的构造方法
	 */
	private DoorMaterial(String desc) {
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