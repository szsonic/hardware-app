package com.hardware.business.service.impl;

import com.hardware.business.dao.MistakeHouseConfirmedRecordDao;
import com.hardware.business.model.House;
import com.hardware.business.model.MistakeHouseConfirmedRecord;
import com.hardware.business.model.User;
import com.hardware.business.service.MistakeHouseConfirmedRecordService;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("mistakeHouseConfirmedRecordService")
public class MistakeHouseConfirmedRecordServiceImpl
    extends BaseServiceSupport<MistakeHouseConfirmedRecord, String> implements MistakeHouseConfirmedRecordService {
	
	@Resource
    MistakeHouseConfirmedRecordDao mistakeHouseConfirmedRecordDao;
	
	@Resource(name = "mistakeHouseConfirmedRecordDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("mistakeHouseConfirmedRecordDaoImpl") BaseHibernateDaoSupport<MistakeHouseConfirmedRecord, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public boolean saveOrUpdate(MistakeHouseConfirmedRecord model) {
		return mistakeHouseConfirmedRecordDao.saveOrUpdate(model);
	}

	@Override
	public MistakeHouseConfirmedRecord isExiteModel(User user, House house) {
		// TODO Auto-generated method stub
		MistakeHouseConfirmedRecord noExistHouseRecord = new MistakeHouseConfirmedRecord();
		noExistHouseRecord.setUser(user);
		noExistHouseRecord.setHouse(house);
		
		String hql="from MistakeHouseConfirmedRecord n where n.user=:user and n.house=:house and n.status='AVAILABLE'";
		List<MistakeHouseConfirmedRecord> list = mistakeHouseConfirmedRecordDao.queryHQL(hql, noExistHouseRecord);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}

}
