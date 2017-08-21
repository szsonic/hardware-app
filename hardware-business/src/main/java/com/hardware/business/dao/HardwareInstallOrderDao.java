package com.hardware.business.dao;

import com.hardware.business.model.HardwareInstallOrder;
import org.apache.commons.lang.StringUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备安装状态
 * 
 * @author zq
 * 
 */
@Repository("hardwareInstallOrderDao")
public class HardwareInstallOrderDao extends BaseHibernateDaoSupport<HardwareInstallOrder, String> {

	@Override
	public List<HardwareInstallOrder> findByModel(HardwareInstallOrder model, Order order, Pager pager) {
		model = model == null ? new HardwareInstallOrder() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("select c from HardwareInstallOrder c");
		hql.append(" left join c.ammeterInstallOrder a");
		hql.append(" left join c.doorLockInstallOrder d");
		hql.append(" left join c.routerInstallOrder r");

		hql.append(" where 1=1 ");
		//这里只查询未安装的订单
		if(model.getDoorLockInstallOrder() == null
				&& model.getAmmeterInstallOrder() == null
				&& model.getRouterInstallOrder() == null){
			hql.append(" and (a.installStatus='WAIT' " +
					          " or d.installStatus='WAIT'" +
					          " or r.installStatus='WAIT'" +
					          ") ");
		}
		if(ValidatorUtils.isNotEmpty(model.getDoorLockInstallOrder())
				&& ValidatorUtils.isNotEmpty(model.getDoorLockInstallOrder().getInstallStatus())){
			hql.append(" and c.doorLockInstallOrder.installStatus='" + model.getDoorLockInstallOrder().getInstallStatus() + "' ");
		}
		if(ValidatorUtils.isNotEmpty(model.getAmmeterInstallOrder())
				&& ValidatorUtils.isNotEmpty(model.getAmmeterInstallOrder().getInstallStatus())){
			hql.append(" and c.ammeterInstallOrder.installStatus='" + model.getAmmeterInstallOrder().getInstallStatus() + "' ");
		}
		if(ValidatorUtils.isNotEmpty(model.getRouterInstallOrder())
				&& ValidatorUtils.isNotEmpty(model.getRouterInstallOrder().getInstallStatus())){
			hql.append(" and c.routerInstallOrder.installStatus='" + model.getRouterInstallOrder().getInstallStatus() + "' ");
		}

		if (StringUtils.isNotBlank(model.getProvince())) {
			hql.append(" and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.provinceName = '" + model.getProvince() + "' ");
		}
		if (StringUtils.isNotBlank(model.getCity())) {
			hql.append(" and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.cityName = '" + model.getCity() + "' ");
		}
		if (StringUtils.isNotBlank(model.getDist())) {
			hql.append(" and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.distName = '" + model.getDist() + "' ");
		}
		if (StringUtils.isNotBlank(model.getStreet())) {
			hql.append(" and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.street like '%" + model.getStreet() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getType())) {
			hql.append(" and c.house.rentType = '" + model.getType()+ "' ");
		}
		if (StringUtils.isNotBlank(model.getDistrictName())) {
			hql.append(" and c.house.houseUnit.houseBlock.houseDistrict.districtName like '%" + model.getDistrictName() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getRoomNo())) {
			hql.append(" and c.house.roomNo = '" + model.getRoomNo() + "' ");
		}
		if (StringUtils.isNotBlank(model.getBlock())) {
			hql.append(" and c.house.houseUnit.houseBlock.name = '" + model.getBlock() + "' ");
		}
		if (StringUtils.isNotBlank(model.getUnit())) {
			hql.append(" and c.house.houseUnit.name = '" + model.getUnit() + "' ");
		}
		if (model.getHouse()!=null&&StringUtils.isNotBlank(model.getHouse().getId())) {
			hql.append(" and c.house.id = '" + model.getHouse().getId() + "' ");
		}

		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public HardwareInstallOrder getByHouseId(String houseId) {
		List<HardwareInstallOrder> houses = this.queryHQL("from HardwareInstallOrder a where a.status='AVAILABLE' and a.houseId='" + houseId + "'");
		return houses != null && houses.size() > 0 ? houses.get(0) : null;
	}
}
