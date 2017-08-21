package com.hardware.service.sdk;

import com.alibaba.fastjson.JSONObject;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.*;
import com.hardware.service.sdk.enums.DoorLockSupplier;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门锁的SDK接口
 * 
 * @author zhongqi
 *
 */
public interface IDoorLock {
	/**
	 * 一键开锁
	 * 
	 * @param params
	 *          一键开锁需要的参数
	 * @return 返回开锁的结果{"success":false,"desc":"HOST_NOT_ONLINE"}
	 */
	public Map<String, Object> controlLock(Map<String, Object> params) throws ThirdPartyRequestException;

	/**
	 * 获取锁的状态
	 * 
	 * @param params
	 *          获取锁的状态
	 * @return
	 */
	public Map<String, Object> status(Map<String, Object> params) throws ThirdPartyRequestException;



	/**
	 * 清除门锁密码
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> clearAllPassword(Map<String, Object> params) throws ThirdPartyRequestException;



	List<JSONObject> openLog(String sn, Date startTime, Date endTime) throws ThirdPartyRequestException;


	/**
	 * 同步房源接口
	 *
	 * @param houses
	 *          房源
	 *
	 * @return
	 */
	public Map<String, Object> syncHouse(List<House> houses, SupplierProduct supplierProduct) throws Exception;


	void frozenPassword(String uuid, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws DataErrorException,ThirdPartyRequestException;

	void unfrozenPassword(String uuid, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog)throws DataErrorException,ThirdPartyRequestException;

	Map<String,Object> addPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog doorLockOperationLog, MemberLockPass memberLockPass, String pwdType)throws Exception;

	void delPassword(MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog, String devId) throws DataErrorException,ThirdPartyRequestException;

	void modifyPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog doorLockOperationLog, MemberLockPass memberLockPass)throws DataErrorException,ThirdPartyRequestException;

	DoorLockSupplier getSupplierCode();
}
