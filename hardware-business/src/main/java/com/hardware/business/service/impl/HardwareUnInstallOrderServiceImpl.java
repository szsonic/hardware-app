package com.hardware.business.service.impl;

import com.hardware.business.dao.AmmeterDao;
import com.hardware.business.dao.DoorLockDao;
import com.hardware.business.dao.HardwareUnInstallOrderDao;
import com.hardware.business.dao.ProjectDao;
import com.hardware.business.enums.HardwareType;
import com.hardware.business.enums.HardwareUnInstallStatus;
import com.hardware.business.exception.ValidateException;
import com.hardware.business.model.Ammeter;
import com.hardware.business.model.DoorLock;
import com.hardware.business.model.HardwareUnInstallOrder;
import com.hardware.business.model.Project;
import com.hardware.business.service.HardwareUnInstallOrderService;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.log.Log;
import org.iframework.support.domain.enums.RecordStatus;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("hardwareUnInstallOrderService")
public class HardwareUnInstallOrderServiceImpl extends BaseServiceSupport<HardwareUnInstallOrder, String>
		implements HardwareUnInstallOrderService {
	
	@Resource(name = "hardwareUnInstallOrderDao")
	private HardwareUnInstallOrderDao hardwareUnInstallOrderDao;
	
	@Resource(name = "hardwareUnInstallOrderDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("hardwareUnInstallOrderDaoImpl") BaseHibernateDaoSupport<HardwareUnInstallOrder, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}
	@Resource(name = "ammeterDao")
	private AmmeterDao ammeterDao;

	@Resource(name = "projectDao")
	private ProjectDao projectDao;
	
	@Resource(name = "doorLockDao")
	private DoorLockDao doorLockDao;
	@Override
	public void unInstallHardware(String ammeterOrDoorLockId, String type, String projectCode, String version)throws Exception {
	
		// TODO Auto-generated method stub
		String params = "[ammeterOrDoorLockId]:" + ammeterOrDoorLockId + "[type]:" + type + "[type]:" + projectCode
				+ "[projectCode]:";
		if (StringUtils.isBlank(projectCode)) {
			Log.e("必须要指定项目编码");
			throw new ValidateException("必须要指定项目编码");
		}
		Project project = projectDao.getByCode(projectCode);
		if (project == null) {
			Log.e("项目编码没有对应的项目");
			throw new ValidateException("项目编码没有对应的项目");
		}

		if (HardwareType.DIANBIAO.name().equals(type)) {
			Ammeter ammeter = new Ammeter();
			ammeter.setId(ammeterOrDoorLockId);
			ammeter = ammeterDao.findById(ammeter);
			if (ammeter == null) {
				Log.e("电表编码没有对应的记录");
				throw new ValidateException("电表编码没有对应的记录");
			}

			HardwareUnInstallOrder hardwareUnInstallOrder = new HardwareUnInstallOrder();
			hardwareUnInstallOrder.setAmmeter(ammeter);
			hardwareUnInstallOrder.setProject(project);
			hardwareUnInstallOrder.setParams(params);
			hardwareUnInstallOrder.setHardwareUnInstallStatus(HardwareUnInstallStatus.DONE);
			hardwareUnInstallOrderDao.save(hardwareUnInstallOrder);
			ammeter.setStatus(RecordStatus.DELETE);
			ammeter.setVersion(version);
			ammeter.setHouse(null);
			ammeterDao.update(ammeter);
		} else if (HardwareType.MENSUO.name().equals(type)) {
			DoorLock doorLock = new DoorLock();
			doorLock.setId(ammeterOrDoorLockId);
			doorLock = doorLockDao.findById(doorLock);
			if (doorLock == null) {
				Log.e("门锁编码没有对应的记录");
				throw new ValidateException("门锁编码没有对应的记录");
				
			}

			HardwareUnInstallOrder hardwareUnInstallOrder = new HardwareUnInstallOrder();
			hardwareUnInstallOrder.setDoorLock(doorLock);
			hardwareUnInstallOrder.setProject(project);
			hardwareUnInstallOrder.setParams(params);
			hardwareUnInstallOrder.setHardwareUnInstallStatus(HardwareUnInstallStatus.DONE);
			hardwareUnInstallOrderDao.save(hardwareUnInstallOrder);
			doorLock.setStatus(RecordStatus.DELETE);
			doorLock.setVersion(version);
			doorLock.setHouse(null);
			doorLockDao.update(doorLock);
		} else {
			Log.e("设备类型参数有误");
			throw new ValidateException("设备类型参数有误");
		}
	}

}
