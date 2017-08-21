package com.hardware.business.service.impl;

import com.hardware.business.model.House;
import com.hardware.business.service.BaiduFkService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 
 * @author zhongqi
 *
 */
@Service("baiduFkService")
public class BaiduFkServiceImpl extends BaseServiceSupport<House, String> implements BaiduFkService {

	@Resource(name = "baifuFkDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("baifuFkDaoImpl") BaseHibernateDaoSupport<House, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
