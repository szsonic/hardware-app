package com.hardware.business.service.impl;

import com.hardware.business.dao.SupplierProductDao;
import com.hardware.business.model.SupplierProduct;
import com.hardware.business.service.SupplierProductService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 智能硬件供应商业务接口实现类<br>
 * 
 * @author fanjunjian
 *
 */
@Service("supplierProductService")
public class SupplierProdecutServiceImpl extends BaseServiceSupport<SupplierProduct, String> implements SupplierProductService {

	@Resource(name = "supplierProductDao")
	private SupplierProductDao supplierProductDao;

	@Resource(name = "supplierProductDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("supplierProductDaoImpl") BaseHibernateDaoSupport<SupplierProduct, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public List<SupplierProduct> findSupplierType(String supplierType) {
		List<SupplierProduct> supplierProducts = this.queryHQL("from SupplierProduct a where a.status='AVAILABLE' and a.supplierType='" + supplierType + "'");
		return supplierProducts;
	}

	@Override
	public List<SupplierProduct> findByType(String type) {
		List<SupplierProduct> list = supplierProductDao.findByType(type);
		return list;
	}

	@Override
	public SupplierProduct findBySupplierCode(String code) {
		SupplierProduct supplierProduct=new SupplierProduct();
		supplierProduct.setCode(code);
		List<SupplierProduct> list=supplierProductDao.findByModel(supplierProduct,null,null);
		return list.size()>0?list.get(0):null;
	}

	@Override
	public SupplierProduct findByProductCode(String code) {
		SupplierProduct supplierProduct=new SupplierProduct();
		supplierProduct.setCode(code);
		List<SupplierProduct> list=supplierProductDao.findByModel(supplierProduct,null,null);
		return list.size()>0?list.get(0):null;
	}
}
