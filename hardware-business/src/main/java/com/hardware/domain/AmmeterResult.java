package com.hardware.domain;

import java.io.Serializable;

/**
 * 接收蜂电平台返回的数据
 * 
 * @author zhongqi
 *
 */
public class AmmeterResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 蜂电返回的code 0 成功
	 */
	private String code;
	private String message;
	private String expand;
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
