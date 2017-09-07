package com.hardware.enums;

/**
 * 硬件状态枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum HardwareStatus {

	ZC("正常"), YC("异常"), WZC("未注册"), SD("锁定"), ONLINE("在线"), OFFLINE("离线"), ON("在线"), OFF("离线");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}