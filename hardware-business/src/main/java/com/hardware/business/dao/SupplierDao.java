package com.hardware.business.dao;

import com.hardware.business.model.Supplier;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 供应商数据类
 * 
 * @author zhongqi
 * 
 */
@Repository("supplierDao")
public class SupplierDao extends BaseHibernateDaoSupport<Supplier, String> {

	@Override
	public List<Supplier> findByModel(Supplier model, Order order, Pager pager) {
		model = model == null ? new Supplier() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from Supplier c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getName()) ? " and c.name=:name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDescription()) ? " and c.description=:description " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public List<Supplier> getSupplierByType(String type){
		StringBuilder hql = new StringBuilder();
		hql.append(" from Supplier c where 1=1");
		if(StringUtils.isNotBlank(type)){
			hql.append( "and exists (select 1 from SupplierProduct t where t.hardwareType='");
			hql.append(type);
			hql.append("' and c.id=t.supplier) ");
		}
		return this.findByQueryString(hql.toString(), null, null);
	}

}
