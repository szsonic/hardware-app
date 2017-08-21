package com.hardware.business.service.impl;

import com.hardware.business.model.HouseDistrictSyncRecord;
import com.hardware.business.service.HouseDistrictSyncRecordService;
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
@Service(value = "houseDistrictSyncRecordServiceImpl")
public class HouseDistrictSyncRecordServiceImpl extends BaseServiceSupport<HouseDistrictSyncRecord, String> implements HouseDistrictSyncRecordService {
    @Resource(name = "houseDistrictSyncRecordDao")
    @Override
    public void setBaseHibernateDaoSupport(
            @Qualifier("houseDistrictSyncRecordDaoImpl") BaseHibernateDaoSupport<HouseDistrictSyncRecord, String> baseHibernateDaoSupport) {
        this.baseHibernateDaoSupport = baseHibernateDaoSupport;
    }
}
