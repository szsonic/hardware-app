package com.hardware.business.service.impl;

import com.hardware.business.dao.RouterDao;
import com.hardware.business.model.Router;
import com.hardware.business.service.RouterService;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 
 * @author lishicheng 
 * 
 */
@Service("routerService")
public class RouterServiceImpl extends BaseServiceSupport<Router, String> implements RouterService {
	
	@Resource(name = "routerDao")
	private RouterDao routerDao;
	@Override
	public List <Router> findByModel(Router model, Order order, Pager pager) {
		return this.routerDao.findByModel(model, order, pager);
	}
	@Override
	public void saveRemark(Router model) {
		this.routerDao.update(model);
		
	}
	
	
	
	

}
