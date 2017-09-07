package com.hardware.domain;

import java.io.Serializable;

/**
 * 接收蜂电平台返回的数据
 * 
 * @author zhongqi
 *
 */
public class AmmeterLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 蜂电返回的code 0 成功
	 */
	private String date;
	private String electricity;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getElectricity() {
		return electricity;
	}

	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}

}
