package com.hardware.business.service.impl;

import com.hardware.business.model.MenuMethod;
import com.hardware.business.service.MenuMethodService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统菜单服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("menuMethodService")
public class MenuMethodServiceImpl extends BaseServiceSupport<MenuMethod, String> implements MenuMethodService {
	@Resource(name = "menuMethodDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("menuMethodDaoImpl") BaseHibernateDaoSupport<MenuMethod, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
