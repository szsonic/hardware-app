package com.hardware.business.service.impl;

import com.hardware.business.dao.UserDao;
import com.hardware.business.model.Role;
import com.hardware.business.model.RolesUsers;
import com.hardware.business.model.User;
import com.hardware.business.service.RoleService;
import com.hardware.business.service.UserService;
import org.iframework.commons.utils.md5.Md5Utils;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统管理员用户服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceSupport<User, String> implements UserService {

	@Resource(name = "userDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("userDaoImpl") BaseHibernateDaoSupport<User, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Autowired
	private RoleService roleService;

	@Override
	public User findUserByLoginNameAndLoginPwd(String loginName, String loginPwd) {
		return ((UserDao) this.baseHibernateDaoSupport).findUserByLoginNameAndLoginPwd(loginName, Md5Utils.toMD5(loginPwd));
	}

	@Override
	public boolean isExistsLoginName(String loginName) {
		return ((UserDao) this.baseHibernateDaoSupport).isExistsLoginName(loginName);
	}

	@Override
	public List<User> findUserListByRoleId(String roleId) {
		Role role = this.roleService.get(roleId);
		return role != null ? role.getUsers() : null;
	}

	@Override
	public int delete(String id) {
		return ((UserDao) this.baseHibernateDaoSupport).delete(id);
	}

	public String save(User user, String roleId) {
		UserDao userDao = (UserDao) this.baseHibernateDaoSupport;
		String id = userDao.save(user);
		return id;
	}

	@Override
	public int addUserRole(RolesUsers rolesUsers) {
		UserDao userDao = (UserDao) this.baseHibernateDaoSupport;
		return userDao.addUserRole(rolesUsers);
	}

	@Override
	public User getUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		UserDao userDao = (UserDao) this.baseHibernateDaoSupport;
		return userDao.getUserByLoginName(loginName);
	}

	@Override
	public User getUserByUserId(String userId) {
		UserDao userDao = (UserDao) this.baseHibernateDaoSupport;
		User user= new User();
		user.setId(userId);
		List<User>  list=userDao.findByModel(user, null, null);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

}
