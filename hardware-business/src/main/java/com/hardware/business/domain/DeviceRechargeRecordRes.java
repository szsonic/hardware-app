package com.hardware.business.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceRechargeRecordRes {
	private String msg;
	private List<Map<String,String>> data=new ArrayList<Map<String,String>>();
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public List<Map<String, String>> getData() {
		return data;
	}
	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

}
