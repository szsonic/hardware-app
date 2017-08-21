package com.hardware.business.service.impl;

import com.hardware.business.model.HouseDistrict;
import com.hardware.business.service.HouseDistrictService;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 房源业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("houseDistrictService")
public class HouseDistrictServiceImpl extends BaseServiceSupport<HouseDistrict, String> implements HouseDistrictService {

	@Resource(name = "houseDistrictDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("houseDistrictImpl") BaseHibernateDaoSupport<HouseDistrict, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public Integer getHouseDistrictCount(String name) {
		// TODO Auto-generated method stub
		
		String sql=" select count(1) from house_district where districtName like '%"+name+"%' and status='AVAILABLE' ";
		return this.countSQL(sql);
	}
	@Override
	public List <HouseDistrict> getHouseDistricts(HouseDistrict model, Pager pager){
		String hql = "from HouseDistrict c where c.districtName like '%"+model.getDistrictName()+"%'";
		return this.findByHQL(hql, model, pager);
		
	}

}
