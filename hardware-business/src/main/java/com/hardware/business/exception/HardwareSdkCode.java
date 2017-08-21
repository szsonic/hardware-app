package com.hardware.business.exception;

public enum HardwareSdkCode {

	EXPIRED(-2, "硬件绑定过期");

	private HardwareSdkCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code;
	private String msg;

	public int getStatus() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
