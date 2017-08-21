package com.hardware.business.dao;

import com.hardware.business.model.MenuMethod;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单支持方法数据访问实现类
 * 
 * @author shenpeng
 * 
 */
@Repository("menuMethodDao")
public class MenuMethodDao extends BaseHibernateDaoSupport<MenuMethod, String> {
	@Override
	public List<MenuMethod> findByModel(MenuMethod model, Order order, Pager pager) {
		model = model == null ? new MenuMethod() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from MenuMethod c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getMethodTagName()) ? " and c.methodTagName=:methodTagName " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getMethodName()) ? " and c.methodName like '%"+model.getMethodName()+"%' " : "");
		
		hql.append(ValidatorUtils.isNotEmpty(model.getMenu())&&ValidatorUtils.isNotEmpty(model.getMenu().getId()) ? " and c.menu.id='"+model.getMenu().getId()+"' " : "");
		
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
				+ DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(),model, pager);
	}
}
