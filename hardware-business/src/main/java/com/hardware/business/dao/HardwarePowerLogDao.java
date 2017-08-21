package com.hardware.business.dao;

import com.hardware.business.model.HardwarePowerLog;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备电量表
 * 
 * @author zq
 * 
 */
@Repository("hardwarePowerLogDao")
public class HardwarePowerLogDao extends BaseHibernateDaoSupport<HardwarePowerLog, String> {

	@Override
	public List<HardwarePowerLog> findByModel(HardwarePowerLog model, Order order, Pager pager) {
		model = model == null ? new HardwarePowerLog() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from HardwarePowerLog c where 1=1");
		if (model.getDoorLock() != null &&
		    org.apache.commons.lang3.StringUtils.isNotBlank(model.getDoorLock().getId())) {
			hql.append(" and c.doorLock.id= '" + model.getDoorLock().getId() + "' ");
		}
		if (model.getAmmeter() != null &&
		    org.apache.commons.lang3.StringUtils.isNotBlank(model.getAmmeter().getId())) {
			hql.append(" and c.ammeter.id= '" + model.getAmmeter().getId() + "' ");
		}
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

}
