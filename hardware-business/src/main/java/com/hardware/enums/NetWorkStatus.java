package com.hardware.enums;

public enum NetWorkStatus {


	NO_NETWORK("无网络"),HAS_NETWORK("有网络");


	// 枚举说明
	private String value;

	/**
	 * 私有的构造方法
	 */
	private NetWorkStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}