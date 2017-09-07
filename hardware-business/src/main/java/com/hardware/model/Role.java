package com.hardware.model;

import com.support.model.base.BaseModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.util.List;

/**
 * 角色信息模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "role")
@org.hibernate.annotations.Table(appliesTo = "role", comment = "角色信息模型")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 角色名称
	 */
	@Column(columnDefinition = "varchar(32) comment '角色名称'")
	private String roleName;
	/**
	 * 父级ID
	 */
	@Column(columnDefinition = "varchar(32) comment '父级ID'")
	private String upId;
	/**
	 * 角色等级
	 */
	@Column(columnDefinition = "int(1) default 0 not null comment '角色等级'")
	private Integer level;
	/**
	 * 备注说明
	 */
	@Column(columnDefinition = "varchar(512) comment '备注说明'")
	private String remark;

	/**
	 * 所属角色
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "upId", nullable = true, insertable = false, updatable = false)
	private Role upRole;

	/**
	 * 角色所属的权限集合
	 */
	@OneToMany(mappedBy = "role", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<Access> accesses;

	/**
	 * 角色所属管理员集合
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "roles_users", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
	private List<User> users;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUpId() {
		return upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Role getUpRole() {
		return upRole;
	}

	public void setUpRole(Role upRole) {
		this.upRole = upRole;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Access> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}

}
