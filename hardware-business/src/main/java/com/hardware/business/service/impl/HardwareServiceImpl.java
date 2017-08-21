package com.hardware.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.hardware.business.dao.*;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.enums.HardwareHdStatus;
import com.hardware.business.enums.HardwareStatus;
import com.hardware.business.enums.HardwareType;
import com.hardware.business.model.Ammeter;
import com.hardware.business.model.Hardware;
import com.hardware.business.model.House;
import com.hardware.business.model.SupplierProduct;
import com.hardware.business.service.DoorLockService;
import com.hardware.business.service.HardwareService;
import com.hardware.service.sdk.IAmmeter;
import com.hardware.service.sdk.enums.AmmeterSupplier;
import com.hardware.service.sdk.factory.AmmeterSupplierFactory;
import com.hardware.service.sdk.fengdian.FDAmmeter;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 硬件设备业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("hardwareService")
public class HardwareServiceImpl extends BaseServiceSupport<Hardware, String> implements HardwareService {

	@Resource(name = "ammeterDao")
	private AmmeterDao ammeterDao;

	@Resource(name = "doorLockDao")
	private DoorLockDao doorLockDao;

	@Resource(name = "houseDao")
	private HouseDao houseDao;

	@Resource(name = "supplierProductDao")
	private SupplierProductDao supplierProductDao;

	@Resource(name = "supplierDao")
	private SupplierDao supplierDao;

	@Resource(name = "hardwareDao")
	private HardwareDao hardwareDao;

	@Autowired
	private MemcachedManager memcachedManager;

	@SuppressWarnings("unused")
	@Autowired
	private DateNumberService dateNumberService;

	@SuppressWarnings("unused")
	@Autowired
	private DoorLockService doorLockService;

	@Resource(name = "hardwareDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("hardwareDaoImpl") BaseHibernateDaoSupport<Hardware, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public int updateHardwareStatus(String id, HardwareStatus status) {
		return hardwareDao.updateHardwareStatus(id, status);
	}

	@Override
	public Hardware getByDevId(String devId) {
		return hardwareDao.getByDevId(devId);
	}

	@Override
	public int updateHdStatus(String devId, HardwareHdStatus status) {
		return hardwareDao.updateHdStatusByDevId(devId, status);
	}

	@Override
	public int updateSettledStatusByDevId(String devId, String status) {
		// TODO Auto-generated method stub
		return hardwareDao.updateSettledStatusByDevId(devId, status);
	}

	@Override
	public String createDianBiao(String supplierProCode,Ammeter ammeter ,House house) throws Exception {
		String offLineSynCode= ammeter.getOffLineSynCode();
		String devId = getFengDianDevId(ammeter.getOffLineSynCode());
		if(StringUtils.isBlank(devId)){
			return null;
		}
		SupplierProduct supplierProduct = supplierProductDao.getByCode(supplierProCode);
		List<Ammeter> ammeterList=house.getAmmeterList();
		Ammeter tempAmmeter=ammeterList.get(0);
		tempAmmeter.setDevId(devId);
		tempAmmeter.setHouse(house);
		tempAmmeter.setSupplierProduct(supplierProduct);
		tempAmmeter.setOffLineSynCode(offLineSynCode);
		tempAmmeter.setInstallTime(new Date());
		ammeterDao.update(tempAmmeter);
//		Hardware hardware = new Hardware();
//		hardware.setHouse(house);
//		hardware.setAmmeter(ammeter);
//		hardware.setType(HardwareType.DIANBIAO);
//		hardwareDao.save(hardware);
	
		//电费模式设置
		String payMod ="1";
		if (ValidatorUtils.isNotEmpty(payMod)) {
			Log.i("===访问蜂电电表设置付费模式===");
			Map<String, Object> params = new HashMap<>();
			params.put("uuid", tempAmmeter.getDevId());
			params.put("mod", payMod);
			//Class<?> clazz = Class.forName("com.hardware.service.sdk.fengdian.FdAmmeterSDK");
			//Constructor<?> constructor = clazz.getConstructor(MemcachedManager.class);
			//IAmmeter iAmmeter = (IAmmeter) constructor.newInstance(memcachedManager);

			IAmmeter iAmmeter = AmmeterSupplierFactory.getAmmeterSupplier(AmmeterSupplier.FENGDIAN);
			//本来这就是蜂电的逻辑，所以这里直接写死枚举类型
			AmmeterResult ammeterResult = iAmmeter.setPayMod(params);
			Log.i("===访问蜂电电表设置付费模式结果===" + JSON.toJSONString(ammeterResult));
		}
		return devId;
		
	}

	private String getFengDianDevId(String offLineSynCode) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 考虑2种情况，蜂电APP上没有绑定的时候需要绑定，如有绑定直接拿devId
		Class<?> clazz = Class.forName("com.hardware.service.sdk.fengdian.FdAmmeterSDK");
		Constructor<?> constructor = clazz.getConstructor(MemcachedManager.class);
		FDAmmeter fdammeter = (FDAmmeter) constructor.newInstance(memcachedManager);
		Log.i("新建电表硬件");
	
		String devId = fdammeter.getUuid(offLineSynCode);
		if (StringUtils.isNotBlank(devId)) {
			Log.i("新建电表硬件DEVID设置成功");
			return devId;
		} else {
			Log.i("新建电表硬件DEVID设置异常");
			return null;
		}
	}

	@Override
	public int deleteById(String id, String type) {
		Hardware hardware = hardwareDao.getById(Hardware.class, id);
		hardwareDao.deleteById(id);
		if (type.equals(HardwareType.DIANBIAO.toString())) {
			ammeterDao.deleteById(hardware.getAmmeter().getId());
		} else if (type.equals(HardwareType.MENSUO.toString())) {
			doorLockDao.deleteById(hardware.getDoorLock().getId());
		}
		return 1;
	}


}
