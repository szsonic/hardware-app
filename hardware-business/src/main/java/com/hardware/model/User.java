package com.hardware.model;

import com.support.model.base.BaseModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 管理员用户信息模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo = "user", comment = "管理员用户信息模型")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 管理员登录账户
	 */
	@Column(columnDefinition = "varchar(32) comment '管理员登录账户'")
	private String loginName;

	/**
	 * 管理员登录密码
	 */
	@Column(columnDefinition = "varchar(32) comment '管理员登录密码'")
	private String loginPwd;

	/**
	 * 管理员重复密码
	 */
	@Transient
	private String reloginPwd;

	/**
	 * 手机号
	 */
	@Column(columnDefinition = "varchar(32) comment '手机号'")
	private String mobile;

	/**
	 * 管理员姓名
	 */
	@Column(columnDefinition = "varchar(32) comment '管理员姓名'")
	private String userName;

	/**
	 * 邮箱
	 */
	@Column(columnDefinition = "varchar(128) comment '邮箱'")
	private String email;

	/**
	 * 工号
	 */
	@Column(columnDefinition = "varchar(32) comment '工号'")
	private String jobNumber;

	/**
	 * 管理员所属角色集合
	 */
	@ManyToMany(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<Role> roles;

	/**
	 * 管理员所属部门集合
	 */
	@ManyToMany(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<Department> departs;

	@Transient
	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getReloginPwd() {
		return reloginPwd;
	}

	public void setReloginPwd(String reloginPwd) {
		this.reloginPwd = reloginPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Department> getDeparts() {
		return departs;
	}

	public void setDeparts(List<Department> departs) {
		this.departs = departs;
	}

}
