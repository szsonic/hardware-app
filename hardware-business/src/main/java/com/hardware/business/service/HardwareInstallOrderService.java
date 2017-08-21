package com.hardware.business.service;

import com.hardware.business.model.HardwareInstallOrder;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

/**
 * 硬件安装状态业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface HardwareInstallOrderService extends IBaseServiceSupport<HardwareInstallOrder, String> {
     void saveHardwareInstallOrder(HardwareInstallOrder hardwareInstallOrder);
}
