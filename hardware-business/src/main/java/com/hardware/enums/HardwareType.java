package com.hardware.enums;

/**
 * 渠道类型枚举类<br>
 * 
 * @author zhongqi
 * 
 */
public enum HardwareType {

	DIANBIAO("电表"), MENSUO("门锁"), POS("POS机"), LUYOUQI("路由器"),MENSUOPEIJIAN("门锁配件"),DIANBIAOPEIJIAN("电表配件")
	,LUYOUQIPEIJIAN("路由器配件"),SIM("SIM卡"),SIMPEIJIAN("SIM卡配件"),QITA("其他");

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