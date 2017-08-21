package com.hardware.business.service.impl;

import com.hardware.business.aop.annotations.AmmeterActionLog;
import com.hardware.business.aop.annotations.OperationType;
import com.hardware.business.aop.enums.AmmeterOperateParams;
import com.hardware.business.aop.enums.OperationCategory;
import com.hardware.business.aop.enums.OperationContent;
import com.hardware.business.dao.AmmeterDao;
import com.hardware.business.dao.HardwareDao;
import com.hardware.business.dao.HouseDao;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.exception.BusinessException;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.model.Ammeter;
import com.hardware.business.model.House;
import com.hardware.business.model.Supplier;
import com.hardware.business.model.SupplierProduct;
import com.hardware.business.service.AmmeterService;
import com.hardware.service.sdk.IAmmeter;
import com.hardware.service.sdk.enums.AmmeterSupplier;
import com.hardware.service.sdk.factory.AmmeterSupplierFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电表业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("ammeterService")
public class AmmeterServiceImpl extends BaseServiceSupport<Ammeter, String> implements AmmeterService {

	@Autowired
	private MemcachedManager memcachedManager;
	
	@Resource(name = "ammeterDao")
	private AmmeterDao ammeterDao;

	@Resource(name = "houseDao")
	private HouseDao houseDao;

	@Resource(name = "hardwareDao")
	private HardwareDao hardwareDao;
	@Resource(name = "ammeterDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("ammeterDaoImpl") BaseHibernateDaoSupport<Ammeter, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public String getDevidByUserMobile(String openId) {
		return this.ammeterDao.getDevidByUserMobile(openId);
	}

	@Override
	public Supplier getSupplierByDevId(String devId) {
		return ammeterDao.getSupplierByDevId(devId);
	}

	@Override
	public SupplierProduct getSupplierProByDevId(String devId) {
		return ammeterDao.getSupplierProByDevId(devId);
	}

	@Override
	public Ammeter getAmmeterByDevId(String devId) {
		return ammeterDao.getAmmeterByDevId(devId);
	}

	@Override
	public String pay(String devId, String amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPayMod(String devId, String mod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateelectricityStatus(String devId, String status) {
		ammeterDao.updateelectricityStatus(devId, status);
	}

	@Override
	public void updateInlineStatus(String devId, String status) {
		ammeterDao.updateInlineStatus(devId, status);
	}




	
	/*
	 * 第三方派遣师傅安装电表后回调接口：更新电表信息
	 * @param syncOlineCode: 同步Code #注意：每个房源对应自己的onlineCode
	 * @param doorType：1 大门  2 小门
	 * @param devId：设备唯一标识
	 */
	public void updateByDevIdAndSyncCode(String onlineSyncCode, String doorType,
			String devId) throws Exception{
		House house = houseDao.getHouseBySynCode(onlineSyncCode);
		
		if(ValidatorUtils.isEmpty(house)){
			Log.i("onlineSyncCode 对应的房源不存在:" + onlineSyncCode);
			throw new DataErrorException("onlineSyncCode 对应的房源不存在");
		}
		
		List<Ammeter> ammeters = house.getAmmeterList();
		if(ValidatorUtils.isEmpty(ammeters)){
			Log.i("该房源未预安装电表");
			throw new DataErrorException("该房源未预安装电表");
		}
		
		boolean updateed = false;
		for(Ammeter ammeter : ammeters){
			boolean changed = updateAmmeterStatusAfterInstall(ammeter, devId);
			if(!updateed){
				updateed = changed;
			}
		}
		
		if(!updateed){
			throw new BusinessException("更新电表状态失败");
		}
	}
	
	/*
	 * 安装电表后, 更新电表信息 
	 * task：设置电表的devId
	 *      调用第三方接口(status) 更新电表的状态
	 */
	private boolean updateAmmeterStatusAfterInstall(Ammeter ammeter, String devId){
		boolean result = false;
	   
		//针对未设置devId的设备
		if(ValidatorUtils.isEmpty(ammeter.getDevId())){
			int change = this.ammeterDao.updateAmmeteDevIdSetting(ammeter.getId(), devId);
			if(change > 0){
				result = true;
				
				SupplierProduct supplier = this.ammeterDao.getSupplierProByDevId(devId);
				if (supplier != null) {
					try {
						Map<String, Object> resultData = new HashMap<>();
						Map<String, Object> params = new HashMap<>();
						params.put("devId", devId);
						IAmmeter iammeter = AmmeterSupplierFactory.getAmmeterSupplier(AmmeterSupplier.valueOf(supplier.getSupplier().getCode()));
						resultData = iammeter.status(params);
						if ("true".equals(ObjectUtils.toString(resultData.get("success")))) {
							if (ObjectUtils.toString(resultData.get("electricDoorSwitchStatus")).equals("off")) {
								updateelectricityStatus(devId, "off");
							} else if (ObjectUtils.toString(resultData.get("electricDoorSwitchStatus")).equals("on")) {
								updateelectricityStatus(devId, "on");
							}
							if (ObjectUtils.toString(resultData.get("wifiOnlineStatus")).equals("on")) {
								updateInlineStatus(devId, "on");
							} else if (ObjectUtils.toString(resultData.get("wifiOnlineStatus")).equals("off")) {
								updateInlineStatus(devId, "off");
							}
						}else{
							updateInlineStatus(devId, "off");
						}
					} catch (Exception e) {
						Log.e(e.getMessage(), e);
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public Integer getAmmeterByInstalling() {
		// TODO Auto-generated method stub
		return ammeterDao.getAmmeterByInstalling();
	}

	@Override
	public Integer getAmmeterByInstalled() {
		// TODO Auto-generated method stub
		return ammeterDao.getAmmeterByInstalled();
	}

	@Override
	public Integer getAmmeterOfOn() {
		// TODO Auto-generated method stub
		return ammeterDao.getAmmeterOfOn();
	}

	@Override
	public Integer getAmmeterOfOff() {
		// TODO Auto-generated method stub
		return ammeterDao.getAmmeterOfOff();
	}
	
	/*
	 * 分页查询已安装电表
	 */
	@OperationType(value = OperationContent.SEARCH_AMMETER,type = OperationCategory.AMMETER)
	@Override
	public List<Ammeter> listByModel(Ammeter model, Order order, Pager pager) throws Exception{
		return ammeterDao.listByModel(model, order, pager);
	}

	@OperationType(value = OperationContent.CONTROL_AMMETER,type = OperationCategory.AMMETER)
	@Override
	public AmmeterResult control(Ammeter ammeter, @AmmeterActionLog(param = AmmeterOperateParams.ACTION) String action, SupplierProduct supplierProduct){
		IAmmeter iAmmeter = AmmeterSupplierFactory.getAmmeterSupplier(AmmeterSupplier.valueOf(supplierProduct.getSupplier().getCode()));
		Map<String,Object> map=new HashMap<>();
		map.put("action",action);
		map.put("uuid",ammeter.getDevId());
		return iAmmeter.control(map);
	}

	@OperationType(value = OperationContent.RESET_AMMETER,type = OperationCategory.AMMETER)
	@Override
	public void reset(Ammeter ammeter, SupplierProduct supplierProduct) throws Exception {
		IAmmeter iAmmeter = AmmeterSupplierFactory.getAmmeterSupplier(AmmeterSupplier.valueOf(supplierProduct.getSupplier().getCode()));
		iAmmeter.reset(ammeter.getDevId());
	}

	@OperationType(value = OperationContent.MODIFY_AMMETER_PRICE,type = OperationCategory.AMMETER)
	@Override
	public void updatePrice(Ammeter ammeter, @AmmeterActionLog(param = AmmeterOperateParams.PRICE) Float price) {
		ammeter.setElectricityPrice(price);
		ammeterDao.update(ammeter);
	}

	@OperationType(value = OperationContent.CHARGE_AMMETER,type = OperationCategory.AMMETER)
	@Override
	public void chargeAmount(Ammeter ammeter, @AmmeterActionLog(param = AmmeterOperateParams.CHARGE) Float amount, SupplierProduct supplierProduct) throws Exception {
		IAmmeter iAmmeter = AmmeterSupplierFactory.getAmmeterSupplier(AmmeterSupplier.valueOf(supplierProduct.getSupplier().getCode()));
		iAmmeter.chargeAmount(ammeter.getDevId(),amount);
	}

	/*
	 * 获取所有已安装电表
	 */
	public List<Ammeter> findAllInstalledAmmeters() throws Exception{
		return ammeterDao.findAllInstalledAmmeters();
	}
}
