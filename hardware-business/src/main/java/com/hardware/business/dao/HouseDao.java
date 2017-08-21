package com.hardware.business.dao;

import com.hardware.business.model.Hardware;
import com.hardware.business.model.House;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 硬件设备类
 * 
 * @author zhongqi
 * 
 */
@Repository("houseDao")
public class HouseDao extends BaseHibernateDaoSupport<House, String> {
	
	@Override
	public List<House> findByModel(House model, Order order, Pager pager) {
		model = model == null ? new House() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from House c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getBlock()) ? " and c.houseUnit.houseBlock.name='" + model.getBlock() + "' ":"");
        hql.append(ValidatorUtils.isNotEmpty(model.getUnit())? " and c.houseUnit.name='" + model.getUnit() + "' ":"");
		hql.append(ValidatorUtils.isNotEmpty(model.getRoomNo()) ? " and c.roomNo=:roomNo " : "");
		if (model.getParentIdNullFlag() != null && model.getParentIdNullFlag()) {
			hql.append(" and c.parentId is null");
		} else {
			hql.append(ValidatorUtils.isNotEmpty(model.getParentId()) ? " and c.parentId=:parentId " : "");
		}

		if (StringUtils.isNotBlank(model.getProvinceName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.provinceName = '" + model.getProvinceName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getCityName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.cityName = '" + model.getCityName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getDistName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.distName = '" + model.getDistName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getStreet())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.street like '%" + model.getStreet() + "%' ");
		}
		if (model.getRentType()!=null) {
			hql.append(" and c.rentType = '" +model.getRentType().name()+ "' ");
		}
		if (StringUtils.isNotBlank(model.getDistrictName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.districtName like '%" + model.getDistrictName() + "%' ");
		}
        if (StringUtils.isNotBlank(model.getDistrictId())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.id = '" + model.getDistrictId() + "' ");
		}
        if (StringUtils.isNotBlank(model.getSourceHouseCode())) {
			hql.append(" and c.sourceHouseCode = '" + model.getSourceHouseCode() + "' ");
		}
        if (model.getSourceHouseCodeList()!=null&&model.getSourceHouseCodeList().size()>0) {
			hql.append(" and c.sourceHouseCode in (:sourceHouseCodeList) ");
		}
        if (model.getAmmeterWaitForDone()!=null&&model.getAmmeterWaitForDone()) {
			hql.append(" and exists (select m.id from Ammeter m where m.house.id=c.id and m.devId is null ) ");
		}
//		if (model.getHardwareInstallOrder() != null && model.getHardwareInstallOrder().getAmmeterInstallStatus() != null) {
//			hql.append(" and c.hardwareInstallOrder.ammeterInstallStatus = '" + model.getHardwareInstallOrder().getAmmeterInstallStatus() + "' ");
//		}
//		if (model.getHardwareInstallOrder() != null && model.getHardwareInstallOrder().getDoorLockInstallStatus() != null) {
//			hql.append(" and c.hardwareInstallOrder.doorLockInstallStatus = '" + model.getHardwareInstallOrder().getDoorLockInstallStatus() + "' ");
//		}
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}
	
	
	public List<House> findByModelBaidu(House model, Order order, Pager pager) {
		model = model == null ? new House() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("select  c from House as c ");
		if (model.getHouseMemberNullFlag() != null && model.getHouseMemberNullFlag()) {
			hql.append(" inner join fetch  c.members as m ");
		}

		hql.append(" where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getBlock()) ? " and c.houseUnit.houseBlock.name=:houseUnit.houseBlock.name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getUnit()) ? " and c.houseUnit=:houseUnit.name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getRoomNo()) ? " and c.roomNo=:roomNo " : "");
		if (model.getHouseMemberNullFlag() != null && model.getHouseMemberNullFlag()) {
			if(StringUtils.isNotBlank(model.getMobile())){
				hql.append(" and m.id is not null and m.mobile ='"+model.getMobile()+"'");
			}
			else{
				hql.append(" and m.id is not null and m.mobile is not null");
			}
			
		}

		if (model.getParentIdNullFlag() != null && model.getParentIdNullFlag()) {
			hql.append(" and c.parentId is null");
		} else {
			hql.append(ValidatorUtils.isNotEmpty(model.getParentId()) ? " and c.parentId=:parentId " : "");
		}


		if (StringUtils.isNotBlank(model.getProvinceName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.provinceId = '" + model.getProvinceName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getCityName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.cityId = '" + model.getCityName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getDistName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.dist = '" + model.getDistName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getStreet())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.street like '%" + model.getStreet() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getType())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStructureType = '" + model.getType()+ "' ");
		}
		if (StringUtils.isNotBlank(model.getDistrictName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.districtName like '%" + model.getDistrictName() + "%' ");
		}
		if (StringUtils.isNotBlank(model.getDistrictId())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.id = '" + model.getDistrictId() + "' ");
		}


//		if (model.getHardwareInstallOrder() != null && model.getHardwareInstallOrder().getAmmeterInstallStatus() != null) {
//			hql.append(" and c.hardwareInstallOrder.ammeterInstallStatus = '" + model.getHardwareInstallOrder().getAmmeterInstallStatus() + "' ");
//		}
//		if (model.getHardwareInstallOrder() != null && model.getHardwareInstallOrder().getDoorLockInstallStatus() != null) {
//			hql.append(" and c.hardwareInstallOrder.doorLockInstallStatus = '" + model.getHardwareInstallOrder().getDoorLockInstallStatus() + "' ");
//		}
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return findAllByQueryString(hql.toString(), model, pager);
	}

	public List<Map<String, Object>> findByModelByHardware(House model, Order order, Pager pager) {
		model = model == null ? new House() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("select hs.*,hd.*,h.* from house as h left join house_district as hd on h.houseDistrictId = hd.id left join house_street as hs on hs.id = hd.houseStreetId ");
		hql.append(" where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and h.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getBlock()) ? " and h.houseUnit.houseBlock.name=:houseUnit.houseBlock.name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getUnit()) ? " and h.houseUnit=:houseUnit.name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getRoomNo()) ? " and h.roomNo=:roomNo " : "");

		hql.append(ValidatorUtils.isNotEmpty(model.getProvinceName()) ? " and hs.provinceId= '" + model.getProvinceName() + "' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getCityName()) ? " and hs.cityId= '" + model.getCityName() + "' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDistName()) ? " and hs.dist= '" + model.getDistName() + "' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStreet()) ? " and hs.street= '" + model.getStreet() + "' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDistrictName()) ? " and hd.districtName= '" + model.getDistrictName() + "' " : "");

		hql.append(" order by h.createTime desc ");
		return findByQueryStringByHardware(hql.toString(), model, pager);
	}

	public List<Map<String, Object>> findByQueryStringByHardware(String queryString, House model, Pager pager) {
		int firstResult = 0;
		int maxResults = 0;
		String countQuery = "select count(*) from " + StringUtils.substringAfter(queryString, "from");
		if (pager != null) {
			Integer count = this.countSQL(countQuery, model);
			if (count == 0) {
				return new ArrayList<Map<String, Object>>();
			}
			pager.setRecordCount(count);
			if (pager.getRecordCount() < 1)
				return new ArrayList<Map<String, Object>>();
			firstResult = (pager.getPage() < 1) ? 0 : (pager.getPage() - 1) * pager.getPageSize();
			maxResults = pager.getPageSize();
		}
		List<Map<String, Object>> list = this.querySQL(queryString, model, firstResult, maxResults);
		return list;
	}

	public House getByCode(String code) {
		List<House> houses = this.queryHQL("from House a where a.status='AVAILABLE' and a.code='" + code + "'");
		return houses != null && houses.size() > 0 ? houses.get(0) : null;
	}

	public House getById(String id) {
		List<House> houses = this.queryHQL("from House a where a.status='AVAILABLE' and a.id='" + id + "'");
		return houses != null && houses.size() > 0 ? houses.get(0) : null;
	}

	public List<Hardware> findByLandlordCode(String code) {
		List<House> houses = this.queryHQL("from House a where a.status='AVAILABLE' and a.sourceHouseCode='" + code + "'");
		House house = houses != null && houses.size() > 0 ? houses.get(0) : null;
		if (house != null) {
			return house.getHardwareList();
		}
		return null;
	}

	public House getSourceHouseCode(String sourceHouseCode) {
		List<House> houses = this.queryHQL("from House a where a.status='AVAILABLE' and a.sourceHouseCode='" + sourceHouseCode + "'");
		House house = houses != null && houses.size() > 0 ? houses.get(0) : null;
		return house;
	}
	@Deprecated
	public House getYunYouCodeAndDoor(String onLineSynCode, String door) {
		List<House> houses;
		if (StringUtils.isBlank(door)) {
			houses = this.queryHQL("from House h  where h.onLineSynCode='" + onLineSynCode + "'  and h.door is null ");
		} else {
			houses = this.queryHQL("from House h  where h.onLineSynCode='" + onLineSynCode + "'  and h.door = '" + door + "' ");
		}
		House house = houses != null && houses.size() > 0 ? houses.get(0) : null;
		return house;
	}

	/**
	 * 根据同步code获取房源信息
	 * @param onLineSynCode  同步code
	 * @return 房源信息
	 */
	public House getHouseBySynCode(String onLineSynCode) {
		List<House> houses=this.queryHQL("from House h  where h.onLineSynCode='" + onLineSynCode + "'");
		return  houses != null && houses.size() > 0 ? houses.get(0) : null;
	}

	/**
	 * 根据门牌号和大门信息获取小门房源
	 * @param parentId 大门id
	 * @param door 门牌号
	 * @return 小门房源信息
	 */
	public House getHomeByHouse(String parentId,String door) {
		List<House> houses=this.queryHQL("from House h  where h.parentId='" + parentId + "'  and h.door = '" + door + "' ");
		return  houses != null && houses.size() > 0 ? houses.get(0) : null;
	}

	public Integer saveHousesMembers(String houseId, String memberId) {
		Integer result = this.executeSQL("insert into houses_members (memberId,houseId) values ( '"
		    + memberId + "','" + houseId + "')");
		return result;
	}


	public Integer getHouseMember(String houseId, String memberId ){
		return   this.countSQL("select count(1) from houses_members where memberId='"+memberId+"' and houseId='"+houseId+"'");
	}

	public Integer deleteHouseMember(String houseId, String memberId ){
		return   this.executeSQL("delete from houses_members where memberId='"+memberId+"' and houseId='"+houseId+"'");
	}

	@SuppressWarnings("unchecked")
	public List<House> findAllByQueryString(final String queryString, final House model,final Pager pager) {

		return (List<House>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				
				int  firstResult = (pager.getPage() < 1) ? 0 : (pager.getPage() - 1) * pager.getPageSize();
				int maxResults = pager.getPageSize();
				
				Query query = session.createQuery(queryString);
				if (firstResult > 0)
					query.setFirstResult(firstResult);
				if (maxResults > 0)
					query.setMaxResults(maxResults);
				if (model != null)
					query.setProperties(model);

				return query.list();
			}
		});

	}
	
	/*
	 * updated:wangyuan, 2017-07-25
	 * 1)HQL do not support 'inner join on'
	 * 2)inner join HouseConfirmTask hck will cause 'path expected for join!' Exception
	 */
	public List<House> findExistHouseByDeviceType(House model, Pager pager, Order order,String type,String userId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select  c.house ");
		if(type.equals("0"))
		{
		hql.append(" from DoorLock c");
		}else if(type.equals("1"))
		{
			hql.append(" from Ammeter c");
		}
		hql.append(" ,HouseConfirmTask hck, User u");
		hql.append(" inner join c.house ");
		hql.append(" inner join c.house.houseUnit");
		hql.append(" inner join c.house.houseUnit.houseBlock");
		hql.append(" inner join c.house.houseUnit.houseBlock.houseDistrict");
		hql.append(" inner join c.house.houseUnit.houseBlock.houseDistrict.houseStreet");
		//hql.append(" inner join HouseConfirmTask hck on hck.house.id=c.house.id");
		//hql.append(" inner join User u on u.id=hck.user.id and u.id='" + userId + "'");
		hql.append(" where c.status='AVAILABLE' ");
		hql.append(" and c.devId is not null ");
		hql.append("and c.house.status='AVAILABLE' ");
		hql.append("and c.house.houseUnit.status='AVAILABLE' ");
		hql.append("and c.house.houseUnit.houseBlock.status='AVAILABLE' ");
		hql.append("and c.house.houseUnit.houseBlock.houseDistrict.status='AVAILABLE' ");
		hql.append("and c.house.houseUnit.houseBlock.houseDistrict.houseStreet.status='AVAILABLE' ");
		hql.append("and c.house.houseUnit.houseBlock.houseDistrict.id='"+model.getDistrictId()+"'");	
		//hql.append("and u.id='"+userId+"'");
		
		hql.append(" and hck.house.id=c.house.id ");
		hql.append(" and u.id=hck.user.id ");
		
		hql.append(" and not exists (select m.id from HouseConfirmedRecord  m where m.house.id=c.house.id and m.status='AVAILABLE')");
		return this.findByQueryString(hql.toString(), pager);
	}

	public List <House> findExistsRemarkHouse(House model,Order order,Pager pager){
		model = model == null ? new House() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from House c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getBlock()) ? " and c.houseUnit.houseBlock.name like '%" + model.getBlock() + "%' ":"");
		hql.append(ValidatorUtils.isNotEmpty(model.getUnit())? " and c.houseUnit.name like '%" + model.getUnit() + "%' ":"");
		hql.append(ValidatorUtils.isNotEmpty(model.getRoomNo()) ? " and c.roomNo=:roomNo " : "");
		if (StringUtils.isNotBlank(model.getProvinceName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.provinceName = '" + model.getProvinceName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getCityName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.cityName = '" + model.getCityName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getDistName())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.distName = '" + model.getDistName() + "' ");
		}
		if (StringUtils.isNotBlank(model.getStreet())) {
			hql.append(" and c.houseUnit.houseBlock.houseDistrict.houseStreet.street like '%" + model.getStreet() + "%' ");
		}
		if(StringUtils.isNotBlank(model.getRemark())){
			hql.append(" and c.remark like '%" + model.getRemark() + "%' ");
		}
		hql.append(" and c.remark is not null and c.remark <> ''");
		return this.findByQueryString(hql.toString(), model, pager);
	}

}
