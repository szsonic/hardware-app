package com.hardware.business.dao;

import com.hardware.business.model.Role;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色数据访问实现类
 * 
 * @author shenpeng
 * 
 */
@Repository("roleDao")
public class RoleDao extends BaseHibernateDaoSupport<Role, String> {

	@Override
	public List<Role> findByModel(Role model, Order order, Pager pager) {
		model = model == null ? new Role() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from Role c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getRoleName()) ? " and c.roleName like '%" + model.getRoleName() + "%' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getUpId()) ? " and c.upId=:upId " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getLevel()) ? " and c.level=:level " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getRemark()) ? " and c.remark like '%" + model.getRemark() + "%' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public List<Role> findAll() {
		List<Role> roles = this.queryHQL("from Role a where a.status='AVAILABLE' ");
		return roles;
	}
}
