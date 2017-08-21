package com.hardware.business.dao;

import com.hardware.business.model.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.enums.RecordStatus;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 门锁表
 * 
 * @author zq
 * 
 */
@Repository("doorLockDao")
public class DoorLockDao extends BaseHibernateDaoSupport<DoorLock, String> {

	@Override
	public List<DoorLock> findByModel(DoorLock model, Order order, Pager pager) {
		model = model == null ? new DoorLock() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from DoorLock c where 1=1");
		if(model.getDevIdExists()!=null){
			hql.append(model.getDevIdExists()? " and c.devId  is not null" : " and c.devId  is  null");
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

	public List<DoorLock> getDevidByUserOpenId(String openId) {
		List<DoorLock> doorLockUsers = this.queryHQL("from DoorLock d where d.status='AVAILABLE' and d.openId='" + openId + "'");
		return doorLockUsers;
	}

	public void updateSnByYunYouCode(String id, String sn) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(new Date());
		this.executeHQL("update DoorLock d set d.devId = '" + sn + "',d.installTime = '" + date + "'  where d.id='" + id + "' ");
	}

	public Supplier getSupplierByDevId(String devId) {
		List<DoorLock> doorLocks = this.queryHQL("from DoorLock a where a.status='AVAILABLE' and a.devId='" + devId + "'");
		return doorLocks != null && doorLocks.size() > 0 ? doorLocks.get(0).getSupplierProduct().getSupplier() : null;
	}

	public List<DoorLock> findDoorLockByDoor(String door) {
		List<DoorLock> doorLocks = this.queryHQL("from DoorLock a where a.status='AVAILABLE' and a.door='" + door + "'");
		return doorLocks;
	}

	public SupplierProduct getSupplierProByDevId(String devId) {
		List<DoorLock> doorLocks = this.queryHQL("from DoorLock a where a.status='AVAILABLE' and a.devId='" + devId + "'");
		return doorLocks != null && doorLocks.size() > 0 ? doorLocks.get(0).getSupplierProduct() : null;
	}

	public DoorLock getDoorLockByDevId(String devId) {
		List<DoorLock> doorLocks = this.queryHQL("from DoorLock a where a.status='AVAILABLE' and a.devId='" + devId + "'");
		return doorLocks != null && doorLocks.size() > 0 ? doorLocks.get(0) : null;
	}

	public DoorLock getDoorLockByYunYouCode(String code, String door) {
		List<DoorLock> doorLocks = null;
		if (StringUtils.isBlank(door)) {
			doorLocks = this.queryHQL("from DoorLock a where a.status='AVAILABLE' and a.yunYouCode='" + code + "' and a.door is null ");
		} else {
			doorLocks = this.queryHQL("from DoorLock a where a.status='AVAILABLE' and a.yunYouCode='" + code + "' and a.door = '" + door + "' ");
		}
		return doorLocks != null && doorLocks.size() > 0 ? doorLocks.get(0) : null;
	}

	public void updateelectricityStatus(String devId, int electric) {
		this.executeHQL("update DoorLock a set a.powerLeftValue = '" + electric + "' where a.devId='" + devId + "' ");
	}

	public void updateInlineStatus(String devId, String status) {
		this.executeHQL("update DoorLock a set a.wifiOnlineStatus = '" + status + "' where a.devId='" + devId + "' ");
	}

	public int deleteById(String id) {
		StringBuilder hql = new StringBuilder();
		hql.append(" delete DoorLock u where u.id =  '" + id + "' ");
		return this.executeHQL(hql.toString());
	}

	public int deleteByHouseId(String houseId){
		return this.executeHQL("delete DoorLock u where u.house.id='"+houseId+"'");
	}

	public int doorLockPassIndex(final String type, final String devId) {
		final java.util.Map<String, Integer> result = new HashMap<>();
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.doWork(new Work() {

			    @Override
			    public void execute(Connection conn) throws SQLException {
				    // TODO Auto-generated method stub
				    CallableStatement proc = null;
				    try {
					    proc = conn.prepareCall("{call pass_index(?,?,?)}");

					    // 注册输出参数
					    proc.registerOutParameter(3, java.sql.Types.INTEGER);
					    // 传递输入参数
					    proc.setString(1, type);
					    proc.setString(2, devId);

					    // 执行存储过程
					    proc.execute();

					    // 获取执行完的存储过程的返回值
					    int index = proc.getInt("newPIndex");
					    result.put("index", index);
				    } catch (Exception e) {
					    // logger.error("访问数据库失败");
					    e.printStackTrace();
				    } finally {
					    if (null != proc) {
						    proc.close();
					    }
				    }
			    }
		    });
				return null;
			}
		});
		Integer index = result.get("index");
		return index;
	}
	public Integer getDoorLockByInstalling()
	{
		return this.countSQL("select count(1) from doorlock where devId is null and status='AVAILABLE'");
	}
	public Integer getDoorLockByInstalled()
	{
		return this.countSQL("select count(1) from doorlock where devId is not null and status='AVAILABLE'");
	}
	
	public Integer getDoorLockOfOn()
	{
		return this.countSQL("select count(1) from doorlock where devId is not null "
				+ "and status='AVAILABLE' and wifiOnlineStatus='on'");
	}
	public Integer getDoorLockOfOff()
	{
		return this.countSQL("select count(1) from doorlock where devId is not null "
				+ "and status='AVAILABLE' and wifiOnlineStatus='off'");
	}

	public List<DoorLock> listByModel(DoorLock model, Order order, Pager pager) {
		model = model == null ? new DoorLock() : model;
		StringBuilder hql = new StringBuilder();
		hql.append(" from DoorLock c ").append(" where 1=1 ");
		
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
		
		if(ValidatorUtils.isNotEmpty(model.getPowerLeftLow())){
			hql.append(StringUtils.equals("1", model.getPowerLeftLow()) ? 
					"and c.powerLeftValue>=35 " : "and (c.powerLeftValue<35 and c.powerLeftValue>0)");
		}
		
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

}
