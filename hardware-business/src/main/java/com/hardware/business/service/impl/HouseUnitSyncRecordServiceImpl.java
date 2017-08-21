package com.hardware.business.service.impl;

import com.hardware.business.model.HouseUnitSyncRecord;
import com.hardware.business.service.HouseUnitSyncRecordService;
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
@Service(value = "houseUnitSyncRecordService")
public class HouseUnitSyncRecordServiceImpl extends BaseServiceSupport<HouseUnitSyncRecord, String> implements HouseUnitSyncRecordService {

    @Resource(name = "houseUnitSyncRecordDao")
    @Override
    public void setBaseHibernateDaoSupport(
            @Qualifier("houseUnitSyncRecordDaoImpl") BaseHibernateDaoSupport<HouseUnitSyncRecord, String> baseHibernateDaoSupport) {
        this.baseHibernateDaoSupport = baseHibernateDaoSupport;
    }

}
