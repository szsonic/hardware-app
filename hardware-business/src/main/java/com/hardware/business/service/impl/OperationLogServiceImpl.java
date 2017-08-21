package com.hardware.business.service.impl;

import com.hardware.business.dao.OperationLogDao;
import com.hardware.business.model.OperationLog;
import com.hardware.business.service.OperationLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Service
public class OperationLogServiceImpl extends BaseServiceSupport<OperationLog, String> implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;
    
    @Resource(name = "operationLogDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("operationLogDaoImpl") BaseHibernateDaoSupport<OperationLog, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

    public void saveOperationLog(OperationLog log){
        operationLogDao.save(log);
    }
}
