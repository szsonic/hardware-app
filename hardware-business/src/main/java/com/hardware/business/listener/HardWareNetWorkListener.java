package com.hardware.business.listener;

import com.hardware.business.dao.HardwareOnlineLogDao;
import com.hardware.business.enums.NotifyType;
import com.hardware.business.event.HardWareNerworkEvent;
import com.hardware.business.model.HardwareOnlineLog;
import com.hardware.business.service.HardwareOnlineLogService;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 硬件设备
 */
@Component
public class HardWareNetWorkListener implements ApplicationListener<HardWareNerworkEvent> {

	@Resource(name="hardwareOnlineLogDao")
	private HardwareOnlineLogDao hardwareOnlineLogDao;

	@Autowired
	private HardwareOnlineLogService hardwareOnlineLogService;

	@Override
	public void onApplicationEvent(HardWareNerworkEvent hardWareNerworkEvent) {
		Log.i("handel HardWareNerworkEvent is start.");

		HardwareOnlineLog hardwareOnlineLog = hardWareNerworkEvent.getOnlineLog();

		if(ValidatorUtils.isEquals("on", hardwareOnlineLog.getWifiOnlineStatus())){
			//设备在线
			hardwareOnlineLog.setOnlineTime(new Date());

			hardwareOnlineLogDao.updateOnlineStatusWhenReOnline(hardwareOnlineLog);
		}else if(ValidatorUtils.isEquals("off", hardwareOnlineLog.getWifiOnlineStatus())){
			//设备离线
			hardwareOnlineLog.setOfflineTime(new Date());
			hardwareOnlineLog.setNotifyType(NotifyType.EMAIL);

			int offLineCount = 1;  //默认DB里有该设备的离线记录
			if(ValidatorUtils.isNotEmpty(hardwareOnlineLog.getAmmeter())){
				offLineCount
						= hardwareOnlineLogDao.findOfflineCountByAmmeter(hardwareOnlineLog.getAmmeter().getId());

			}else if(ValidatorUtils.isNotEmpty(hardwareOnlineLog.getDoorLock())){
				offLineCount
						= hardwareOnlineLogDao.findOfflineCountByDoorLock(hardwareOnlineLog.getDoorLock().getId());
			}

			//当DB里没有旧的离线记录('离线记录' 是指离线还没恢复的记录), 那么重新记录一条
			if(offLineCount <= 0){
				hardwareOnlineLogService.save(hardwareOnlineLog);
			}
		}

		Log.i("handel HardWareNerworkEvent is end.");
	}
}
