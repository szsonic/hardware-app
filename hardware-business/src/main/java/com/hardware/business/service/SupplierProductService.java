package com.hardware.business.service;

import com.hardware.business.model.SupplierProduct;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 智能硬件供应商产品业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface SupplierProductService extends IBaseServiceSupport<SupplierProduct, String> {

	public List<SupplierProduct> findSupplierType(String supplierType);

	public List<SupplierProduct> findByType(String type);

	SupplierProduct findBySupplierCode(String code);

	SupplierProduct findByProductCode(String code);

}
