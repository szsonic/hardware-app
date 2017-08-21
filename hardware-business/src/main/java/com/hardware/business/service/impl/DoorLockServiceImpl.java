package com.hardware.business.service.impl;

import com.hardware.business.aop.annotations.OperationType;
import com.hardware.business.aop.enums.OperationCategory;
import com.hardware.business.aop.enums.OperationContent;
import com.hardware.business.client.HttpRequestUtils;
import com.hardware.business.dao.*;
import com.hardware.business.enums.FrozenStatus;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.*;
import com.hardware.business.service.DoorLockService;
import com.hardware.business.service.HardwareService;
import com.hardware.business.service.MemberLockPassService;
import com.hardware.business.utils.PropertiesUtils;
import com.hardware.service.sdk.IDoorLock;
import com.hardware.service.sdk.enums.DoorLockSupplier;
import com.hardware.service.sdk.factory.DoorLockSupplierFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 云柚门锁业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("doorLockService")
public class DoorLockServiceImpl extends BaseServiceSupport<DoorLock, String> implements DoorLockService {

	@Resource(name = "doorLockDao")
	private DoorLockDao doorLockDao;

	@Resource(name = "memberDao")
	private MemberDao memberDao;

	@Resource(name = "supplierProductDao")
	private SupplierProductDao supplierProductDao;

	@Resource(name = "supplierDao")
	private SupplierDao supplierDao;

	@Resource(name = "houseDao")
	private HouseDao houseDao;

	@Resource(name = "hardwareDao")
	private HardwareDao hardwareDao;

	@Resource(name = "memberLockPassDao")
	private MemberLockPassDao memberLockPassDao;

	@Resource(name = "hardwareService")
	private HardwareService hardwareService;

	@Resource(name = "memberLockPassService")
	private MemberLockPassService memberLockPassService;



	@Resource(name = "doorLockDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("doorLockDaoImpl") BaseHibernateDaoSupport<DoorLock, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public List<DoorLock> getDevidByUserOpenId(String openId) {
		return this.doorLockDao.getDevidByUserOpenId(openId);
	}

	@Override
	public void updateSurplusElectricity(String devId, int electric) {
		doorLockDao.updateelectricityStatus(devId, electric);
	}

	@Override
	public void updateInlineStatus(String devId, String status) {
		doorLockDao.updateInlineStatus(devId, status);
	}

	@Override
	public void updateSnByCode(String code, String door, final String sn) {
		House house=houseDao.getHouseBySynCode(code);//先根据synCode获取大门信息
		if(StringUtils.isNotBlank(door)){
			//表示当前传入的是小门
			house=houseDao.getHomeByHouse(house.getId(),door);
			//根据大门信息和小门号获取小门信息
		}
		List<DoorLock> doorLockList = house.getDoorLockList();
		for (DoorLock doorLock : doorLockList) {
			updateDoorLockStatus(doorLock,sn);
		}
	}

	@Override
	public void updateDoorLockStatus(DoorLock doorLock, String sn) {
		if (StringUtils.isBlank(doorLock.getDevId())) {
			this.doorLockDao.updateSnByYunYouCode(doorLock.getId(), sn);
			Hardware hardware = doorLock.getHardware();
			SupplierProduct supplier = getSupplierProByDevId(sn);
			if (supplier != null) {
				try {
					Map<String, Object> resultData = new HashMap<>();
					Map<String, Object> params = new HashMap<>();
					params.put("sn", sn);
					IDoorLock idoorLock= DoorLockSupplierFactory.getDoorLockSupplier(DoorLockSupplier.valueOf(supplier.getSupplier().getCode()));
					resultData = idoorLock.status(params);
					if ("true".equals(ObjectUtils.toString(resultData.get("success")))) {
						updateInlineStatus(sn, "on");
						int electric = Integer.parseInt(ObjectUtils.toString(resultData.get("electric")));
						updateSurplusElectricity(sn, electric);
//						if (StringUtils.isBlank(sn)) {
//							hardwareService.updateHardwareStatus(hardware.getId(), HardwareStatus.WZC);
//						} else {
//							if (ObjectUtils.toString(resultData.get("state")).equals("success") && electric < 20) {
//								hardwareService.updateHardwareStatus(hardware.getId(), HardwareStatus.YC);
//							} else {
//								hardwareService.updateHardwareStatus(hardware.getId(), HardwareStatus.ZC);
//							}
//						}
					} else {
						updateInlineStatus(sn, "off");
						//hardwareService.updateHardwareStatus(hardware.getId(), HardwareStatus.YC);
					}
				} catch (Exception e) {
					Log.e(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public DoorLock getDoorLockByDevId(String devId) {
		return doorLockDao.getDoorLockByDevId(devId);
	}

	@Override
	public Supplier getSupplierByDevId(String devId) {
		return doorLockDao.getSupplierByDevId(devId);
	}

	@Override
	public SupplierProduct getSupplierProByDevId(String devId) {
		return doorLockDao.getSupplierProByDevId(devId);
	}

	@Override
	public List<DoorLock> findDoorLockByDoor(String door) {
		return doorLockDao.findDoorLockByDoor(door);
	}

	public static void main(String[] args) {
		Properties config = PropertiesUtils.getProperties();
		String smsPath = config.getProperty("SMS_PATH");
		StringBuilder url = new StringBuilder(smsPath);
		Map<String, Object> params = new HashMap<>();
		params.put("smsChannelName", "wwtl");
		params.put("projectName", "zhsh");
		params.put("smsSendTo", "13564270329");
		params.put("smsSendMsg", "您此次的开门密码为：121212,有效期为1小时");
		params.put("batchSend", false);
		String post = HttpRequestUtils.post(url.toString(), params);
		System.out.println(post);
	}


	@Override
	@OperationType(value = OperationContent.FROZEN_PWD,type = OperationCategory.DOORLOCK)
	public void frozenPwd(DoorLock doorLock, MemberLockPass  memberLockPass,DoorLockOperationLog doorLockOperationLog) throws IllegalAccessException, InstantiationException, ClassNotFoundException,DataErrorException,ThirdPartyRequestException {
		IDoorLock doorLockSDK =DoorLockSupplierFactory.getDoorLockSupplier(DoorLockSupplier.valueOf(doorLock.getSupplierProduct().getSupplier().getCode()));
		doorLockSDK.frozenPassword(doorLock.getDevId(),memberLockPass,doorLockOperationLog);
	}

	@Override
	@OperationType(value = OperationContent.UNFROZEN_PWD,type = OperationCategory.DOORLOCK)
	public void unFrozenPwd(DoorLock doorLock,  MemberLockPass  memberLockPass,DoorLockOperationLog doorLockOperationLog) throws IllegalAccessException, InstantiationException, ClassNotFoundException, DataErrorException, ThirdPartyRequestException {
		IDoorLock doorLockSDK =DoorLockSupplierFactory.getDoorLockSupplier(DoorLockSupplier.valueOf(doorLock.getSupplierProduct().getSupplier().getCode()));
		doorLockSDK.unfrozenPassword(doorLock.getDevId(),memberLockPass,doorLockOperationLog);
	}

	@OperationType(value = OperationContent.ADD_PWD,type = OperationCategory.DOORLOCK)
	@Override
	public void addPassword(Date start, Date end, String pwd, DoorLock doorLock, Member member, DoorLockOperationLog doorLockOperationLog, String pwdType) throws Exception {
		//1、新增密码
		IDoorLock doorLockSDK =DoorLockSupplierFactory.getDoorLockSupplier(DoorLockSupplier.valueOf(doorLock.getSupplierProduct().getSupplier().getCode()));
		MemberLockPass memberLockPass=new MemberLockPass();
		Map<String,Object> result = doorLockSDK.addPassword(start,end,pwd,doorLock,doorLockOperationLog,memberLockPass,pwdType);
		//2、插入密码记录
		if(result.get("passIndex")!=null){
			memberLockPass.setDoorLock(doorLock);
			memberLockPass.setPassIndex((Integer) result.get("passIndex"));
			memberLockPass.setMember(member);
			memberLockPass.setPass(pwd);
			memberLockPass.setPassType(pwdType);
			memberLockPass.setFrozenStatus(FrozenStatus.UNFROZEN);
			memberLockPass.setEffectiveStart(start);
			memberLockPass.setEffectiveEnd(end);
			//默认15分钟若不回调，视为密码失效（密码失效目前只针对丁盯，云柚新增密码时若反正成功则直接变SUCCESS状态）。
			memberLockPassDao.save(memberLockPass);
			doorLockOperationLog.setMemberLockPass(memberLockPass);
		}else if(result.get("passwordId") != null){
			//云寓门锁新增密码返回的是 lockUserId（字符串）， 删除密码时使用 
			memberLockPass.setDoorLock(doorLock);
			memberLockPass.setPasswordId(result.get("passIndex").toString());
			memberLockPass.setMember(member);
			memberLockPass.setPass(pwd);
			memberLockPass.setPassType(pwdType);
			memberLockPass.setFrozenStatus(FrozenStatus.UNFROZEN);
			memberLockPass.setEffectiveStart(start);
			memberLockPass.setEffectiveEnd(end);
			
			memberLockPassDao.save(memberLockPass);
			doorLockOperationLog.setMemberLockPass(memberLockPass);
		}else{
			throw new DataErrorException("没有密码id，新增失败");
			//理论上不会出现这种可能，防止以后继续接其他供应商时有不返回该key的情况。
		}
	}


	@OperationType(value = OperationContent.DEL_PWD,type = OperationCategory.DOORLOCK)
	@Override
	public void delPassword(DoorLock doorLock, MemberLockPass memberLockPass,DoorLockOperationLog doorLockOperationLog) throws Exception{
		IDoorLock doorLockSDK =DoorLockSupplierFactory.getDoorLockSupplier(DoorLockSupplier.valueOf(doorLock.getSupplierProduct().getSupplier().getCode()));
		doorLockSDK.delPassword(memberLockPass,doorLockOperationLog,doorLock.getDevId());

	}

	@Override
	@OperationType(value = OperationContent.MODIFY_PWD,type = OperationCategory.DOORLOCK)
	public void modifyPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog operationLog,MemberLockPass memberLockPass) throws Exception {
		IDoorLock doorLockSDK =DoorLockSupplierFactory.getDoorLockSupplier(DoorLockSupplier.valueOf(doorLock.getSupplierProduct().getSupplier().getCode()));
		doorLockSDK.modifyPassword(start,end,pwd,doorLock,operationLog,memberLockPass);
	}

	@Override
	public Integer getDoorLockByInstalling() {
		// TODO Auto-generated method stub
		return doorLockDao.getDoorLockByInstalling();
		
	}

	@Override
	public Integer getDoorLockByInstalled() {
		// TODO Auto-generated method stub
		return doorLockDao.getDoorLockByInstalled();
	}

	@Override
	public Integer getDoorLockOfOn() {
		// TODO Auto-generated method stub
		return doorLockDao.getDoorLockOfOn();
	}

	@Override
	public Integer getDoorLockOfOff() {
		// TODO Auto-generated method stub
		return doorLockDao.getDoorLockOfOff();
	}
	@OperationType(value = OperationContent.SEARCH_DOORLOCK,type = OperationCategory.DOORLOCK)
	@Override
	public List<DoorLock> listByModel(DoorLock model, Order order, Pager pager) {
		return doorLockDao.listByModel(model,order,pager);
	}

	//
//	@Override
//	public Number maxPasswordIndex(DoorLock doorLock) {
//		return memberLockPassDao.maxPasswordIndex(doorLock);
//	}
	
	/*
	 * 更新设备devId(用于第三方回调)
	 * @param onlineSyncCode 房源唯一同步标识
	 *  注意: 这个方法用于向第三方同步房源时，大小房都是用自己的 onlineSyncCode
	 */
	@Override
	public void updateDevIdByOnlineSyncCode(String onlineSyncCode, String doorType, String devId)
			 throws Exception{
		House house = houseDao.getHouseBySynCode(onlineSyncCode);
		if(ValidatorUtils.isEmpty(house)){
			Log.i("onlineSyncCode 对应的房源不存在:" + onlineSyncCode);
			throw new DataErrorException("onlineSyncCode 对应的房源不存在");
		}
		
		List<DoorLock> doorLockList = house.getDoorLockList();
		if(ValidatorUtils.isEmpty(doorLockList)){
			Log.i("该房源未预安装门锁");
			throw new DataErrorException("该房源未预安装门锁");
		}
		
		for (DoorLock doorLock : doorLockList) {
			updateDoorLockStatus(doorLock, devId);
		}
	}
}
