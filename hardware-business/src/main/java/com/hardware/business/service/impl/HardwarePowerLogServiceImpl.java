package com.hardware.business.service.impl;

import com.hardware.business.model.HardwarePowerLog;
import com.hardware.business.service.HardwarePowerLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设备电量业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("hardwarePowerLogService")
public class HardwarePowerLogServiceImpl extends BaseServiceSupport<HardwarePowerLog, String> implements HardwarePowerLogService {

	@Resource(name = "hardwarePowerLogDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("hardwarePowerLogDaoImpl") BaseHibernateDaoSupport<HardwarePowerLog, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
