package com.hardware.domain;

public class SurplusResponse {
	private String code;
	private SurplusRes res;

	public void setCode(String code) {
		this.code = code;
	}

	public SurplusRes getRes() {
		return res;
	}

	public void setRes(SurplusRes res) {
		this.res = res;
	}

	public String getCode() {
		return code;
	}
	public SurplusResponse(String code,SurplusRes res)
	{
		this.code=code;
		this.res=res;
	}
}
