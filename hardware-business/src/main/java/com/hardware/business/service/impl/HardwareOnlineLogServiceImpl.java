package com.hardware.business.service.impl;

import com.hardware.business.model.HardwareOnlineLog;
import com.hardware.business.service.HardwareOnlineLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设备在线离线业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("hardwareOnlineLogService")
public class HardwareOnlineLogServiceImpl extends BaseServiceSupport<HardwareOnlineLog, String> implements HardwareOnlineLogService {

	@Resource(name = "hardwareOnlineLogDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("hardwareOnlineLogDaoImpl") BaseHibernateDaoSupport<HardwareOnlineLog, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
