package com.hardware.business.dao;

import com.hardware.business.model.Menu;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单数据访问实现类
 * 
 * @author shenpeng
 * 
 */
@Repository("menuDao")
public class MenuDao extends BaseHibernateDaoSupport<Menu, String> {
	@Override
	public List<Menu> findByModel(Menu model, Order order, Pager pager) {
		model = model == null ? new Menu() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from Menu c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getTagName()) ? " and c.tagName=:tagName " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getUpId()) ? " and c.upId=:upId " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getLevel()) ? " and c.level=:level " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getMenuName()) ? " and c.menuName like '%" + model.getMenuName() + "%' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getSort()) ? " and c.sort like '%" + model.getSort() + "%' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
				+ DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(),model, pager);
	}
}
