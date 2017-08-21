package com.hardware.business.service.impl;

import com.hardware.business.enums.PwdOpStatus;
import com.hardware.business.enums.ThirdPartySyncAction;
import com.hardware.business.model.ThirdPartySyncLog;
import com.hardware.business.service.ThirdPartySyncLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Service(value = "thirdPartySyncLogService")
public class ThirdPartySyncLogServiceImpl extends BaseServiceSupport<ThirdPartySyncLog, String> implements ThirdPartySyncLogService {

    @Resource(name = "thirdPartySyncLogDao")
    @Override
    public void setBaseHibernateDaoSupport(@Qualifier("thirdPartySyncLogDao") BaseHibernateDaoSupport<ThirdPartySyncLog, String> baseHibernateDaoSupport) {
        this.baseHibernateDaoSupport = baseHibernateDaoSupport;
    }

    @Override
    public void saveThirdPartySyncLog(String params, String message, PwdOpStatus syncStatus, String methodName, ThirdPartySyncAction action) {
        ThirdPartySyncLog thirdPartySyncLog=new ThirdPartySyncLog();
        thirdPartySyncLog.setSyncTime(new Date());
        thirdPartySyncLog.setParams(params);
        thirdPartySyncLog.setMessage(message);
        thirdPartySyncLog.setSyncStatus(syncStatus);
        thirdPartySyncLog.setMethodName(methodName);
        thirdPartySyncLog.setThirdPartySyncAction(action);
        this.save(thirdPartySyncLog);
    }
}
