package com.hardware.enums;

/**
 * 故障发生时的通知类型
 */
public enum NotifyType {

	EMAIL("邮件通知"), SMS("短信");

	// 枚举说明
	private String desc;


	private String Name;

	/**
	 * 私有的构造方法
	 */
	private NotifyType(String desc) {
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