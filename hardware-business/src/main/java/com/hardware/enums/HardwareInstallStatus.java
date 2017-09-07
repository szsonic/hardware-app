package com.hardware.enums;


import com.utils.ValidatorUtils;

public enum HardwareInstallStatus {


	NO_APPLY("未申请"),WAIT("待安装"),REJECTED("订单驳回"),APPLIED("申请通过"), DONE("安装完成"),FAIL_ALREADY("失败,该设备已安装"),FAIL("失败");


	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private HardwareInstallStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	//判断订单是否在处理中
	public static boolean isOrderInProcessing(HardwareInstallStatus orderStatus){
		if(ValidatorUtils.isEmpty(orderStatus)){
			return false;
		}

		switch (orderStatus){
			case WAIT: return true;
			case APPLIED: return true;

			default: return false;
		}
	}
}