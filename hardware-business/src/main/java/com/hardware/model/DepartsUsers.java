package com.hardware.model;

/**
 * 部门和用户的中间模型(只做数据传输用不需要进行映射操作)
 * 
 * @author shenpeng
 * 
 */
public class DepartsUsers {

	/**
	 * 部门ID
	 */
	private String departId;

	/**
	 * 用户ID
	 */
	private String userId;

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
