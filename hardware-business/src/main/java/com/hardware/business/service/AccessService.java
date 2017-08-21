package com.hardware.business.service;

import com.hardware.business.model.Access;
import com.hardware.business.model.User;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.Collection;
import java.util.List;

/**
 * 权限服务接口<br>
 * 
 * @author shenpeng
 */
public interface AccessService extends IBaseServiceSupport<Access, String> {

	/**
	 * 获取用户是否有访问权限(循环遍历权限集合)
	 * 
	 * @param modelName
	 *            菜单对象关键字
	 * @param methodName
	 *            菜单支持方法名称
	 * @param accesses
	 *            访问权限集合
	 * @return 返回用户是否拥有操作权限
	 */
	public boolean getBoolUserHasAccess(String modelName, String methodName, List<Access> accesses);

	/**
	 * 根据用户对象获取用户的所有操作权限（包括用户自身的权限和用户所拥有的角色权限）
	 * 
	 * @param user
	 *            用户对象
	 * @return 返回用户可操作的访问权限集合
	 */
	public List<Access> getUserAccessList(User user);

	/**
	 * 批量添加访问权限集合
	 * 
	 * @param models
	 *            访问权限集合
	 */
	public void addUserAccessList(Collection<Access> models);

	/**
	 * 批量删除访问权限集合
	 * 
	 * @param models
	 *            访问权限集合
	 */
	public void deleteUserAccessList(Collection<Access> models);
}
