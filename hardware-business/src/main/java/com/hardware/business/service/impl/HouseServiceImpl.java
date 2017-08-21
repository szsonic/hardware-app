package com.hardware.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hardware.business.conf.Config;
import com.hardware.business.dao.*;
import com.hardware.business.enums.*;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.model.*;
import com.hardware.business.model.houseCenter.FinalHouseRoomModel;
import com.hardware.business.model.houseCenter.FinalHouseSourceModel;
import com.hardware.business.model.houseCenter.IntelligentHouseResult;
import com.hardware.business.service.HardwareCheckConfirmTaskService;
import com.hardware.business.service.HardwareInstallOrderService;
import com.hardware.business.service.HouseService;
import com.hardware.business.service.HouseSyncService;
import com.hardware.service.sdk.IAmmeter;
import com.hardware.service.sdk.IDoorLock;
import com.hardware.service.sdk.enums.AmmeterSupplier;
import com.hardware.service.sdk.enums.DoorLockSupplier;
import com.hardware.service.sdk.factory.AmmeterSupplierFactory;
import com.hardware.service.sdk.factory.DoorLockSupplierFactory;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.http.HttpClientUtils;
import org.iframework.commons.utils.log.Log;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 房源业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("houseService")
public class HouseServiceImpl extends BaseServiceSupport<House, String> implements HouseService {

	@Autowired
	private DateNumberService dateNumberService;

	@Autowired
	private HouseSyncService houseSyncService;

	@Resource(name = "houseDao")
	private HouseDao houseDao;

	@Resource(name = "doorLockDao")
	private DoorLockDao doorLockDao;

	@Resource(name = "ammeterDao")
	private AmmeterDao ammeterDao;

	@Resource(name = "memberDao")
	private MemberDao memberDao;

	@Resource(name = "hardwareDao")
	private HardwareDao hardwareDao;

	@Resource(name = "houseDistrictDao")
	private HouseDistrictDao houseDistrictDao;

	@Resource(name = "supplierProductDao")
	private SupplierProductDao supplierProductDao;

	@Resource(name = "supplierDao")
	private SupplierDao supplierDao;

	@Resource(name = "houseStreetDao")
	private HouseStreetDao houseStreetDao;

	@Resource(name = "hardwareInstallOrderDao")
	private HardwareInstallOrderDao hardwareInstallOrderDao;
	
	@Autowired
	private HardwareCheckConfirmTaskService hardwareCheckConfirmTaskService;

	@Resource(name = "houseDao")
	@Override
	public void setBaseHibernateDaoSupport(
			@Qualifier("houseDaoImpl") BaseHibernateDaoSupport<House, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Autowired
	private HouseUnitDao houseUnitDao;
	@Autowired
	private HouseBlockDao houseBlockDao;
	@Autowired
	private HardwareInstallOrderService hardwareInstallOrderService;

	@Override
	public List<Hardware> findBySourceCode(String landlordCode) {
		HouseDao h = (HouseDao) this.baseHibernateDaoSupport;
		return h.findByLandlordCode(landlordCode);
	}

	@Override
	public House getSourceHouseCode(String sourceHouseCode) {
		HouseDao h = (HouseDao) this.baseHibernateDaoSupport;
		return h.getSourceHouseCode(sourceHouseCode);
	}

	public void saveHouseCenterInfo(String houseOrRoomId, String doorType, HouseSource houseSource, RentType rentType, Pager pager, Order order) throws Exception{

		Map<String, Object> urlVariables = new HashMap<String, Object>();
		String searchType=null;
		String Url=null;
		String result=null;
		if(DoorType.DAMEN.name().equals(doorType)){
			urlVariables.put("houseId", houseOrRoomId);
			Url = Config.HOUSE_CENTER_PATH+ Config.HOUSE_CENTER_GET_HOUSE_INFO;
			result = HttpClientUtils.post(Url, urlVariables);
		}
		else {
			urlVariables.put("roomId", houseOrRoomId);
			Url = Config.HOUSE_CENTER_PATH+ Config.HOUSE_CENTER_GET_ROOM_INFO;
			result = HttpClientUtils.get(Url, urlVariables);
		}
		
		//urlVariables.put("searchType", searchType);
		// String HOUSE_CENTER_PATH = "http://192.168.1.89:8080";
		// String HOUSE_CENTER_GET_HOUSE_INFO =
		// "/s/intelligentHouse/getApartmentById";
		
		
//		Map<String, Class<FinalHouseRoomModel>> map = new HashMap<String, Class<FinalHouseRoomModel>>();
//        map.put("rooms", FinalHouseRoomModel.class);
//		JSONObject obj = JSONObject.fromObject(result);
		Log.e("房源返回result:"+result);
		IntelligentHouseResult intelligentHouseResult = JSONObject.parseObject(result,IntelligentHouseResult.class);
		if ("0".equals(intelligentHouseResult.getCode())) {
			FinalHouseSourceModel[] FinalHouseSourceModelList = intelligentHouseResult.getData();
			if (FinalHouseSourceModelList != null && FinalHouseSourceModelList.length > 0) {
				FinalHouseSourceModel finalHouseSourceModel=FinalHouseSourceModelList[0];
				//2017-07-13, wangyuan, 接口的返回可能是：
				//{"code" : "0","data" : [ null ]}
				if(finalHouseSourceModel == null){
					throw new Exception("房源不存在:" + houseOrRoomId);
				}
				
				String houseId = finalHouseSourceModel.getHouseId();
				if (DoorType.DAMEN.name().equals(doorType)) {
					House houseDaMen = generalHouseSave(finalHouseSourceModel,houseSource,rentType, pager, order);
					for (FinalHouseRoomModel finalHouseRoomModel : finalHouseSourceModel.getRooms()) {
						House house = new House();
						house.setSourceHouseCode(finalHouseRoomModel.getRoomId());
						List<House> houseList = houseDao.findByModel(house, order, pager);
						if (houseList == null || houseList.size() == 0) {
							house.setHouseUnit(houseDaMen.getHouseUnit());
							house.setRentType(null);//小门的房源类型全部为null
							house.setRoomNo(houseDaMen.getRoomNo());
							house.setDoor(finalHouseRoomModel.getRoomName());
							house.setParentId(houseDaMen.getId());
							house.setOnLineSynCode("HR" + dateNumberService.getDateNumber());
							houseDao.save(house);
						}
					}
				} else {
					List<FinalHouseRoomModel> rooms =finalHouseSourceModel.getRooms();
					if (rooms.isEmpty()) {
						throw new Exception("小门同步数量为空");
					
					} 
					FinalHouseRoomModel room=null;//待同步小门数据
					for(FinalHouseRoomModel roomTem:rooms){
						if(roomTem.getRoomId().equals(houseOrRoomId)){
							room=roomTem;
							break;
						}
					}
					
					House houseDaMen = new House();
					houseDaMen.setSourceHouseCode(
							houseId);
					List<House> houseDaMenList = houseDao.findByModel(houseDaMen, order, pager);
					if (houseDaMenList.isEmpty()) {
						 houseDaMen = generalHouseSave(finalHouseSourceModel,houseSource,rentType, pager, order);
						
					} else if (houseDaMenList.size() > 1) {
						throw new DataErrorException("大门数量异常,小门无法同步");
						
					} else {
						houseDaMen = houseDaMenList.get(0);                   
					}
					if(rentType!=null){
						if(!houseDaMen.getRentType().name().equals(rentType.name())){
							throw new DataErrorException("大小门房屋类型不一致,无法同步");
						}
					}

					House houseXiaoMen = new House();
					houseXiaoMen.setSourceHouseCode(room.getRoomId());

					List<House> houseXiaoMenLIst = houseDao.findByModel(houseXiaoMen, order, pager);
					if (houseXiaoMenLIst == null || houseXiaoMenLIst.size() == 0) {
						houseXiaoMen.setRoomNo(houseDaMen.getRoomNo());
						houseXiaoMen.setDoor(room.getRoomName());
						houseXiaoMen.setSource(houseSource);
						houseXiaoMen.setHouseUnit(houseDaMen.getHouseUnit());
						houseXiaoMen.setRentType(rentType);
						houseXiaoMen.setParentId(houseDaMen.getId());
						houseXiaoMen.setOnLineSynCode("HR" + dateNumberService.getDateNumber());
						houseDao.save(houseXiaoMen);
					}

				}
			}
		}
		else{
			throw new Exception("獲取數據失敗:"+JSONObject.toJSONString(intelligentHouseResult));
		}

	}

	

	@Override
	public String generateAmmeter(String proCode, String houseId) throws Exception {
		House house = houseDao.getById(houseId);

		SupplierProduct supplierProduct = supplierProductDao.getByCode(proCode);
		Ammeter ammeter = new Ammeter();
		// doorLock.setOffLineSynCode(house.getOnLineSynYunYouCode());
		ammeter.setSupplierProduct(supplierProduct);
		ammeter.setDevId(null);
		ammeter.setHouse(house);
		ammeterDao.save(ammeter);
		Hardware hardware = new Hardware();
		hardware.setHouse(house);
		hardware.setDoorLock(null);
		hardware.setAmmeter(ammeter);
		hardware.setType(HardwareType.DIANBIAO);
		hardware.setHardwareStatus(HardwareStatus.WZC);
		String hardwareId = hardwareDao.save(hardware);
		IAmmeter iAmmeter = AmmeterSupplierFactory.getAmmeterSupplier(AmmeterSupplier.valueOf(supplierProduct.getSupplier().getCode()));
		// IDoorLock iDoorLock =
		// (IDoorLock)Class.forName(supplierProduct.getSdk()).newInstance();
		iAmmeter.syncHouse(house, supplierProduct);
		// 已安装
		// HardwareInstallOrder hardwareInstallOrder =
		// house.getHardwareInstallOrder();
		// hardwareInstallOrder.setAmmeterInstallStatus(HardwareInstallStatus.DONE);
		// hardwareInstallOrderDao.update(hardwareInstallOrder);
		return hardwareId;
	}
	
	
	/*
	 * 生成电表时, 同时设置监工师傅
	 */
	@Override
	public String generateAmmeterByInstallOrder(String proCode, String orderId,
			String userId) throws Exception{
		String result = null;
		HardwareInstallOrder order = hardwareInstallOrderService.get(orderId);
		House house = order.getHouse();
		List<Ammeter> ammeterList = house.getAmmeterList();
		if (ammeterList == null || ammeterList.size() <= 0) {
		    result = generateAmmeter(proCode, house.getId());
			order.getAmmeterInstallOrder().setInstallStatus(HardwareInstallStatus.APPLIED);

			//保存监工师傅信息
			hardwareCheckConfirmTaskService.savaAllConfirmTask(userId, orderId, "1");
		} else {
			// 如果该房屋已经安装/申请过电表，则不再创建电表，不再同步房屋
			order.getAmmeterInstallOrder().setInstallStatus(HardwareInstallStatus.FAIL_ALREADY);
		}
		
		hardwareInstallOrderService.update(order);
		return result;
	}

	@Override
	public String generateDoorLock(String proCode, String houseId) throws Exception {
		House house = houseDao.getById(houseId);

		SupplierProduct supplierProduct = supplierProductDao.getByCode(proCode);
		String hardwareId = generateDoorLock(supplierProduct, house);
		return hardwareId;
	}

	private String generateDoorLock(SupplierProduct supplierProduct, House house) throws Exception {
		DoorLock doorLock = new DoorLock();
		// doorLock.setOffLineSynCode(house.getOnLineSynYunYouCode());
		doorLock.setSupplierProduct(supplierProduct);
		doorLock.setDevId(null);
		doorLock.setHouse(house);
		doorLockDao.save(doorLock);
		Hardware hardware = new Hardware();
		hardware.setHouse(house);
		hardware.setDoorLock(doorLock);
		hardware.setAmmeter(null);
		hardware.setType(HardwareType.MENSUO);
		hardware.setHardwareStatus(HardwareStatus.WZC);
		String hardwareId = hardwareDao.save(hardware);
		IDoorLock iDoorLock= DoorLockSupplierFactory.getDoorLockSupplier(DoorLockSupplier.valueOf(supplierProduct.getSupplier().getCode()));
		iDoorLock.syncHouse(Lists.newArrayList(house), supplierProduct);
		return hardwareId;
	}
	
	/*
	 * 生成门锁时，同时保存监工师傅信息
	 */
	@Override
	public String generateDoorLockByInstallOrder(String proCode, String orderId, String userId) throws Exception {
		String result = null;
		
		HardwareInstallOrder order = hardwareInstallOrderService.get(orderId);
		House house = order.getHouse();
		List<DoorLock> doorLockList = house.getDoorLockList();
		if (doorLockList == null || doorLockList.size() <= 0) {
			result = generateDoorLock(proCode, house.getId());
			order.getDoorLockInstallOrder().setInstallStatus(HardwareInstallStatus.APPLIED);

			//保存监工师傅信息
			hardwareCheckConfirmTaskService.savaAllConfirmTask(userId, orderId, "0");
		} else {
			// 如果该房屋已经安装/申请过门锁，则不再创建门锁，不再同步房屋
			order.getDoorLockInstallOrder().setInstallStatus(HardwareInstallStatus.FAIL_ALREADY);
		}
		
		hardwareInstallOrderService.update(order);
		return result;
	}

	@Override
	public List<HouseDistrict> findByDistrictName(String districtName) {
		List<HouseDistrict> houseDistricts = houseDistrictDao.findByDistrictName(districtName);
		return houseDistricts;
	}

	@Override
	public List<HouseStreet> findHouseStreet(HouseStreet houseStreet) {
		return houseStreetDao.findByHouseStreet(houseStreet);
	}

	@Override
	public List<HouseDistrict> findHouseDistrict(List<String> ids, HouseDistrict district) {
		List<HouseDistrict> houseDistricts = houseDistrictDao.findByHouseStreetIds(ids, district);
		return houseDistricts;
	}

	@Override
	public List<HouseDistrict> findHouseDistrict(HouseDistrict houseDistrict) {
		List<HouseDistrict> houseDistricts = houseDistrictDao.findByHouseDistrict(houseDistrict);
		return houseDistricts;
	}

	@Override
	public List<Map<String, Object>> findByModelByHardware(House model, Order order, Pager pager) {
		List<Map<String, Object>> houses = houseDao.findByModelByHardware(model, order, pager);
		return houses;
	}

	@Override
	public List<House> findByModelBaidu(House model, Order order, Pager pager) {
		List<House> houses = houseDao.findByModelBaidu(model, order, pager);
		return houses;
	}

	@Override
	public House getHouseBySynCode(String onLineSynCode) {
		return houseDao.getHouseBySynCode(onLineSynCode);
	}
	
	@Override
	public Boolean bindMember(String[] houseOrRoomOpenIds, String openId) {
		for (String houseOrRoomOpenId : houseOrRoomOpenIds) {
			House house = houseDao.getSourceHouseCode(houseOrRoomOpenId);
			if (house == null) {
				throw new HardwareSdkException("没有找到房源");
			}
		}

		Member member = memberDao.getByOpenId(openId);
		if (member == null) {
			Member memberSave=new Member();
			memberSave.setAccountOpenId(openId);
			memberDao.save(memberSave);
			member=memberSave;
		}
		for (String houseOrRoomOpenId : houseOrRoomOpenIds) {
			House house = houseDao.getSourceHouseCode(houseOrRoomOpenId);
			List<Member> members = house.getMembers();
			Map<String, Member> itemMembers = Maps.uniqueIndex(members.iterator(), new Function<Member, String>() {
				@Override
				public String apply(Member input) {
					return input.getAccountOpenId();
				}
			});

			if (itemMembers.get(openId) == null) {
				houseDao.saveHousesMembers(house.getId(), member.getId());
			}
		}
		return true;
	}

	@Override
	public Boolean unbindMember(String[] houseOrRoomOpenIds, String openId) {
		for (String houseOrRoomOpenId : houseOrRoomOpenIds) {
			House house = houseDao.getSourceHouseCode(houseOrRoomOpenId);
			if (house == null) {
				throw new HardwareSdkException("没有找到房源");
			}
		}

		Member member = memberDao.getByOpenId(openId);
		if (member == null) {
			throw new HardwareSdkException("没有找到用户");
		}
		for (String houseOrRoomOpenId : houseOrRoomOpenIds) {
			House house = houseDao.getSourceHouseCode(houseOrRoomOpenId);
			List<Member> members = house.getMembers();

			for (Iterator iter = members.iterator(); iter.hasNext();) {
				Member memberTem = (Member) iter.next();
				if (memberTem.getAccountOpenId().equals(openId)) {
					iter.remove();
					house.setMembers(members);
					houseDao.update(house);
					break;
				}
			}
		}
		return true;
	}
	@Override
	public Boolean unbindMember(String[] houseOpenIds,String[] roomOpenIds, String openId) {
		
		
		//小门必须先处理
		for (String roomOpenId : roomOpenIds) {
			House house = houseDao.getSourceHouseCode(roomOpenId);
			if (house == null) {
				throw new HardwareSdkException("没有找到房源");
			}
		}

		Member member = memberDao.getByOpenId(openId);
		if (member == null) {
			throw new HardwareSdkException("没有找到用户");
		}
		for (String roomOpenId : roomOpenIds) {
			House house = houseDao.getSourceHouseCode(roomOpenId);
			List<Member> members = house.getMembers();

			for (Iterator iter = members.iterator(); iter.hasNext();) {
				Member memberTem = (Member) iter.next();
				if (memberTem.getAccountOpenId().equals(openId)) {
					iter.remove();
					house.setMembers(members);
					houseDao.update(house);
					break;
				}
			}
		}
		
		//大门处理
		for (String houseOrRoomOpenId : houseOpenIds) {
			House house = houseDao.getSourceHouseCode(houseOrRoomOpenId);
			if (house == null) {
				throw new HardwareSdkException("没有找到房源");
			}
		}

		for (String houseOrRoomOpenId : houseOpenIds) {
			House house = houseDao.getSourceHouseCode(houseOrRoomOpenId);
			//如果大门下，依然有小门，那么大门关系不可去除
			List<House> memberOwnHouses=member.getHouses();
			if(memberOwnHouses!=null&&memberOwnHouses.size()>0){
				for(House memberOwnHouse:memberOwnHouses){
					if(house.getId().equals(memberOwnHouse.getParentId())){
						continue ;
					}
				}
			}
			List<Member> members = house.getMembers();

			for (Iterator iter = members.iterator(); iter.hasNext();) {
				Member memberTem = (Member) iter.next();
				if (memberTem.getAccountOpenId().equals(openId)) {
					iter.remove();
					house.setMembers(members);
					houseDao.update(house);
					break;
				}
			}
		}
		return true;
	}
	@Override
	public House getYunYouCodeAndDoor(String onLineSynYunYouCode, String door) {
		House house = houseDao.getHouseBySynCode(onLineSynYunYouCode);// 先根据synCode获取大门信息
		if (StringUtils.isNotBlank(door)) {
			// 表示当前传入的是小门
			house = houseDao.getHomeByHouse(house.getId(), door);
			// 根据大门信息和小门号获取小门信息
		}
		return house;
	}

	@Override
	public Map<String, Object> syncHouse(House house, SupplierProduct supplierProduct) {
		return null;
	}

	private House generalHouseSave(FinalHouseSourceModel finalHouseSourceModel, HouseSource houseSource, RentType rentType, Pager pager,
                                   Order order) throws Exception {
		String houseId = finalHouseSourceModel.getHouseId();
		String houseNumber = finalHouseSourceModel.getHouseNumber();
		String provinceId = finalHouseSourceModel.getProvinceId();
		String provinceName = finalHouseSourceModel.getProvinceName();
		String cityId = finalHouseSourceModel.getCityId();
		String cityName =finalHouseSourceModel.getCityName();
		String cityAreaId = finalHouseSourceModel.getCityAreaId();
		String cityAreaName = finalHouseSourceModel.getCityAreaName();
		String streetId = finalHouseSourceModel.getStreetId();
		String street = finalHouseSourceModel.getStreetName();
		String address = finalHouseSourceModel.getStreetAddress();
		String villageId = finalHouseSourceModel.getVillageId();
		String villageName = finalHouseSourceModel.getVillageName();

		String buildingName = finalHouseSourceModel.getBuildingNumber();
		String buildingId = finalHouseSourceModel.getBuildingId();
		String unitNumber = finalHouseSourceModel.getUnitNumber();
		String unitUUID =finalHouseSourceModel.getUnitId();

		HouseStreet houseStreet = new HouseStreet();
		House house = new House();
		houseStreet.setSourceHouseStreetCode(streetId);
		List<HouseStreet> houseStreetList = houseStreetDao.findByModel(houseStreet, order, pager);
		if (houseStreetList != null && houseStreetList.size() > 0) {
			houseStreet = houseStreetList.get(0);
		} else {
			houseStreet.setProvinceId(provinceId);
			houseStreet.setProvinceName(provinceName);
			houseStreet.setCityId(cityId);
			houseStreet.setCityName(cityName);
			houseStreet.setDist(cityAreaId);
			houseStreet.setDistName(cityAreaName);
			houseStreet.setStreet(street);
			houseStreet.setAddress(address);
			houseStreetDao.save(houseStreet);
		}

		HouseDistrict houseDistric = new HouseDistrict();
		houseDistric.setSourceHouseDistrictCode(villageId);
		List<HouseDistrict> houseDistrictList = houseDistrictDao.findByModel(houseDistric, order, pager);
		if (houseDistrictList != null && houseDistrictList.size() > 0) {
			houseDistric = houseDistrictList.get(0);
		}  else {
			houseDistric.setDistrictName(villageName);
			houseDistric.setHouseStreet(houseStreet);
			houseDistric.setOnLineSynCode("DR" + dateNumberService.getDateNumber());
			houseDistrictDao.save(houseDistric);
		}
		HouseBlock houseBlock = new HouseBlock();
		houseBlock.setSourceHouseBlockCode(buildingId);
		List<HouseBlock> houseBlockList = houseBlockDao.findByModel(houseBlock, order, pager);
		if (houseBlockList != null && houseBlockList.size() > 0) {
			houseBlock = houseBlockList.get(0);
		} else {
			if (StringUtils.isBlank(buildingName)){
				buildingName=null;
			}
			houseBlock.setName(buildingName);
			houseBlock.setHouseDistrict(houseDistric);
			houseBlock.setOnLineSynCode("BR" + dateNumberService.getDateNumber());
			houseBlockDao.save(houseBlock);
		}

		HouseUnit houseUnit = new HouseUnit();
		houseUnit.setSourceHouseUnitCode(unitUUID);
		List<HouseUnit> houseUnitList = houseUnitDao.findByModel(houseUnit, order, pager);
		if (houseUnitList != null && houseUnitList.size() > 0) {
			houseUnit = houseUnitList.get(0);
		} else {
			if (StringUtils.isBlank(unitNumber)){
				unitNumber=null;
			}
			houseUnit.setName(unitNumber);
			houseUnit.setHouseBlock(houseBlock);
			houseUnit.setOnLineSynCode("UR" + dateNumberService.getDateNumber());
			houseUnitDao.save(houseUnit);
		}
		List<String> sourceHouseCodeList=new ArrayList();
		sourceHouseCodeList.add(houseId);
		house.setSourceHouseCodeList(sourceHouseCodeList);
		List<House> houseList = houseDao.findByModel(house, order, pager);
		if (houseList != null && houseList.size() > 0) {
			house = houseList.get(0);
		} else {
			house.setRoomNo(houseNumber);
			house.setRentType(rentType);
			house.setSourceHouseCode(houseId);
			house.setHouseUnit(houseUnit);
			house.setSource(houseSource);
			house.setOnLineSynCode("HR" + dateNumberService.getDateNumber());
			houseDao.save(house);
		}
		return house;
	}

	@Override
	public Integer getHouseMember(String houseId, String memberId) {
		return houseDao.getHouseMember(houseId,memberId);
	}

	@Override
	public List<House> findExistHouseByDeviceType(House model, Pager pager, Order order, String type, String userId) {
		// TODO Auto-generated method stub
		//TODO inner
	   return houseDao.findExistHouseByDeviceType(model, pager,order,type,userId);
	}

	@Override
	public List <House> findExistsRemarkHouse(House model, Order order, Pager pager){
		return this.houseDao.findExistsRemarkHouse( model, order, pager);
	}
}
