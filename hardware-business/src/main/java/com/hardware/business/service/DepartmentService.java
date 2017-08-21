package com.hardware.business.service;

import com.hardware.business.model.Department;
import com.hardware.business.model.DepartsRoles;
import com.hardware.business.model.DepartsUsers;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

/**
 * 部门服务接口<br>
 * 
 * @author shenpeng
 * 
 */
public interface DepartmentService extends IBaseServiceSupport<Department, String> {

	/**
	 * 添加角色到部门
	 * 
	 * @param model
	 *            中间表实体对象
	 * @return 操作是否成功（true成功，false失败）
	 */
	public Integer addRole(DepartsRoles model);

	/**
	 * 部门中移除角色
	 * 
	 * @param model
	 *            中间表实体对象
	 * @return 操作是否成功（true成功，false失败）
	 */
	public Integer removeRole(DepartsRoles model);

	/**
	 * 添加用户到部门
	 * 
	 * @param model
	 *            中间表实体对象
	 * @return 操作是否成功（true成功，false失败）
	 */
	public Integer addUser(DepartsUsers model);

	/**
	 * 部门中移除用户
	 * 
	 * @param model
	 *            中间表实体对象
	 * @return 操作是否成功（true成功，false失败）
	 */
	public Integer removeUser(DepartsUsers model);

}
