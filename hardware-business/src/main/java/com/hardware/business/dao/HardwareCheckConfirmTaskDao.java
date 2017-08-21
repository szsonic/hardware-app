package com.hardware.business.dao;

import com.hardware.business.enums.HardwareStatusCheckOrderStatus;
import com.hardware.business.model.HardwareCheckConfirmTask;
import com.hardware.business.model.House;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("hardwareCheckConfirmTaskDao")
public class HardwareCheckConfirmTaskDao  extends BaseHibernateDaoSupport<HardwareCheckConfirmTask, String> {

	@Override
	public List<HardwareCheckConfirmTask> findByModel(HardwareCheckConfirmTask model, Order order, Pager pager) {
		StringBuffer hql = new StringBuffer();
		hql.append("select c from HardwareCheckConfirmTask c where 1=1 ");
		hql.append(ValidatorUtils.isNotEmpty(model.getUser()) ? " and c.user=:user" : " ");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id" : " ");
		hql.append(ValidatorUtils.isNotEmpty(model.getType()) ? " and c.type=:type" : " ");
		if(ValidatorUtils.isNotEmpty(model.getInstallOrder())
				&& ValidatorUtils.isNotEmpty(model.getInstallOrder().getHouse())
				&& ValidatorUtils.isNotEmpty(model.getInstallOrder().getHouse().getHouseUnit())
				&& ValidatorUtils.isNotEmpty(model.getInstallOrder().getHouse().getHouseUnit().getHouseBlock())
				&& ValidatorUtils.isNotEmpty(model.getInstallOrder().getHouse().getHouseUnit().getHouseBlock().getHouseDistrict())
				&& ValidatorUtils.isNotEmpty(model.getInstallOrder().getHouse().getHouseUnit().getHouseBlock().getHouseDistrict().getId())){
			
			String houseDistrictId = model.getInstallOrder().getHouse().getHouseUnit().getHouseBlock().getHouseDistrict().getId();
			
			hql.append(" and c.installOrder.house.houseUnit.houseBlock.houseDistrict.id='" + houseDistrictId + "' ");
		}
		
		hql.append(ValidatorUtils.isNotEmpty(model.getHardwareStatusCheckOrderStatus()) ? " and c.hardwareStatusCheckOrderStatus=:hardwareStatusCheckOrderStatus" : " ");
		hql.append(" and c.status='AVAILABLE' order by c.des asc,c.updateTime desc");
		return this.findByQueryString(hql.toString(),model, pager);
		
	}
	//判断房源信息是否存在
	public boolean isExistHouseById(HardwareCheckConfirmTask houseConfirmTask){
		List<HardwareCheckConfirmTask> houseConfirmTaskList = 
				this.queryHQL("from HardwareCheckConfirmTask a"
						+ " where a.status='AVAILABLE' and a.installOrder=:installOrder", houseConfirmTask);
		
		return houseConfirmTaskList != null && houseConfirmTaskList.size() > 0 ?true : false;

	}
	
	/*
	 * 查询未确认的硬件监测工单
	 * @param type :硬件类型，0 门锁 1电表
	 * @param userId: 监工师傅id
	 */
	public List<HardwareCheckConfirmTask> findUnConfirmedCheckTask(House house, 
			Pager pager, Order order, String type, String userId){
		
		StringBuilder hql = new StringBuilder();
		
		hql.append("select c ");
		hql.append(" from HardwareCheckConfirmTask c ");
		hql.append(" inner join c.installOrder ");
		hql.append(" inner join c.installOrder.house ");
		hql.append(" inner join c.installOrder.house.houseUnit");
		hql.append(" inner join c.installOrder.house.houseUnit.houseBlock");
		hql.append(" inner join c.installOrder.house.houseUnit.houseBlock.houseDistrict");
		hql.append(" inner join c.installOrder.house.houseUnit.houseBlock.houseDistrict.houseStreet");
		
		hql.append(" where c.status='AVAILABLE' ");
		hql.append(" and c.hardwareStatusCheckOrderStatus='" + HardwareStatusCheckOrderStatus.DISTRIBUTED.name() + "' ");
		//hql.append(" and c.devId is not null ");
		hql.append("and c.installOrder.status='AVAILABLE' ");
		hql.append("and c.installOrder.house.status='AVAILABLE' ");
		hql.append("and c.installOrder.house.houseUnit.status='AVAILABLE' ");
		hql.append("and c.installOrder.house.houseUnit.houseBlock.status='AVAILABLE' ");
		hql.append("and c.installOrder.house.houseUnit.houseBlock.houseDistrict.status='AVAILABLE' ");
		hql.append("and c.installOrder.house.houseUnit.houseBlock.houseDistrict.houseStreet.status='AVAILABLE' ");
		hql.append("and c.installOrder.house.houseUnit.houseBlock.houseDistrict.id='" + house.getDistrictId() + "'");	
		
		hql.append("and c.user.id='" + userId + "'");
		hql.append("and c.type='" + type + "'");
		
		hql.append(" and not exists (select m.id from MistakeHouseConfirmedRecord  m where m.house.id=c.installOrder.house.id and m.status='AVAILABLE')");
		
		List<HardwareCheckConfirmTask> queryResult = findByQueryString(hql.toString(), pager);
		List<HardwareCheckConfirmTask> result = new ArrayList<HardwareCheckConfirmTask>();
		if(ValidatorUtils.isNotEmpty(queryResult)){
			for(HardwareCheckConfirmTask task : queryResult){
				House houseTemp = task.getInstallOrder().getHouse();
				if("0".equals(type)
						&& ValidatorUtils.isNotEmpty(houseTemp.getDoorLockList())
						&& ValidatorUtils.isNotEmpty(houseTemp.getDoorLockList().get(0).getDevId())){
					result.add(task);
				}else if("1".equals(type)
						&& ValidatorUtils.isNotEmpty(houseTemp.getAmmeterList())
						&& ValidatorUtils.isNotEmpty(houseTemp.getAmmeterList().get(0).getDevId())){
					result.add(task);
				}
			}
		}
		
		return result;
	}
	
	/*
	 * 查询存在的工单信息
	 */
	public HardwareCheckConfirmTask findById(HardwareCheckConfirmTask model){
		
		return this.getById(HardwareCheckConfirmTask.class, model.getId());
		
	}

}
