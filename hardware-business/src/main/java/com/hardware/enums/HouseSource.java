package com.hardware.enums;

/**
 * 房源来源类型枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum HouseSource {

	SQ("SQ"), SY("舍艺"), YJSH("盈家生活"),ZG("租管"), IMPORT("导入"),FD_CONTRACT("房東合同"),FD_INSTALL_HARDWARE("房東安裝鎖電"),OTHER("其它");

	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HouseSource(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}