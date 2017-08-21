package com.hardware.business.dao;

import com.hardware.business.model.HouseDistrict;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 房源小区类
 * 
 * @author zhongqi
 * 
 */
@Repository("houseDistrictDao")
public class HouseDistrictDao extends BaseHibernateDaoSupport<HouseDistrict, String> {

	@Override
	public List<HouseDistrict> findByModel(HouseDistrict model, Order order, Pager pager) {
		model = model == null ? new HouseDistrict() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("select new HouseDistrict(c.id,c.onLineSynCode,c.districtName,c.houseStreet,count(*)) from HouseDistrict c  inner join  c.houseBlockList  b inner join b.houseUnitList u inner join u.houseList h  where 1=1 ");
		if (model.getHouseStreet() != null && StringUtils.isNotBlank(model.getHouseStreet().getProvinceName())) {
			hql.append(" and c.houseStreet.provinceName = '" + model.getHouseStreet().getProvinceName() + "' ");
		}
		if (model.getHouseStreet() != null &&
		    StringUtils.isNotBlank(model.getHouseStreet().getCityName())) {
			hql.append(" and c.houseStreet.cityName = '" + model.getHouseStreet().getCityName() + "' ");
		}
		if (model.getHouseStreet() != null && StringUtils.isNotBlank(model.getHouseStreet().getDistName())) {
			hql.append(" and c.houseStreet.distName = '" + model.getHouseStreet().getDistName() + "' ");
		}
		if (model.getHouseStreet() != null && StringUtils.isNotBlank(model.getHouseStreet().getStreet())) {
			hql.append(" and c.houseStreet.street like '%" + model.getHouseStreet().getStreet() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getDistrictName())) {
			hql.append(" and c.districtName like '%" + model.getDistrictName() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getSourceHouseDistrictCode())) {
			hql.append(" and c.sourceHouseDistrictCode ='" + model.getSourceHouseDistrictCode() + "' ");
		}
		hql.append(ValidatorUtils.isNotEmpty(model.getHouseStructureType()) ? " and c.type=:type " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append("group by (c.id) ");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public List<HouseDistrict> findByQueryString(String queryString, HouseDistrict model, Pager pager) {
		int firstResult = 0;
		int maxResults = 0;
		String countQuery="select count(distinct c.id)  as dn from HouseDistrict c inner join c.houseBlockList b inner join b.houseUnitList u inner join u.houseList h";
		if (pager != null) {
			List<Long> countlist = (List<Long>) this.getObjectByHQL(countQuery, model, 0, 0);
			if (countlist.isEmpty() || countlist.get(0) == 0) {
				return new ArrayList<HouseDistrict>();
			}
			pager.setRecordCount(NumberUtils.toInt(countlist.get(0).toString()));
			if (pager.getRecordCount() < 1)
				return new ArrayList<HouseDistrict>();
			firstResult = (pager.getPage() < 1) ? 0 : (pager.getPage() - 1) * pager.getPageSize();
			maxResults = pager.getPageSize();
		}
		List<HouseDistrict> list = (List<HouseDistrict>) getObjectByHQL(queryString, model, firstResult, maxResults);
		return list;
	}

	public List<HouseDistrict> findByDistrictName(String districtName) {
		List<HouseDistrict> houseDistricts = this.queryHQL("from HouseDistrict a where a.status='AVAILABLE' and a.districtName='" + districtName + "'");
		return houseDistricts;
	}

	public List<HouseDistrict> findByHouseDistrict(HouseDistrict model) {
		model = model == null ? new HouseDistrict() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from HouseDistrict c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getDistrictName()) ? " and c.districtName=:districtName " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getHouseStructureType()) ? " and c.type=:type " : "");
		return this.queryHQL(hql.toString(), model);
	}

	public HouseDistrict getBySign(String sign) {
		List<HouseDistrict> houseDistricts = this.queryHQL("from HouseDistrict a where a.status='AVAILABLE' and a.sign='" + sign + "'");
		return houseDistricts != null && houseDistricts.size() > 0 ? houseDistricts.get(0) : null;
	}

	public List<HouseDistrict> findByHouseStreetIds(List<String> ids, HouseDistrict model) {
		StringBuilder hql = new StringBuilder();
		hql.append(" from HouseDistrict c where 1=1 ");
		if (ids.size() > 0) {
			hql.append(" and c.houseStreet.id in ('" + StringUtils.join(ids, "','") + "')");
		}
		hql.append(ValidatorUtils.isNotEmpty(model.getDistrictName()) ? " and c.districtName=:districtName " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getHouseStructureType()) ? " and c.type=:type " : "");
		return this.queryHQL(hql.toString(), model);
	}

}
