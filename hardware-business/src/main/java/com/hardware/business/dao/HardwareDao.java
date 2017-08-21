package com.hardware.business.dao;

import com.hardware.business.enums.HardwareHdStatus;
import com.hardware.business.enums.HardwareStatus;
import com.hardware.business.enums.InstallStatus;
import com.hardware.business.model.Hardware;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 硬件设备类
 * 
 * @author zhongqi
 * 
 */
@Repository("hardwareDao")
public class HardwareDao extends BaseHibernateDaoSupport<Hardware, String> {

	@Resource(name = "doorLockDao")
	private DoorLockDao doorLockDao;

	@Resource(name = "ammeterDao")
	private AmmeterDao ammeterDao;

	@Resource(name = "houseDao")
	private HouseDao houseDao;

	@Override
	public List<Hardware> findByModel(Hardware model, Order order, Pager pager) {
		model = model == null ? new Hardware() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from Hardware c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getHouse()) ? " and c.house.id=:house.id " : "");
		hql.append((ValidatorUtils.isNotEmpty(model.getType())) ? " and c.type=:type " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public Hardware getByDevId(String devId) {
		List<Hardware> hardwares = this.queryHQL("from Hardware a where a.status='AVAILABLE' and a.devId='" + devId + "'");
		return (hardwares != null && hardwares.size() > 0) ? hardwares.get(0) : null;
	}

	public int updateHdStatusByDevId(String devId, HardwareHdStatus status) {
		StringBuilder hql = new StringBuilder();
		hql.append(" update Hardware u set u.hdStatus = ").append("'" + status + "'");
		hql.append(" where u.devId = ").append("'" + devId + "'");
		return this.executeHQL(hql.toString());
	}

	public int updateSettledStatusByDevId(String devId, String status) {
		StringBuilder hql = new StringBuilder();
		hql.append(" update Hardware u set u.settledStatus = ").append("'" + status + "'");
		hql.append(" where u.devId = ").append("'" + devId + "'");
		return this.executeHQL(hql.toString());
	}

	public int updateDevIdByDoorLockId(String devId, String doorLockId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" update Hardware u set u.devId = ").append("'" + devId + "'");
		hql.append(" , u.hardwareStatus = ").append("'" + HardwareStatus.ZC + "'");
		hql.append(" where u.doorLockId = ").append("'" + doorLockId + "'");
		return this.executeHQL(hql.toString());
	}

	public int updateHardwareStatus(String id, HardwareStatus status) {
		StringBuilder hql = new StringBuilder();
		hql.append(" update Hardware u set u.hardwareStatus = ").append("'" + status + "'");
		hql.append(" where u.id = ").append("'" + id + "'");
		return this.executeHQL(hql.toString());
	}

	public int updateInstallStatusById(String id) {
		StringBuilder hql = new StringBuilder();
		hql.append(" update Hardware u set u.installStatus = ").append("'" + InstallStatus.INSTALL_DONE + "'");
		hql.append(" where u.id = ").append("'" + id + "'");
		return this.executeHQL(hql.toString());
	}

	public int deleteById(String id) {
		StringBuilder hql = new StringBuilder();
		hql.append(" delete Hardware u where u.id =  '" + id + "' ");
		return this.executeHQL(hql.toString());
	}
	public int deleteByHouseId(String houseId,String type){
		return this.executeHQL("delete Hardware u where u.house.id='"+houseId+"' and u.type='"+type+"'");
	}
}
