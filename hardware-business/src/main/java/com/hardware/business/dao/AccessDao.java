package com.hardware.business.dao;

import com.hardware.business.model.Access;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限数据访问实现类
 * 
 * @author shenpeng
 * 
 */
@Repository("accessDao")
public interface AccessDao extends BaseHibernateDaoSupport<Access, String> {

//	@Override
//	public List<Access> findByModel(Access model, Order order, Pager pager) {
//		model = model == null ? new Access() : model;
//		StringBuilder hql = new StringBuilder();
//		hql.append("from Access c where 1=1");
//		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getMenuId()) ? " and c.menuId=:menuId " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getMenuMethodId()) ? " and c.menuMethodId=:menuMethodId " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getRoleId()) ? " and c.roleId=:roleId " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getUserId()) ? " and c.userId=:userId " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getDepartId()) ? " and c.departId=:departId " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
//		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
//				+ DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
//		hql.append(order != null ? order.toString() : "");
//		return this.findByQueryString(hql.toString(), model, pager);
//	}

}
