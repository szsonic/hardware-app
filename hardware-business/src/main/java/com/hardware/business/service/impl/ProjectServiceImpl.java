package com.hardware.business.service.impl;

import com.hardware.business.dao.ProjectDao;
import com.hardware.business.model.Project;
import com.hardware.business.service.ProjectService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 项目业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("projectService")
public class ProjectServiceImpl extends BaseServiceSupport<Project, String> implements ProjectService {

	@Resource(name = "projectDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("projectDaoImpl") BaseHibernateDaoSupport<Project, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public Project getByCode(String code) {
		return ((ProjectDao)this.baseHibernateDaoSupport).getByCode(code);
	}

	
	
}
