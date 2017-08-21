package com.hardware.business.service;

import com.hardware.business.model.HouseDistrict;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 房源业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface HouseDistrictService extends IBaseServiceSupport<HouseDistrict, String> {
	
	public Integer getHouseDistrictCount(String name);


	public List<HouseDistrict> getHouseDistricts(HouseDistrict model, Pager pager);

}
