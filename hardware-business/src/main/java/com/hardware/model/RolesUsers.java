package com.hardware.model;

/**
 * 项目和用户之间的关系表
 * 
 * @author shenpeng
 * 
 */
public class RolesUsers {

	private String roleId;

	private String userId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
