package com.hardware.business.service.impl;

import com.hardware.business.model.ExcelImportLog;
import com.hardware.business.service.ExcelImportLogService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("excelImportLogService")
public class ExcelImportLogServiceImpl  extends BaseServiceSupport<ExcelImportLog, String> implements ExcelImportLogService {

	@Resource(name = "excelImportLogDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("excelImportLogServiceDaoImpl") BaseHibernateDaoSupport<ExcelImportLog, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

}
