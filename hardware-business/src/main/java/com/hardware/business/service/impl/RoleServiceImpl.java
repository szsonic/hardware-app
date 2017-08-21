package com.hardware.business.service.impl;

import com.hardware.business.dao.RoleDao;
import com.hardware.business.model.Role;
import com.hardware.business.model.RolesUsers;
import com.hardware.business.service.RoleService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceSupport<Role, String> implements RoleService {

	@Resource(name = "roleDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("roleDaoImpl") BaseHibernateDaoSupport<Role, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	public Integer addUser(RolesUsers model) {
		return this.baseHibernateDaoSupport.executeSQL("INSERT INTO roles_users (roleId,userId) VALUES ( '" + model.getRoleId() + "','" + model.getUserId() + "')");
	}

	@Override
	public Integer removeUser(RolesUsers model) {
		return this.baseHibernateDaoSupport.executeSQL("DELETE FROM roles_users WHERE roleId='" + model.getRoleId() + "' AND userId='" + model.getUserId() + "'");
	}

	@Override
	public List<Role> findAll() {
		RoleDao roledao = (RoleDao) this.baseHibernateDaoSupport;
		return roledao.findAll();
	}

}
