package com.hardware.business.service.impl;

import com.hardware.business.model.FileInfo;
import com.hardware.business.service.FileInfoService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文件资源服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("fileInfoService")
public class FileInfoServiceImpl extends BaseServiceSupport<FileInfo, String> implements FileInfoService {

	@Resource(name = "fileInfoDao")
	@Override
	public void setBaseHibernateDaoSupport(
			@Qualifier("fileInfoDaoImpl") BaseHibernateDaoSupport<FileInfo, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
