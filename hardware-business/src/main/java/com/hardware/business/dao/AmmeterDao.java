package com.hardware.business.dao;

import com.hardware.business.model.*;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.enums.RecordStatus;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 电表类
 * 
 * @author zhongqi
 * 
 */
@Repository("ammeterDao")
public class AmmeterDao extends BaseHibernateDaoSupport<Ammeter, String> {

	@Override
	public List<Ammeter> findByModel(Ammeter model, Order order, Pager pager) {
		model = model == null ? new Ammeter() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from Ammeter c where 1=1");
		if(model.getDevIdExists()!=null){
			hql.append(model.getDevIdExists()?" and c.devId  is not null" : " and c.devId  is  null");
		}
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getHouse()) ? " and c.house.id='"+model.getHouse().getId()+"'" : "");

		if(ValidatorUtils.isEmpty(model.getStatus())){
			model.setStatus(RecordStatus.AVAILABLE);
		}
		hql.append(!ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public String getDevidByUserMobile(String openId) {
		List<Ammeter> ammterUsers = this.queryHQL("from Ammeter a where a.status='AVAILABLE' and a.openId='" + openId + "'");
		return ammterUsers != null && ammterUsers.size() > 0 ? ammterUsers.get(0).getDevId() : null;
	}

	public Supplier getSupplierByDevId(String devId) {
		List<Ammeter> ammterUsers = this.queryHQL("from Ammeter a where a.status='AVAILABLE' and a.devId='" + devId + "'");
		return ammterUsers != null && ammterUsers.size() > 0 ? ammterUsers.get(0).getSupplierProduct().getSupplier() : null;
	}

	public SupplierProduct getSupplierProByDevId(String devId) {
		List<Ammeter> ammterUsers = this.queryHQL("from Ammeter a where a.status='AVAILABLE' and a.devId='" + devId + "'");
                                     		return ammterUsers != null && ammterUsers.size() > 0 ? ammterUsers.get(0).getSupplierProduct() : null;
	}

	public Ammeter getAmmeterByDevId(String devId) {
		List<Ammeter> ammterUsers = this.queryHQL("from Ammeter a where a.status='AVAILABLE' and a.devId='" + devId + "'");
		return ammterUsers != null && ammterUsers.size() > 0 ? ammterUsers.get(0) : null;
	}

	public List<Ammeter> findByRoomNo(String roomNo) {
		List<Ammeter> ammterUsers = this.queryHQL("from Ammeter a where a.status='AVAILABLE' and a.door='" + roomNo + "'");
		return ammterUsers;
	}

	public void updateElectricity(String electricity, String devId) {
		this.executeHQL("update Ammeter a set a.electricityLeftValue = '" + electricity + "' where a.devId='" + devId + "' ");
	}

	public void updateelectricityStatus(String devId, String status) {
		this.executeHQL("update Ammeter a set a.electricDoorSwitchStatus = '" + status + "' where a.devId='" + devId + "' ");
	}

	public void updateInlineStatus(String devId, String status) {
		this.executeHQL("update Ammeter a set a.wifiOnlineStatus = '" + status + "' where a.devId='" + devId + "' ");
	}

	public int deleteByHouseId(String houseId){
		return this.executeHQL("delete Ammeter a where a.house.id='"+houseId+"'");
	}

	public int deleteById(String id) {
		StringBuilder hql = new StringBuilder();
		hql.append(" delete Ammeter u where u.id =  '" + id + "' ");
		return this.executeHQL(hql.toString());
	}
	
	public Integer updateAmmeteDevIdSetting(String id, String sn) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(new Date());
		return this.executeHQL("update Ammeter a set a.devId = '" + sn + "',a.installTime = '" + date + "'  where a.id='" + id + "' ");
	}
	public Integer getAmmeterByInstalling()
	{
		return this.countSQL("select count(1) from ammeter where devId is null and status='AVAILABLE'");
	}
	public Integer getAmmeterByInstalled()
	{
		return this.countSQL("select count(1) from ammeter where devId is not null and status='AVAILABLE'");
	}
	public Integer getAmmeterOfOn()
	{
		return this.countSQL("select count(1) from ammeter where devId is not null "
				+ "and status='AVAILABLE' and wifiOnlineStatus='on'");
	}
	public Integer getAmmeterOfOff()
	{
		return this.countSQL("select count(1) from ammeter where devId is not null "
				+ "and status='AVAILABLE' and wifiOnlineStatus='off'");
	}
	
	/*
	 * 分页查询已安装电表
	 */
	public List<Ammeter> listByModel(Ammeter model, Order order, Pager pager) {
		model = model == null ? new Ammeter() : model;
		StringBuilder hql = new StringBuilder();
		hql.append(" from Ammeter c ").append(" where 1=1 ");
		
		if(ValidatorUtils.isNotEmpty(model.getHouse())){
			hql.append(ValidatorUtils.isNotEmpty(model.getHouse().getRoomNo()) ? 
					" and c.house.roomNo='" + model.getHouse().getRoomNo() +  "' " : "");
			if(ValidatorUtils.isNotEmpty(model.getHouse().getHouseUnit())){
				HouseUnit unit = model.getHouse().getHouseUnit();
				hql.append(ValidatorUtils.isNotEmpty(unit.getName()) ? 
						" and c.house.houseUnit.name='" + unit.getName() +  "' " : "");
				if(ValidatorUtils.isNotEmpty(unit.getHouseBlock())){
					HouseBlock block = unit.getHouseBlock();
					hql.append(ValidatorUtils.isNotEmpty(block.getName()) ? 
							" and c.house.houseUnit.houseBlock.name='" + block.getName() +  "' " : "");
					if(ValidatorUtils.isNotEmpty(block.getHouseDistrict())){
						HouseDistrict district = block.getHouseDistrict();
						hql.append(ValidatorUtils.isNotEmpty(district.getDistrictName()) ? 
								" and c.house.houseUnit.houseBlock.houseDistrict.districtName like '%" + district.getDistrictName() +  "%' " : "");
						if(ValidatorUtils.isNotEmpty(district.getHouseStreet())){
							HouseStreet street = district.getHouseStreet();
							
							hql.append(ValidatorUtils.isNotEmpty(street.getCityName()) ? " and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.cityName='" + street.getCityName() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getProvinceName()) ? " and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.provinceName='" + street.getProvinceName() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getStreet()) ? " and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.street='" + street.getStreet() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getDistName()) ? " and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.distName='" + street.getDistName() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getAddress()) ? " and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.address like '%" + street.getAddress() +  "%' " : "");
						}
					}
				}
			}
		}
		
		if(ValidatorUtils.isNotEmpty(model.getDevIdExists())){
			hql.append(model.getDevIdExists() ? " and c.devId  is not null " : " and c.devId  is  null ");
		}

		if(ValidatorUtils.isEmpty(model.getStatus())){
			model.setStatus(RecordStatus.AVAILABLE);
		}
		
		hql.append(!ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		
		if(ValidatorUtils.isNotEmpty(model.getSupplierProduct().getCode())){
			hql.append(" and c.supplierProduct.code='" + model.getSupplierProduct().getCode() + "' ");
		}
		
		if(ValidatorUtils.isNotEmpty(model.getWifiOnlineStatus())){
			hql.append(" and c.wifiOnlineStatus=:wifiOnlineStatus ");
		}
		
		if(ValidatorUtils.isNotEmpty(model.getElectricDoorSwitchStatus())){
			hql.append(" and c.electricDoorSwitchStatus=:electricDoorSwitchStatus ");
		}
		
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}
	
	/*
	 * 获取所有已安装电表
	 */
	public List<Ammeter> findAllInstalledAmmeters(){
		StringBuilder hql = new StringBuilder();
		hql.append("from Ammeter c where 1=1");
		hql.append(" and c.devId  is not null");
		hql.append(" and c.status='" + RecordStatus.AVAILABLE.name() + "' ");
		
		return this.findByQueryString(hql.toString(), null, null);
	} 
}
