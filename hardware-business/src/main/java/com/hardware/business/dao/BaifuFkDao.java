package com.hardware.business.dao;

import com.hardware.business.model.House;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author zhongqi
 * 
 */
@Repository("baifuFkDao")
public class BaifuFkDao extends BaseHibernateDaoSupport<House, String> {

	@Override
	public List<House> findByModel(House model, Order order, Pager pager) {
		model = model == null ? new House() : model;
		StringBuilder hql = new StringBuilder();

		hql.append("from House c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getDistrictId()) ? " and c.houseUnit.houseBlock.houseDistrict.id=:houseUnit.houseBlock.houseDistrict.id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getBlock()) ? " and c.houseUnit.houseBlock.name=:houseUnit.houseBlock.name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getUnit()) ? " and c.houseUnit.name=:houseUnit.name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getRoomNo()) ? " and c.roomNo=:roomNo " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

}
