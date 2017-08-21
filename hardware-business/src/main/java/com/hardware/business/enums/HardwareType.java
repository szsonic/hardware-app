package com.hardware.business.enums;

/**
 * 渠道类型枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum HardwareType {

	DIANBIAO("电表"), MENSUO("门锁"), POS("POS机"), LUYOUQI("路由器");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}