package com.hardware.business.service;


import com.hardware.business.enums.PwdOpStatus;
import com.hardware.business.enums.ThirdPartySyncAction;
import com.hardware.business.model.ThirdPartySyncLog;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public interface ThirdPartySyncLogService extends IBaseServiceSupport<ThirdPartySyncLog, String> {
    void saveThirdPartySyncLog(String params, String message, PwdOpStatus syncStatus, String methodName, ThirdPartySyncAction action);
}
