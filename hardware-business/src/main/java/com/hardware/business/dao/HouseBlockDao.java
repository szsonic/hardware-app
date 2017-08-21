package com.hardware.business.dao;

import com.hardware.business.model.HouseBlock;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("houseBlockDao")
public class HouseBlockDao extends BaseHibernateDaoSupport<HouseBlock, String> {

	@Override
	public List<HouseBlock> findByModel(HouseBlock model, Order order, Pager pager) {
		model = model == null ? new HouseBlock() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from HouseBlock c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getHouseDistrict())&&ValidatorUtils.isNotEmpty(model.getHouseDistrict().getId()) ? " and c.houseDistrict.id = '" + model.getHouseDistrict().getId() + "' ": "");
		hql.append(ValidatorUtils.isNotEmpty(model.getName()) ? " and c.name = '" + model.getName() + "' ": "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getSourceHouseBlockCode()) ? " and c.sourceHouseBlockCode='" + model.getSourceHouseBlockCode()+ "' ": "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
				+ DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

}
