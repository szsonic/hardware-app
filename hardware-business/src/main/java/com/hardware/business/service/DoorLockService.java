package com.hardware.business.service;

import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.*;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.Date;
import java.util.List;

/**
 * 门锁业务接口<br>
 * 
 * @author zq
 *
 */

public interface DoorLockService extends IBaseServiceSupport<DoorLock, String> {

	/**
	 * 根据用户的手机号码查询用户绑定的门锁
	 * 
	 * @param mobile
	 *          用户的手机号码
	 * @return 返回用户绑定的门锁
	 */
	public List<DoorLock> getDevidByUserOpenId(String mobile);

	/**
	 * 更新锁的sn
	 * 
	 * @param code
	 *          房源编号
	 * @param door
	 *          内门编号
	 * @param sn
	 *          锁的唯一标识
	 */
	public void updateSnByCode(String code, String door, String sn);


	void updateDoorLockStatus(DoorLock doorLock, String sn);


	/**
	 * 根据设备号查找门锁
	 *
	 * @param devId
	 *          设备号
	 * @return 返回电表
	 */
	public DoorLock getDoorLockByDevId(String devId);

	/**
	 * 根据设备号查找供应商ID
	 *
	 * @param devId
	 *          设备号
	 * @return 返回电表的Supplier
	 */
	public Supplier getSupplierByDevId(String devId);

	/**
	 * 根据设备号查找供应商产品ID
	 *
	 * @param devId
	 *          设备号
	 * @return 返回电表的SupplierPro
	 */
	public SupplierProduct getSupplierProByDevId(String devId);

	/**
	 *
	 * @param door
	 * @return
	 */
	public List<DoorLock> findDoorLockByDoor(String door);

	public void updateSurplusElectricity(String devId, int electricity);

	public void updateInlineStatus(String devId, String status);

	void frozenPwd(DoorLock doorLock, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws IllegalAccessException, InstantiationException, ClassNotFoundException,DataErrorException,ThirdPartyRequestException;

	void unFrozenPwd(DoorLock doorLock, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws IllegalAccessException, InstantiationException, ClassNotFoundException,DataErrorException,ThirdPartyRequestException;

	void addPassword(Date start, Date end, String pwd, DoorLock doorLock, Member accountOpenId, DoorLockOperationLog operationLog, String pwdType) throws Exception;

	void delPassword(DoorLock doorLock, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws Exception;

	void modifyPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog operationLog, MemberLockPass memberLockPass) throws Exception;

	public Integer getDoorLockByInstalling();
	
	public Integer getDoorLockByInstalled();
	
	public Integer getDoorLockOfOn();
	
	public Integer getDoorLockOfOff();

	public List <DoorLock> listByModel(DoorLock model, Order order, Pager pager);
	
	public void updateDevIdByOnlineSyncCode(String onlineSyncCode, String doorType, String devId) throws Exception;
}
