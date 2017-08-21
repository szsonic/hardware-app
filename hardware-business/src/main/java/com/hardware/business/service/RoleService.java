package com.hardware.business.service;

import com.hardware.business.model.Role;
import com.hardware.business.model.RolesUsers;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 系统角色服务接口<br>
 * 
 * @author shenpeng
 * 
 */
public interface RoleService extends IBaseServiceSupport<Role, String> {

	/**
	 * 添加用户到角色组
	 * 
	 * @param model
	 *          中间表实体对象
	 * @return 操作是否成功（true成功，false失败）
	 */
	public Integer addUser(RolesUsers model);

	/**
	 * 角色组中移除用户
	 * 
	 * @param model
	 *          中间表实体对象
	 * @return 操作是否成功（true成功，false失败）
	 */
	public Integer removeUser(RolesUsers model);

	public List<Role> findAll();

}
