package com.hardware.business.service;

import com.hardware.business.model.DoorLockOperationLog;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public interface DoorLockOperationLogService extends IBaseServiceSupport<DoorLockOperationLog, String> {
    void saveDoorLockOperationLog();
}
