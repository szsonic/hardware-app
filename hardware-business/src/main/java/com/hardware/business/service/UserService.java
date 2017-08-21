package com.hardware.business.service;

import com.hardware.business.model.RolesUsers;
import com.hardware.business.model.User;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 系统管理员用户服务接口<br>
 * 
 * @author shenpeng
 * 
 */
public interface UserService extends IBaseServiceSupport<User, String> {

	/**
	 * 根据账号和密码查找用户
	 * 
	 * @param loginName
	 *          用户名
	 * @param loginPwd
	 *          密码
	 * @return User 用户信息
	 */
	public User findUserByLoginNameAndLoginPwd(String loginName, String loginPwd);

	/**
	 * 账号是否存在
	 * 
	 * @param mobile
	 *          账号号码
	 * @return boolean 是否存在
	 */
	public boolean isExistsLoginName(String loginName);

	/**
	 * 根据角色ID获取用户列表
	 * 
	 * @param roleId
	 *          角色ID
	 * @return List<User> 用户集合
	 */
	public List<User> findUserListByRoleId(String roleId);

	public int delete(String id);
	
	public String save(User user, String roleId);
	
	public int addUserRole(RolesUsers rolesUsers);
	
	public User getUserByLoginName(String loginName);
	
	public User getUserByUserId(String userId);
}
