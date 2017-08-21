package com.hardware.business.dao;

import com.hardware.business.model.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.iframework.commons.utils.date.DatetimeUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备在线离线表
 * 
 * @author zq
 * 
 */
@Repository("hardwareOnlineLogDao")
public class HardwareOnlineLogDao extends BaseHibernateDaoSupport<HardwareOnlineLog, String> {

	@Override
	public List<HardwareOnlineLog> findByModel(HardwareOnlineLog model, Order order, Pager pager) {
		model = model == null ? new HardwareOnlineLog() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from HardwareOnlineLog c where 1=1");

		if(ValidatorUtils.isNotEmpty(model.getWifiOnlineStatus())){
			hql.append(" and c.wifiOnlineStatus= '" + model.getWifiOnlineStatus() + "' ");
		}

		House house = null; //根据房间信息查询
		String housePrefix = "c.ammeter.house";

		if(ValidatorUtils.isNotEmpty(model.getDoorLock())){
			if(ValidatorUtils.isNotEmpty(model.getDoorLock().getId())){
				hql.append(" and c.doorLock.id= '" + model.getDoorLock().getId() + "' ");
			}else if(ValidatorUtils.isNotEmpty(model.getDoorLock().getHouse())){
				house = model.getDoorLock().getHouse();
				housePrefix = " c.doorLock.house";
			}
		}

		if(ValidatorUtils.isNotEmpty(model.getAmmeter())){
			if(ValidatorUtils.isNotEmpty(model.getAmmeter().getId())){
				hql.append(" and c.ammeter.id= '" + model.getAmmeter().getId() + "' ");
			}else if(ValidatorUtils.isNotEmpty(model.getAmmeter().getHouse())){
				house = model.getAmmeter().getHouse();
			}
		}

		if(ValidatorUtils.isNotEmpty(house)){
			hql.append(ValidatorUtils.isNotEmpty(house.getRoomNo()) ?
					" and " + housePrefix + ".roomNo='" + house.getRoomNo() +  "' " : "");
			if(ValidatorUtils.isNotEmpty(house.getHouseUnit())){
				HouseUnit unit = house.getHouseUnit();
				hql.append(ValidatorUtils.isNotEmpty(unit.getName()) ?
						" and " + housePrefix + ".houseUnit.name='" + unit.getName() +  "' " : "");
				if(ValidatorUtils.isNotEmpty(unit.getHouseBlock())){
					HouseBlock block = unit.getHouseBlock();
					hql.append(ValidatorUtils.isNotEmpty(block.getName()) ?
							" and " + housePrefix + ".houseUnit.houseBlock.name='" + block.getName() +  "' " : "");
					if(ValidatorUtils.isNotEmpty(block.getHouseDistrict())){
						HouseDistrict district = block.getHouseDistrict();
						hql.append(ValidatorUtils.isNotEmpty(district.getDistrictName()) ?
								" and " + housePrefix + ".houseUnit.houseBlock.houseDistrict.districtName like '%" + district.getDistrictName() +  "%' " : "");
						if(ValidatorUtils.isNotEmpty(district.getHouseStreet())){
							HouseStreet street = district.getHouseStreet();

							hql.append(ValidatorUtils.isNotEmpty(street.getCityName()) ? " and " + housePrefix + ".houseUnit.houseBlock.houseDistrict.houseStreet.cityName='" + street.getCityName() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getProvinceName()) ? " and " + housePrefix + ".houseUnit.houseBlock.houseDistrict.houseStreet.provinceName='" + street.getProvinceName() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getStreet()) ? " and " + housePrefix + ".houseUnit.houseBlock.houseDistrict.houseStreet.street='" + street.getStreet() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getDistName()) ? " and " + housePrefix + ".houseUnit.houseBlock.houseDistrict.houseStreet.distName='" + street.getDistName() +  "' " : "");
							hql.append(ValidatorUtils.isNotEmpty(street.getAddress()) ? " and " + housePrefix + ".houseUnit.houseBlock.houseDistrict.houseStreet.address like '%" + street.getAddress() +  "%' " : "");
						}
					}
				}
			}
		}

		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	/*
	 * 获取某个电表的离线日志(未恢复的)
	 */
	public int findOfflineCountByAmmeter(String ammeterId){
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from hardware_online_log where ammeterId='").append(ammeterId).append("' ");
		sql.append(" and status='AVAILABLE'");
		sql.append(" and offlineTime is not null");
		sql.append(" and onlineTime is null");

		return this.countSQL(sql.toString());
	}

	/*
	 * 获取某个门锁的离线日志(未恢复的)
	 */
	public int findOfflineCountByDoorLock(String doorLockId){
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from hardware_online_log where doorLockId='").append(doorLockId).append("' ");
		sql.append(" and status='AVAILABLE'");
		sql.append(" and offlineTime is not null");
		sql.append(" and onlineTime is null");

		return this.countSQL(sql.toString());
	}

	/*
	 * 当设备重新上线时, 更新记录
	 */
	public int updateOnlineStatusWhenReOnline(HardwareOnlineLog model){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("onlineTime", model.getOnlineTime());

		StringBuilder sql = new StringBuilder();
		sql.append(" update HardwareOnlineLog c set c.onlineTime='")
			.append(DatetimeUtils.format(model.getOnlineTime(), "yyyy-MM-dd HH:mm:ss")).append("' ");
		sql.append(" where 1=1 ");
		if(ValidatorUtils.isNotEmpty(model.getAmmeter())){
			sql.append(" and c.ammeter.id='").append(model.getAmmeter().getId()).append("' ");
		}
		if(ValidatorUtils.isNotEmpty(model.getDoorLock())){
			sql.append(" and c.doorLock.id='").append(model.getDoorLock().getId()).append("' ");
		}
		sql.append(" and c.offlineTime is not null");
		sql.append(" and c.onlineTime is null");

		return this.executeHQL(sql.toString(), data);
	}

}
