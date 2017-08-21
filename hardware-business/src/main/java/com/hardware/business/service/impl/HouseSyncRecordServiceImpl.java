package com.hardware.business.service.impl;

import com.hardware.business.model.HouseSyncRecord;
import com.hardware.business.service.HouseSyncRecordService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Service("houseSyncRecordService")
public class HouseSyncRecordServiceImpl extends BaseServiceSupport<HouseSyncRecord, String> implements HouseSyncRecordService {

    @Resource(name = "houseSyncRecordDao")
    @Override
    public void setBaseHibernateDaoSupport(
            @Qualifier("houseSyncRecordDaoImpl") BaseHibernateDaoSupport<HouseSyncRecord, String> baseHibernateDaoSupport) {
        this.baseHibernateDaoSupport = baseHibernateDaoSupport;
    }

}
