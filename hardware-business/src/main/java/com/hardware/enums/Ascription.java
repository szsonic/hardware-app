package com.hardware.enums;

/**
 * 操作日志所属枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum Ascription {

	sitepass("设置密码"), openlock("开锁"),recharge("电表充值");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private Ascription(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}