package com.hardware.business.dao;

import com.hardware.business.model.HardwareUnInstallOrder;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("hardwareUnInstallOrderDao")
public class HardwareUnInstallOrderDao extends BaseHibernateDaoSupport<HardwareUnInstallOrder, String> {

    @Override
    public List<HardwareUnInstallOrder> findByModel(HardwareUnInstallOrder model, Order order, Pager pager) {
        // TODO Auto-generated method stub
        return null;
    }


}