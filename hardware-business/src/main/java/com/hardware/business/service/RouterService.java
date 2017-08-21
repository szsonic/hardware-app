package com.hardware.business.service;

import com.hardware.business.model.Router;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

public interface RouterService extends IBaseServiceSupport<Router, String>{
	
	public List <Router> findByModel(Router model, Order order, Pager pager);
	
	public void saveRemark(Router model);

}
