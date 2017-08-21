package com.hardware.business.dao;

import com.hardware.business.model.MistakeHouseConfirmedRecord;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mistakeHouseConfirmedRecordDao")
public class MistakeHouseConfirmedRecordDao extends BaseHibernateDaoSupport<MistakeHouseConfirmedRecord, String>  {
	 
	@Override
	public List<MistakeHouseConfirmedRecord> findByModel(MistakeHouseConfirmedRecord model, Order order, Pager pager) {
		model = model == null ? new MistakeHouseConfirmedRecord() : model;
		
		StringBuilder hql = new StringBuilder();
		hql.append("from MistakeHouseConfirmedRecord c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getUser()) ? " and c.user=:user" : " ");
		
		if(ValidatorUtils.isNotEmpty(model.getHouse())
				&& ValidatorUtils.isNotEmpty(model.getHouse().getHouseUnit())
				&& ValidatorUtils.isNotEmpty(model.getHouse().getHouseUnit().getHouseBlock())
				&& ValidatorUtils.isNotEmpty(model.getHouse().getHouseUnit().getHouseBlock().getHouseDistrict())
				&& ValidatorUtils.isNotEmpty(model.getHouse().getHouseUnit().getHouseBlock().getHouseDistrict().getId())){
			
			String houseDistrictId = model.getHouse().getHouseUnit().getHouseBlock().getHouseDistrict().getId();
			
			hql.append(" and c.house.houseUnit.houseBlock.houseDistrict.id='" + houseDistrictId + "' ");
		}
		
		hql.append(" and status='AVAILABLE' order by c.updateTime desc");
		
		return this.findByQueryString(hql.toString(),model, pager);
	}

}
