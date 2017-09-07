package com.hardware.domain;

import java.util.HashMap;
import java.util.Map;

public class SurplusRes {
	private String msg;
	private Map<String,String> data=new HashMap<String,String>();
	public String getMsg() {
		return msg;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public SurplusRes(String msg,Map<String,String> data)
	{
		this.msg=msg;
		this.data=data;
	}
	public SurplusRes(String msg, String devId, String surplus) {
		// TODO Auto-generated constructor stub
		this.msg=msg;
		data.put("devId", devId);
		data.put("surplus", surplus);
	}

}
