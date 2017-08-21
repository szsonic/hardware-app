package com.hardware.business.dao;

import com.hardware.business.model.FriendLink;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 友情链接数据访问实现类
 * 
 * @author shenpeng
 * 
 */
@Repository("friendLinkDao")
public class FriendLinkDao extends BaseHibernateDaoSupport<FriendLink, String> {
	@Override
	public List<FriendLink> findByModel(FriendLink model, Order order, Pager pager) {
		model = model == null ? new FriendLink() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from FriendLink c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id='" + model.getId() + "' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getLinkTitle()) ? " and c.linkTitle like '%"+model.getLinkTitle()+"%' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
				+ DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(),model, pager);
	}
}
