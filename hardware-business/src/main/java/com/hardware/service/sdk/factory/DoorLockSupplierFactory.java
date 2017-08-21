package com.hardware.service.sdk.factory;

import com.hardware.service.sdk.IDoorLock;
import com.hardware.service.sdk.enums.DoorLockSupplier;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * 门锁供应商工厂类
 * @author sunzhongshuai
 */
@Component
public class DoorLockSupplierFactory implements ApplicationContextAware{

    private static Map<DoorLockSupplier, IDoorLock> doorLockBeanMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        doorLockBeanMap=new HashMap<>();
        Map<String, IDoorLock> map = applicationContext.getBeansOfType(IDoorLock.class);
        for (String key : map.keySet()) {
            doorLockBeanMap.put(map.get(key).getSupplierCode(),map.get(key));
        }
    }
    @SuppressWarnings("unchecked")
    public static <T extends IDoorLock> T getDoorLockSupplier(DoorLockSupplier supplier) {
        return (T)doorLockBeanMap.get(supplier);
    }
}
