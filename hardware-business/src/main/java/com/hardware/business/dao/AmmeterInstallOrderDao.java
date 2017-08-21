package com.hardware.business.dao;

import com.hardware.business.model.AmmeterInstallOrder;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ammeterInstallOrderDao")
public class AmmeterInstallOrderDao extends BaseHibernateDaoSupport<AmmeterInstallOrder, String> {
	@Override
	public List<AmmeterInstallOrder> findByModel(AmmeterInstallOrder ammeterInstallOrder,
												 Order order, Pager pager) {
		return null;
	}
}
