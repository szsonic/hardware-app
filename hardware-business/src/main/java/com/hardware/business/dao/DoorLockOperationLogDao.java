package com.hardware.business.dao;

import com.hardware.business.model.DoorLockOperationLog;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Repository("doorLockOperationLogDao")
public class DoorLockOperationLogDao extends BaseHibernateDaoSupport<DoorLockOperationLog, String> {
    @Override
    public List<DoorLockOperationLog> findByModel(DoorLockOperationLog model, Order order, Pager pager) {
        return null;
    }

}
