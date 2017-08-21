package com.hardware.business.service.impl;

import com.hardware.business.dao.HardwareCheckConfirmTaskDao;
import com.hardware.business.enums.HardwareStatusCheckOrderStatus;
import com.hardware.business.model.HardwareCheckConfirmTask;
import com.hardware.business.model.HardwareInstallOrder;
import com.hardware.business.model.House;
import com.hardware.business.model.User;
import com.hardware.business.service.HardwareCheckConfirmTaskService;
import com.hardware.business.service.HardwareInstallOrderService;
import com.hardware.business.service.HouseService;
import com.hardware.business.service.UserService;
import org.hibernate.criterion.DetachedCriteria;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("hardwareCheckConfirmTaskService")
public class HardwareCheckConfirmTaskServiceImpl implements HardwareCheckConfirmTaskService {

	@Resource
    HardwareCheckConfirmTaskDao hardwareCheckConfirmTaskDao;
	@Resource
	UserService userService;
	@Resource
    HouseService houseService;
	@Resource
    HardwareInstallOrderService hardwareInstallOrderService;
	

	@Override
	public boolean isTaskExist(HardwareCheckConfirmTask houseConfirmTask) {
		return hardwareCheckConfirmTaskDao.isExistHouseById(houseConfirmTask);
	}
	
	@Override
	public HardwareCheckConfirmTask isModleExist(HardwareCheckConfirmTask houseConfirmTask){
		return hardwareCheckConfirmTaskDao.findById(houseConfirmTask);
	}
	
	/*
	 * 批量保存硬件状态监测派单
	 * @userId 监测师傅ID
	 * @orderIds 硬件安装订单(HardwareInstallOrder)， 以逗号分隔
	 * @type 硬件类型 0代表门锁  1代表电表
	 */
	public String savaAllConfirmTask(String userId, String orderIds, String type) throws Exception{
		User user = userService.getUserByUserId(userId);
		if(user == null){
			return "用户信息错误";
		}
		
		String[] ids = orderIds.split(",");
		if(ids == null || ids.length <= 0){
			return "订单信息错误";
		}
			
		List<HardwareCheckConfirmTask> allTasks = new ArrayList<HardwareCheckConfirmTask>();
		for(String orderId : ids){
			HardwareInstallOrder order = hardwareInstallOrderService.get(orderId);
			if(order == null){
				return "订单信息错误";
			}
			
			HardwareCheckConfirmTask houseConfirmTask = new HardwareCheckConfirmTask();
			houseConfirmTask.setUser(user);
			houseConfirmTask.setInstallOrder(order);
			houseConfirmTask.setType(type);
			if(isTaskExist(houseConfirmTask)){
				//return "房源已经分配给该监工";
				//houseConfirmTask.setCheckStatus("0");
				houseConfirmTask.setUpdateTime(new Date());
			}
			
			houseConfirmTask.setHardwareStatusCheckOrderStatus(HardwareStatusCheckOrderStatus.DISTRIBUTED);
			
			allTasks.add(houseConfirmTask);
		}
		
		//批量保存
		hardwareCheckConfirmTaskDao.saveOrUpdateAll(allTasks);
		
		return null;
	}
	
	/*
	 * 查询未确认的硬件监测工单对应的房源信息
	 * @param type :赢家类型，0 门锁 1电表
	 * @param userId: 监工师傅id
	 */
	public List<House> findHouseWithUnConfirmedCheckTask(House house, 
			Pager pager, Order order, String type, String userId){
		
		List<House> houses = new ArrayList<House>();
		List<HardwareCheckConfirmTask> tasks 
		    = findUnConfirmedCheckTask(house, pager, order, type, userId);
		
		if(ValidatorUtils.isNotEmpty(tasks)){
			for(HardwareCheckConfirmTask task : tasks){
				houses.add(task.getInstallOrder().getHouse());
			}
		}
		
		return houses;
	}
	
	/*
	 * 查询未确认的硬件监测工单
	 * @param type :赢家类型，0 门锁 1电表
	 * @param userId: 监工师傅id
	 */
	public List<HardwareCheckConfirmTask> findUnConfirmedCheckTask(House house, 
			Pager pager, Order order, String type, String userId){
		return hardwareCheckConfirmTaskDao.findUnConfirmedCheckTask(house, pager, order, type, userId);
	}
	/*
	 * 更新工单信息，保存检测状态、检测描述
	 */
	@Override
	public boolean saveOrUpdateHardwareCheckConfirmTask(HardwareCheckConfirmTask hardwareCheckConfirmTask,String des) {
		
		hardwareCheckConfirmTask.setHardwareStatusCheckOrderStatus(HardwareStatusCheckOrderStatus.DONE);
		hardwareCheckConfirmTask.setUpdateTime(new Date());
		hardwareCheckConfirmTask.setDes(des);
		this.hardwareCheckConfirmTaskDao.update(hardwareCheckConfirmTask);
		return true;
	}
	
	@Override
	public Integer getDeviceTestFailureCount(String type, String userId, String districtId) {
//		String sql="select count(*)"
//				+ " from hardware_check_confirm_task where userId='" + userId 
//				+ "' and type='" + type + "' and des='0' and status='AVAILABLE'";
		StringBuilder countSql = new StringBuilder();
		countSql.append("SELECT ");
		countSql.append("	count(*) ");
		countSql.append("FROM ");
		countSql.append("	hardware_check_confirm_task hcct ");
		if(ValidatorUtils.isNotEmpty(districtId)){
			countSql.append(",	hardware_install_order hio, ");
			countSql.append("	house h, ");
			countSql.append("	house_unit hu, ");
			countSql.append("	house_block hb, ");
			countSql.append("	house_district hd ");
		}
		countSql.append(" WHERE hcct.des='0' and hcct.status='AVAILABLE' and hcct.hardwareStatusCheckOrderStatus='DONE' ");
		countSql.append(ValidatorUtils.isNotEmpty(type)?" and hcct.type='"+type+"' ":"");
		countSql.append(ValidatorUtils.isNotEmpty(userId)?" and hcct.userId='"+userId+"' ":"");
		if(ValidatorUtils.isNotEmpty(districtId)){
			countSql.append(" and hcct.installOrderId = hio.id ");
			countSql.append(" AND hio.houseId = h.id ");
			countSql.append(" and h.houseUnitId=hu.id ");
			countSql.append(" and hu.houseBlockId=hb.id ");
			countSql.append(" and hb.houseDistrictId=hd.id ");
			countSql.append(" and hd.id='"+districtId+"' ");
		}
		
		return this.hardwareCheckConfirmTaskDao.countSQL(countSql.toString());
	}
	
	@Override
	public boolean load(HardwareCheckConfirmTask model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HardwareCheckConfirmTask get(String pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HardwareCheckConfirmTask get(Class<HardwareCheckConfirmTask> modelClass, String pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(HardwareCheckConfirmTask model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveAll(Collection<HardwareCheckConfirmTask> models) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(HardwareCheckConfirmTask model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll(Collection<HardwareCheckConfirmTask> models) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(HardwareCheckConfirmTask model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(Class<HardwareCheckConfirmTask> modelClass, String[] pks) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteVirtual(HardwareCheckConfirmTask model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAllVirtual(Collection<HardwareCheckConfirmTask> models) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HardwareCheckConfirmTask> findByHQL(String hql, HardwareCheckConfirmTask model, Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> findByModel(HardwareCheckConfirmTask model, Order order, Pager pager) {
		// TODO Auto-generated method stub
		return this.hardwareCheckConfirmTaskDao.findByModel(model, order, pager);
	}

	@Override
	public Integer countByCriteria(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> findByCriteria(DetachedCriteria detachedCriteria, Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer executeHQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer executeHQL(String hql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer executeHQL(String hql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> queryHQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> queryHQL(String hql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> queryHQL(String hql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> queryHQL(String hql, Integer firstResult, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> queryHQL(String hql, Object model, Integer firstResult, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HardwareCheckConfirmTask> queryHQL(String hql, Map<String, Object> models, Integer firstResult,
			Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countHQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countHQL(String hql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countHQL(String hql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number sumHQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number sumHQL(String hql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number sumHQL(String hql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer executeSQL(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer executeSQL(String sql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer executeSQL(String sql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQL(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQL(String sql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQL(String sql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQL(String sql, Integer firstResult, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQL(String sql, Object model, Integer firstResult, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQL(String sql, Map<String, Object> models, Integer firstResult,
			Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQLPager(String sql, Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQLPager(String sql, Object model, Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> querySQLPager(String sql, Map<String, Object> models, Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countSQL(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countSQL(String sql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countSQL(String sql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number sumSQL(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number sumSQL(String sql, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number sumSQL(String sql, Map<String, Object> models) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
