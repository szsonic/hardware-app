package com.hardware.business.service;

import com.hardware.business.model.OperationLog;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public interface OperationLogService extends IBaseServiceSupport<OperationLog, String>{
    void saveOperationLog(OperationLog log);
}
