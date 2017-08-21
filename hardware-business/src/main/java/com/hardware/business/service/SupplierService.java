package com.hardware.business.service;

import com.hardware.business.model.Supplier;
import com.hardware.business.model.SupplierProduct;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 智能硬件供应商业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface SupplierService extends IBaseServiceSupport<Supplier, String> {

	/**
	 * 保存供应商
	 * @param supplierProduct
	 * @return
	 */
	public String saveSupplier(SupplierProduct supplierProduct);

	/**
	 * 根据硬件类型获取厂商
	 * @param type 硬件类型
	 * @return
	 */
	List<Supplier> getSupplierByType(String type);
}
