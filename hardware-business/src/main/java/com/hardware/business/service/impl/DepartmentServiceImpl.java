package com.hardware.business.service.impl;

import com.hardware.business.model.Department;
import com.hardware.business.model.DepartsRoles;
import com.hardware.business.model.DepartsUsers;
import com.hardware.business.service.DepartmentService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统角色服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceSupport<Department, String> implements DepartmentService {

	@Resource(name = "departmentDao")
	@Override
	public void setBaseHibernateDaoSupport(
			@Qualifier("departmentDaoImpl") BaseHibernateDaoSupport<Department, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	public Integer addUser(DepartsUsers model) {
		return this.baseHibernateDaoSupport.executeSQL("INSERT INTO departs_users (departId,userId) VALUES ( '"
				+ model.getDepartId() + "','" + model.getUserId() + "')");
	}

	@Override
	public Integer removeUser(DepartsUsers model) {
		return this.baseHibernateDaoSupport.executeSQL("DELETE FROM departs_users WHERE departId='"
				+ model.getDepartId() + "' AND userId='" + model.getUserId() + "'");
	}

	@Override
	public Integer addRole(DepartsRoles model) {
		return this.baseHibernateDaoSupport.executeSQL("INSERT INTO departs_roles (departId,roleId) VALUES ( '"
				+ model.getDepartId() + "','" + model.getRoleId() + "')");
	}

	@Override
	public Integer removeRole(DepartsRoles model) {
		return this.baseHibernateDaoSupport.executeSQL("DELETE FROM departs_roles WHERE departId='"
				+ model.getDepartId() + "' AND roleId='" + model.getRoleId() + "'");
	}
}
