package com.hardware.domain;

import java.io.Serializable;

/**
 * 接收蜂电平台登录成功后返回的数据
 * 
 * @author zhongqi
 *
 */
public class AmmterUserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	private String expand;
	private AmmterUserData data;

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

	public AmmterUserData getData() {
		return data;
	}

	public void setData(AmmterUserData data) {
		this.data = data;
	}

	public static class AmmterUserData {
		private String uuid;
		private String userid;

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

	}

}
