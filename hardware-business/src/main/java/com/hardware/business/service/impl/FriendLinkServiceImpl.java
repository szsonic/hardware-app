package com.hardware.business.service.impl;

import com.hardware.business.model.FriendLink;
import com.hardware.business.service.FriendLinkService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 友情链接服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("friendLinkService")
public class FriendLinkServiceImpl extends BaseServiceSupport<FriendLink, String> implements FriendLinkService {
	@Resource(name = "friendLinkDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("friendLinkDaoImpl") BaseHibernateDaoSupport<FriendLink, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
