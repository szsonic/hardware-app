package com.hardware.business.service;

import com.hardware.business.model.HardwareCheckConfirmTask;
import com.hardware.business.model.House;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

public interface HardwareCheckConfirmTaskService extends IBaseServiceSupport<HardwareCheckConfirmTask, String> {
	
	//判断房源信息是否存在
	public boolean isTaskExist(HardwareCheckConfirmTask houseConfirmTask);

	public String savaAllConfirmTask(String userId, String orderIds, String type) throws Exception;
	
	/*
	 * 查询未确认的硬件监测工单
	 * @param type :赢家类型，0 门锁 1电表
	 * @param userId: 监工师傅id
	 */
	public List<HardwareCheckConfirmTask> findUnConfirmedCheckTask(House house,
                                                                   Pager pager, Order order, String type, String userId);

	/*
	 * 查询未确认的硬件监测工单对应的房源信息
	 * @param type :赢家类型，0 门锁 1电表
	 * @param userId: 监工师傅id
	 */
	public List<House> findHouseWithUnConfirmedCheckTask(House house,
                                                         Pager pager, Order order, String type, String userId);

	public boolean saveOrUpdateHardwareCheckConfirmTask(HardwareCheckConfirmTask hardwareCheckConfirmTask, String des);

	public HardwareCheckConfirmTask isModleExist(
            HardwareCheckConfirmTask houseConfirmTask);

	public Integer getDeviceTestFailureCount(String type, String userId, String districtId);

}
