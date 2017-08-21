package com.hardware.business.service;

import com.hardware.business.model.Project;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

/**
 * 项目业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface ProjectService extends IBaseServiceSupport<Project, String> {

	/**
	 * 根据项目编号获取项目
	 * 
	 * @param code
	 * @return
	 */
	public Project getByCode(String code);

}
