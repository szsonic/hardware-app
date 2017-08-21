package com.hardware.business.dao;

import com.hardware.business.model.Router;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("routerDao")
public class RouterDao extends BaseHibernateDaoSupport<Router, String>{
	
	@Override
	public List<Router> findByModel(Router model, Order order, Pager pager) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Router c where 1=1 ");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? "and c.id=:id":"");
		hql.append(ValidatorUtils.isNotEmpty(model.getDevId()) ? "and c.devId=:devId":"");
		return this.findByQueryString(hql.toString(), model, pager);
	}
	
}
