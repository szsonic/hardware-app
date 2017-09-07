package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

/**
 * 访问权限信息模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "access")
@org.hibernate.annotations.Table(appliesTo = "access", comment = "访问权限信息模型")
public class Access extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门ID
	 */
	@Column(columnDefinition = "varchar(32) comment '部门ID'")
	private String departId;

	/**
	 * 角色组ID
	 */
	@Column(columnDefinition = "varchar(32) comment '角色组ID'")
	private String roleId;
	/**
	 * 菜单ID
	 */
	@Column(columnDefinition = "varchar(32) comment '菜单ID'")
	private String menuId;

	/**
	 * 菜单方法ID
	 */
	@Column(columnDefinition = "varchar(32) comment '菜单方法ID'")
	private String menuMethodId;
	/**
	 * 用户ID
	 */
	@Column(columnDefinition = "varchar(32) comment '用户ID'")
	private String userId;

	/**
	 * 部门信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "departId", nullable = true, insertable = false, updatable = false)
	private Department depart;

	/**
	 * 角色信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId", nullable = true, insertable = false, updatable = false)
	private Role role;
	/**
	 * 用户信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = true, insertable = false, updatable = false)
	private User user;
	/**
	 * 菜单信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "menuId", nullable = true, insertable = false, updatable = false)
	private Menu menu;

	/**
	 * 菜单方法信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "menuMethodId", nullable = true, insertable = false, updatable = false)
	private MenuMethod menuMethod;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMenuMethodId() {
		return menuMethodId;
	}

	public void setMenuMethodId(String menuMethodId) {
		this.menuMethodId = menuMethodId;
	}

	public MenuMethod getMenuMethod() {
		return menuMethod;
	}

	public void setMenuMethod(MenuMethod menuMethod) {
		this.menuMethod = menuMethod;
	}

}
