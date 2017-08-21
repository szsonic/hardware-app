package com.hardware.business.service.impl;

import com.hardware.business.dao.SupplierDao;
import com.hardware.business.dao.SupplierProductDao;
import com.hardware.business.model.Supplier;
import com.hardware.business.model.SupplierProduct;
import com.hardware.business.service.SupplierService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 智能硬件供应商业务接口实现类<br>
 * 
 * @author zhongqi
 *
 */
@Service("supplierService")
public class SupplierServiceImpl extends BaseServiceSupport<Supplier, String> implements SupplierService {

	@Resource(name = "supplierDao")
	private SupplierDao supplierDao;
	
	@Resource(name = "supplierProductDao")
	private SupplierProductDao supplierProductDao;

	@Resource(name = "supplierDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("supplierDaoImpl") BaseHibernateDaoSupport<Supplier, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public String saveSupplier(SupplierProduct supplierProduct) {
		Supplier supplier = supplierProduct.getSupplier();
		String id = supplierDao.save(supplier);
		SupplierProduct product = new SupplierProduct();
		product.setCode(supplierProduct.getCode());
		product.setSupplier(supplier);
		product.setName(supplierProduct.getName());
		product.setSdk(supplierProduct.getSdk());
		supplierProductDao.save(product);
		return id;
	}

	@Override
	public List<Supplier> getSupplierByType(String type) {
		return supplierDao.getSupplierByType(type);
	}
}
