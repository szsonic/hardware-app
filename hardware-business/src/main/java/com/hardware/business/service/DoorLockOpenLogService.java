package com.hardware.business.service;

import com.hardware.business.model.DoorLockOpenLog;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.Map;

/**
 * 门锁业务接口<br>
 * 
 * @author zq
 *
 */

public interface DoorLockOpenLogService extends IBaseServiceSupport<DoorLockOpenLog, String> {

	void saveAPIOpenDoorRecordMessage(Map<String, Object> data, MemberLockPassService memberLockPassService);

	void saveALMSOpenDoorRecordMessage(Map<String, Object> data, MemberLockPassService memberLockPassService);
	//


}
