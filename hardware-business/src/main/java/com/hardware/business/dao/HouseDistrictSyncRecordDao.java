package com.hardware.business.dao;

import com.hardware.business.model.HouseDistrictSyncRecord;
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
@Repository("houseDistrictSyncRecordDao")
public class HouseDistrictSyncRecordDao extends BaseHibernateDaoSupport<HouseDistrictSyncRecord, String> {
    @Override
    public List<HouseDistrictSyncRecord> findByModel(HouseDistrictSyncRecord model, Order order, Pager pager) {
        model = model == null ? new HouseDistrictSyncRecord() : model;
        StringBuilder hql = new StringBuilder();
        hql.append("from HouseDistrictSyncRecord c where 1=1");
        hql.append(ValidatorUtils.isNotEmpty(model.getSupplierProduct().getCode()) ? " and c.supplierProduct.code='"+model.getSupplierProduct().getCode()+"' " : "");
        hql.append(ValidatorUtils.isNotEmpty(model.getHouseDistrict().getId()) ? " and c.houseDistrict.id='"+model.getHouseDistrict().getId()+"' " : "");
        hql.append(order != null ? order.toString() : "");
        return this.findByQueryString(hql.toString(), model, pager);
    }
}
