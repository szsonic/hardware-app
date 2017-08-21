package com.hardware.business.dao;

import com.hardware.business.model.ThirdPartySyncLog;
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
@Repository("thirdPartySyncLogDao")
public class ThirdPartySyncLogDao extends BaseHibernateDaoSupport<ThirdPartySyncLog, String> {
    @Override
    public List<ThirdPartySyncLog> findByModel(ThirdPartySyncLog model, Order order, Pager pager) {
        return null;
    }
}
