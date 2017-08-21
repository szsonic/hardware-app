package com.hardware.business.dao;

import com.hardware.business.model.RouterInstallOrder;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("routerInstallOrderDao")
public class RouterInstallOrderDao extends BaseHibernateDaoSupport<RouterInstallOrder, String> {
	@Override
	public List<RouterInstallOrder> findByModel(RouterInstallOrder routerInstallOrder,
                                                Order order, Pager pager) {
		return null;
	}
}
