package com.hardware.business.service;

import com.hardware.business.model.HardwareUnInstallOrder;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

public interface HardwareUnInstallOrderService extends IBaseServiceSupport<HardwareUnInstallOrder, String> {

	
	public void unInstallHardware(String ammeterOrDoorLockId, String type, String projectCode, String version)throws Exception ;
}
