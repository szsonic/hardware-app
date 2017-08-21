package com.hardware.business.dao;

import com.hardware.business.model.HouseStreet;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 房源街道类
 * 
 * @author zhongqi
 * 
 */
@Repository("houseStreetDao")
public class HouseStreetDao extends BaseHibernateDaoSupport<HouseStreet, String> {

	@Override
	public List<HouseStreet> findByModel(HouseStreet model, Order order, Pager pager) {
		model = model == null ? new HouseStreet() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from HouseStreet c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getProvinceId()) ? " and c.provinceId=:provinceId " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getCityId()) ? " and c.cityId=:cityId " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDist()) ? " and c.dist=:dist " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStreet()) ? " and c.street=:street " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getSourceHouseStreetCode()) ? " and c.sourceHouseStreetCode=:sourceHouseStreetCode " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}
	
	
	public List<HouseStreet> findByHouseStreet(HouseStreet model) {
		model = model == null ? new HouseStreet() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from HouseStreet c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getProvinceId()) ? " and c.provinceId=:provinceId " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getCityId()) ? " and c.cityId=:cityId " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDist()) ? " and c.dist=:dist " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStreet()) ? " and c.street=:street " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getSourceHouseStreetCode()) ? " and c.sourceHouseStreetCode=:sourceHouseStreetCode " : "");
		return this.queryHQL(hql.toString(), model);
	}

	public HouseStreet getBySign(String sign) {
		List<HouseStreet> houseStreets = this.queryHQL("from HouseStreet a where a.status='AVAILABLE' and a.sign='" + sign + "'");
		return houseStreets != null && houseStreets.size() > 0 ? houseStreets.get(0) : null;
	}
}
