package com.hardware.business.dao;

import com.hardware.business.model.DoorLockInstallOrder;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("doorLockInstallOrderDao")
public class DoorLockInstallOrderDao extends BaseHibernateDaoSupport<DoorLockInstallOrder, String> {
	@Override
	public List<DoorLockInstallOrder> findByModel(DoorLockInstallOrder routerInstallOrder,
                                                  Order order, Pager pager) {
		return null;
	}
}
