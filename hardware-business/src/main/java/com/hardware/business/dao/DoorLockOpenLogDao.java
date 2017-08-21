package com.hardware.business.dao;

import com.hardware.business.model.DoorLockOpenLog;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 门锁开门日志表
 * 
 * @author zq
 * 
 */
@Repository("doorLockOpenLogDao")
public class DoorLockOpenLogDao extends BaseHibernateDaoSupport<DoorLockOpenLog, String> {

	@Override
	public List<DoorLockOpenLog> findByModel(DoorLockOpenLog model, Order order, Pager pager) {
		model = model == null ? new DoorLockOpenLog() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from DoorLockOpenLog c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDoorLock()) ? " and c.doorLock=:doorLock " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

}
