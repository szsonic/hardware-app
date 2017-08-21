package com.hardware.business.service.impl;

import com.hardware.business.model.Access;
import com.hardware.business.model.Department;
import com.hardware.business.model.Role;
import com.hardware.business.model.User;
import com.hardware.business.service.AccessService;
import com.support.BaseHibernateDaoSupport;
import com.support.BaseServiceSupport;
import com.utils.ValidatorUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 系统管理员用户服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * @author shenpeng
 * 
 */
@Service("accessService")
public class AccessServiceImpl extends BaseServiceSupport<Access, String> implements AccessService {

	@Resource(name = "accessDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("accessDaoImpl") BaseHibernateDaoSupport<Access, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public boolean getBoolUserHasAccess(String modelName, String methodName, List<Access> accesses) {
		boolean flag = false;
		if (accesses != null && accesses.size() > 0) {
			for (Access access : accesses) {
				if (access.getMenu().getTagName().equals(modelName)) {
					if (access.getMenuMethodId() != null && access.getMenuMethod().getMethodTagName().equals(methodName)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	@Override
	public List<Access> getUserAccessList(User user) {
		// 当登录成功则检查该用户的操作权限菜单集合
		StringBuffer hql = new StringBuffer();
		hql.append("from Access c where 1=1");
		// 1.获取当前用户的访问权限菜单
		hql.append(" and (");
		hql.append(" c.userId='" + user.getId() + "' ");
		// 2.获取当前用户所拥有的角色列表，并获取所有角色的权限菜单
		List<Role> roles = user.getRoles();
		for (Role r : roles) {
			hql.append(" or c.roleId='" + r.getId() + "' ");
		}
		// 3.获取当前用户所拥有的部门列表，并获取所有部门的权限菜单
		List<Department> departs = user.getDeparts();
		for (Department d : departs) {
			hql.append(" or c.departId='" + d.getId() + "' ");
		}
		hql.append(")");
		// 4.查询权限集合并排除相同的菜单记录
		List<Access> accesses = this.queryHQL(hql.toString(),null);
		return accesses;
	}

	@Override
	public void addUserAccessList(Collection<Access> models) {
		for (Access model : models) {
			DetachedCriteria criteria = DetachedCriteria.forClass(Access.class);
			if (ValidatorUtils.isNotEmpty(model.getRoleId())) {
				criteria.add(Restrictions.eq("roleId", model.getRoleId()));
			}
			if (ValidatorUtils.isNotEmpty(model.getMenuId())) {
				criteria.add(Restrictions.eq("menuId", model.getMenuId()));
			}
			if (ValidatorUtils.isNotEmpty(model.getMenuMethodId())) {
				criteria.add(Restrictions.eq("menuMethodId", model.getMenuMethodId()));
			}
			if (ValidatorUtils.isNotEmpty(model.getUserId())) {
				criteria.add(Restrictions.eq("userId", model.getUserId()));
			}
			if (ValidatorUtils.isNotEmpty(model.getDepartId())) {
				criteria.add(Restrictions.eq("departId", model.getDepartId()));
			}
			if (this.baseHibernateDaoSupport.countByCriteria(criteria) < 1) {
				this.baseHibernateDaoSupport.save(model);
			}
		}
	}

	@Override
	public void deleteUserAccessList(Collection<Access> models) {
		for (Access model : models) {
			String hql = "delete from Access c where 1=1 ";
			if (ValidatorUtils.isNotEmpty(model.getRoleId())) {
				hql += " and c.roleId='" + model.getRoleId() + "'";
			}
			if (ValidatorUtils.isNotEmpty(model.getMenuId())) {
				hql += " and c.menuId='" + model.getMenuId() + "'";
			}
			if (ValidatorUtils.isNotEmpty(model.getMenuMethodId())) {
				hql += " and c.menuMethodId='" + model.getMenuMethodId() + "'";
			}
			if (ValidatorUtils.isNotEmpty(model.getUserId())) {
				hql += " and c.userId='" + model.getUserId() + "'";
			}
			if (ValidatorUtils.isNotEmpty(model.getDepartId())) {
				hql += " and c.departId='" + model.getDepartId() + "'";
			}
			this.baseHibernateDaoSupport.executeHQL(hql);
		}
	}

}
