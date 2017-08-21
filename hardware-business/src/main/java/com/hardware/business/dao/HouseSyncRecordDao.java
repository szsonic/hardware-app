package com.hardware.business.dao;

import com.hardware.business.model.HouseSyncRecord;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Repository("houseSyncRecordDao")
public class HouseSyncRecordDao extends BaseHibernateDaoSupport<HouseSyncRecord, String> {
    @Override
    public List<HouseSyncRecord> findByModel(HouseSyncRecord model, Order order, Pager pager) {
        model = model == null ? new HouseSyncRecord() : model;
        StringBuilder hql = new StringBuilder();
        hql.append("from HouseSyncRecord c where 1=1");
        hql.append(ValidatorUtils.isNotEmpty(model.getHouse().getId()) ? " and c.house.id='"+model.getHouse().getId()+"' " : "");
        hql.append(ValidatorUtils.isNotEmpty(model.getSupplierProduct().getSupplier().getId()) ? " and c.supplierProduct.supplier.id='"+model.getSupplierProduct().getSupplier().getId()+"' " : "");
        hql.append(order != null ? order.toString() : "");
        return this.findByQueryString(hql.toString(), model, pager);
    }
}
