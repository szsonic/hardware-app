package com.hardware.business.service;

import com.hardware.business.enums.HouseSource;
import com.hardware.business.enums.RentType;
import com.hardware.business.model.*;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;
import java.util.Map;

/**
 * 房源业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface HouseService extends IBaseServiceSupport<House, String> {
	
	
	
	
	/**
	 * 通过房东短的房源code查询房源下面的所有硬件
	 * 
	 * @param landlordCode
	 * @return
	 */
	public Boolean unbindMember(String[] houseOpenIds, String[] roomOpenIds, String openId) ;

	public void saveHouseCenterInfo(String houseOrRoomId, String doorType, HouseSource houseSource, RentType rentType, Pager pager, Order order)throws Exception;

	public List<Hardware> findBySourceCode(String landlordCode);

	public List<HouseStreet> findHouseStreet(HouseStreet houseStreet);

	public List<HouseDistrict> findHouseDistrict(HouseDistrict houseDistrict);

	public List<HouseDistrict> findHouseDistrict(List<String> ids, HouseDistrict district);

	public List<HouseDistrict> findByDistrictName(String districtName);

	public House getSourceHouseCode(String sourceHouseCode);

	public House getYunYouCodeAndDoor(String onLineSynYunYouCode, String door);

	public List<Map<String, Object>> findByModelByHardware(House model, Order order, Pager pager);

	public String generateDoorLock(String proCode, String houseId) throws Exception;

	public String generateAmmeter(String proCode, String houseId) throws Exception;

	public Boolean bindMember(String[] houseOrRoomOpenIds, String openId);

	public Boolean unbindMember(String[] houseOrRoomOpenIds, String openId);

	public List<House> findByModelBaidu(House model, Order order, Pager pager) ;

	House getHouseBySynCode(String onLineSynCode);

	Map<String, Object> syncHouse(House house, SupplierProduct supplierProduct);

	Integer getHouseMember(String houseId, String memberId);

	public List<House> findExistHouseByDeviceType(House model, Pager pager, Order order, String type, String userId);

	//public List<HouseDistrict> findHouseDistrictListByDistrictName(String districtName, Pager pager, Order order);


	public String generateAmmeterByInstallOrder(String proCode, String orderId, String userId) throws Exception;
	public String generateDoorLockByInstallOrder(String proCode, String orderId, String userId) throws Exception;

	//查询存在备注的房源信息
	public List <House> findExistsRemarkHouse(House model, Order order, Pager pager);

}
