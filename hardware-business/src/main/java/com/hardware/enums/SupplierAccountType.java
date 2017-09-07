package com.hardware.enums;

/**
 * 供应商账户的类型<br>
 * 
 * @author zhongqi
 * 
 */
public enum SupplierAccountType {

	PRO("正式"), TEST("测试");

	// 枚举说明
	private String desc;

	/**
	 * 私有的构造方法
	 */
	private SupplierAccountType(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取说明内容
	 */
	public String getDesc() {
		return desc;
	}
}
