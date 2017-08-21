package com.hardware.business.service.impl;

import com.hardware.business.model.DoorLockOperationLog;
import com.hardware.business.service.DoorLockOperationLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Service("doorLockOperationLogService")
public class DoorLockOperationLogServiceImpl extends BaseServiceSupport<DoorLockOperationLog, String> implements DoorLockOperationLogService {

    @Resource(name = "doorLockOperationLogDao")
    @Override
    public void setBaseHibernateDaoSupport(
            @Qualifier("doorLockOperationLogDaoImpl") BaseHibernateDaoSupport<DoorLockOperationLog, String> baseHibernateDaoSupport) {
        this.baseHibernateDaoSupport = baseHibernateDaoSupport;
    }

    @Override
    public void saveDoorLockOperationLog() {

    }
}
