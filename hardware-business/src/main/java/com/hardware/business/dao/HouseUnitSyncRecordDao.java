package com.hardware.business.dao;

import com.hardware.business.model.HouseUnitSyncRecord;
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
@Repository("houseUnitSyncRecordDao")
public class HouseUnitSyncRecordDao extends BaseHibernateDaoSupport<HouseUnitSyncRecord, String> {
    @Override
    public List<HouseUnitSyncRecord> findByModel(HouseUnitSyncRecord model, Order order, Pager pager) {
        model = model == null ? new HouseUnitSyncRecord() : model;
        StringBuilder hql = new StringBuilder();
        hql.append("from HouseUnitSyncRecord c where 1=1");
        hql.append(ValidatorUtils.isNotEmpty(model.getHouseUnit().getId()) ? " and c.houseUnit.id='"+model.getHouseUnit().getId()+"' " : "");
        hql.append(ValidatorUtils.isNotEmpty(model.getSupplierProduct().getSupplier().getCode()) ? " and c.supplierProduct.supplier.code='"+model.getSupplierProduct().getSupplier().getCode()+"' " : "");
        hql.append(order != null ? order.toString() : "");
        return this.findByQueryString(hql.toString(), model, pager);
    }
}
