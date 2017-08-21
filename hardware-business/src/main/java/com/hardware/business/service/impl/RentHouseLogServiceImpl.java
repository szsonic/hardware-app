package com.hardware.business.service.impl;

import com.hardware.business.model.RentHouseLog;
import com.hardware.business.service.RentHouseLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("rentHouseLogService")
public class RentHouseLogServiceImpl extends BaseServiceSupport<RentHouseLog, String> implements RentHouseLogService {

	@Resource(name = "rentHouseLogDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("rentHouseLogDaoImpl") BaseHibernateDaoSupport<RentHouseLog, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	
	
}