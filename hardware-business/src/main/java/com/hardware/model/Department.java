package com.hardware.model;

import com.support.model.base.BaseModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 部门信息模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "department")
@org.hibernate.annotations.Table(appliesTo = "department", comment = "部门信息模型")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Department extends BaseModel {
	private static final long serialVersionUID = -1810543951094434261L;

	/**
	 * 部门名称
	 */
	@Column(columnDefinition = "varchar(32) comment '部门名称'")
	private String departName;
	/**
	 * 备注说明
	 */
	@Column(columnDefinition = "varchar(128) comment '备注说明'")
	private String remark;

	/**
	 * 部门所属角色集合
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "departs_roles", joinColumns = { @JoinColumn(name = "departId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	private List<Role> roles;

	/**
	 * 部门所属管理员集合
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "departs_users", joinColumns = { @JoinColumn(name = "departId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
	private List<User> users;

	/**
	 * 部门所属的权限集合
	 */
	@OneToMany(mappedBy = "depart", cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<Access> accesses;


	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Access> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}

}
