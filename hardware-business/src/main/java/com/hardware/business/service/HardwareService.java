package com.hardware.business.service;

import com.hardware.business.enums.HardwareHdStatus;
import com.hardware.business.enums.HardwareStatus;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.model.Ammeter;
import com.hardware.business.model.Hardware;
import com.hardware.business.model.House;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

/**
 * 硬件设备业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface HardwareService extends IBaseServiceSupport<Hardware, String> {

	/**
	 * 查询设备
	 * 
	 * @param devId
	 * @return
	 */
	public Hardware getByDevId(String devId);

	/**
	 * 更新硬件的状态
	 * 
	 * @param devId
	 *          电表唯一ID
	 * @param status
	 * @return
	 */
	public int updateHdStatus(String devId, HardwareHdStatus status);

	public int updateHardwareStatus(String id, HardwareStatus status);

	/**
	 * 更新设备的入住状态
	 * 
	 * @param devId
	 * @param status
	 * @return
	 */
	public int updateSettledStatusByDevId(String devId, String status);

	public int deleteById(String id, String type);

	/**
	 * 创建硬件设备
	 * 
	 * @param supplierProCode
	 * @param terminalAmmeterId
	 *          选择的集中器ID
	 * @param hardware
	 * @return
	 * @throws HardwareSdkException
	 */
	public String createDianBiao(String supplierProCode, Ammeter ammeter, House house) throws Exception;




}
