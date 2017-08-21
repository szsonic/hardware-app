package com.hardware.business.dao;

import com.hardware.business.model.SupplierProduct;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 供应商产品数据类
 * 
 * @author zhongqi
 * 
 */
@Repository("supplierProductDao")
public class SupplierProductDao extends BaseHibernateDaoSupport<SupplierProduct, String> {

	@Override
	public List<SupplierProduct> findByModel(SupplierProduct model, Order order, Pager pager) {
		model = model == null ? new SupplierProduct() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from SupplierProduct c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		if(model.getSupplier()!=null){
			hql.append(ValidatorUtils.isNotEmpty(model.getSupplier().getId()) ? " and c.supplier.id=:supplier.id " : "");
			hql.append(ValidatorUtils.isNotEmpty(model.getSupplier().getCode()) ? " and c.supplier.code= '"+model.getSupplier().getCode()+"' ": "");
			hql.append(ValidatorUtils.isNotEmpty(model.getSupplier().getName()) ? " and c.supplier.name= '"+model.getSupplier().getName()+"' ": "");
		}
		hql.append(ValidatorUtils.isNotEmpty(model.getHardwareType()) ? " and c.hardwareType=:hardwareType " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getCode()) ? " and c.code=:code " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public SupplierProduct getByCode(String code) {
		List<SupplierProduct> supplierProducts = this.queryHQL("from SupplierProduct a where a.status='AVAILABLE' and a.code='" + code + "'");
		return supplierProducts != null && supplierProducts.size() > 0 ? supplierProducts.get(0) : null;
	}

	public List<SupplierProduct> findByType(String type) {
		List<SupplierProduct> supplierProducts = this.queryHQL("from SupplierProduct a where a.status='AVAILABLE'  and hardwareType = '" + type + "' ");
		return supplierProducts;
	}
}
