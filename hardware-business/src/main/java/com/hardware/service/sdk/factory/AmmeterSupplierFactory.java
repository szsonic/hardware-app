package com.hardware.service.sdk.factory;

import com.hardware.service.sdk.IAmmeter;
import com.hardware.service.sdk.enums.AmmeterSupplier;
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
public class AmmeterSupplierFactory implements ApplicationContextAware{

    private static Map<AmmeterSupplier, IAmmeter> ammeterSupplierBeanMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ammeterSupplierBeanMap=new HashMap<>();
        Map<String, IAmmeter> map = applicationContext.getBeansOfType(IAmmeter.class);
        for (String key : map.keySet()) {
            ammeterSupplierBeanMap.put(map.get(key).getSupplierCode(),map.get(key));
        }
    }
    @SuppressWarnings("unchecked")
    public static <T extends IAmmeter> T getAmmeterSupplier(AmmeterSupplier supplier) {
        return (T)ammeterSupplierBeanMap.get(supplier);
    }

}
