package com.hardware.business.service;

import com.hardware.business.model.House;
import com.hardware.business.model.MistakeHouseConfirmedRecord;
import com.hardware.business.model.User;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

public interface MistakeHouseConfirmedRecordService extends IBaseServiceSupport<MistakeHouseConfirmedRecord, String>{
	
	public List<MistakeHouseConfirmedRecord> findByModel(MistakeHouseConfirmedRecord model, Order order, Pager pager);
	
	public boolean saveOrUpdate(MistakeHouseConfirmedRecord model);
	
	//查询这条记录是不是存在   存在就更新数据
	public MistakeHouseConfirmedRecord isExiteModel(User user, House house);
}
