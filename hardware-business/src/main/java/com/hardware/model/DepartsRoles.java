package com.hardware.model;

/**
 * 部门和角色的中间模型(只做数据传输用不需要进行映射操作)
 * 
 * @author shenpeng
 * 
 */
public class DepartsRoles {

	/**
	 * 部门ID
	 */
	private String departId;

	/**
	 * 角色ID
	 */
	private String roleId;

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
