package com.hardware.business.service.impl;

import com.hardware.business.model.UserLog;
import com.hardware.business.service.UserLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户操作日志接口实现类<br>
 * 
 * @author zhongqi
 * 
 */
@Service("userLogService")
public class UserLogServiceImpl extends BaseServiceSupport<UserLog, String> implements UserLogService {

	@Resource(name = "userLogDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("userLogDaoImpl") BaseHibernateDaoSupport<UserLog, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
