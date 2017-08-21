package com.hardware.business.service;

import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.House;
import com.hardware.business.model.SupplierProduct;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.Map;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public interface HouseSyncService extends IBaseServiceSupport<House, String> {
    Map<String,Object> syncDingDingHouse(House house, SupplierProduct supplierProduct) throws ThirdPartyRequestException, DataErrorException ;
    Map<String,Object> syncYunYouHouse(House house, SupplierProduct supplierProduct) throws DataErrorException;
    Map<String, Object> syncHouse(House house, SupplierProduct supplierProduct) throws DataErrorException;
    Map<String, Object> syncGuoJiaHouse(House house) throws DataErrorException, ThirdPartyRequestException;
}
