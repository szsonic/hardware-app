package com.hardware.business.service.impl;

import com.hardware.business.model.DoorLock;
import com.hardware.business.model.DoorLockOpenLog;
import com.hardware.business.model.MemberLockPass;
import com.hardware.business.service.DoorLockOpenLogService;
import com.hardware.business.service.DoorLockService;
import com.hardware.business.service.MemberLockPassService;
import org.iframework.commons.utils.log.Log;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 云柚门锁业务接口<br>
 * 
 * @author zhongqi
 *
 */
@Service("doorLockOpenLogService")
public class DoorLockOpenLogServiceImpl extends BaseServiceSupport<DoorLockOpenLog, String> implements DoorLockOpenLogService {

	@Resource(name = "doorLockOpenLogDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("doorLockOpenLogDaoImpl") BaseHibernateDaoSupport<DoorLockOpenLog, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}
	@Resource
    DoorLockService doorLockService;

	@Override
	public void saveAPIOpenDoorRecordMessage(Map<String, Object> data, MemberLockPassService memberLockPassService) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> datas = (List<Map<String, Object>>) data.get("data");
		if(datas!=null&&datas.size()>0)
		{
		for (Map<String, Object> map : datas) {
			String device = (String) map.get("device");
			String index =map.get("pwdIndex")+"";
			DoorLockOpenLog doorLockOpenLog=new DoorLockOpenLog();
			if("0".equals(map.get("type").toString()))
			{
				doorLockOpenLog.setType("6");
			}else if("3".equals(map.get("type").toString()))
			{
				doorLockOpenLog.setType("1");
			}else if("6".equals(map.get("type").toString()))
			{
				doorLockOpenLog.setType("2");
			}else if("7".equals(map.get("type").toString()))
			{
				doorLockOpenLog.setType("5");
			}	
			doorLockOpenLog.setCreateTime( new Date(System.currentTimeMillis()));
			doorLockOpenLog.setUpdateTime( new Date(System.currentTimeMillis()));
			doorLockOpenLog.setOpenDoorMobile((String) map.get("operator"));
			doorLockOpenLog.setOpenLockTime(new Date(new Long(map.get("time")+"")*1000)   );
			if ("".equals(device)) {
				// 保存数据到数据库 外键为null
				this.save(doorLockOpenLog);
				Log.i("device为空  置外键为null");
			} else {
				DoorLock doorLock=doorLockService.getDoorLockByDevId(device);
				doorLockOpenLog.setDoorLock(doorLock);
				List<MemberLockPass> list = memberLockPassService.findMemberLockPassByDoorLockDevIdAndPass(doorLock,
						index, map.get("type").toString());
				if (list != null && list.size() == 1) {
					// 添加数据到数据库 记录外键
					MemberLockPass memberLockPass=list.get(0);
					doorLockOpenLog.setMemberLockPass(memberLockPass);
					this.save(doorLockOpenLog);	
				} else if (list != null && list.size() > 1) {
					// 数据库存在脏数据 主键没法记录 错误信息写入log日志
					this.save(doorLockOpenLog);
					Log.i("数据库存在脏数据  设备信息不唯一  置外键为null");
				} else {
					// 数据为空 记录数据 外键为空
					this.save(doorLockOpenLog);
					Log.i("数据为空  置外键为null");
					
				}
			}
		}
	 }
	}

	@Override
	public void saveALMSOpenDoorRecordMessage(Map<String, Object> data, MemberLockPassService memberLockPassService) {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> datas = (List<Map<String, Object>>) data.get("data");
		if(datas!=null&&datas.size()>0)
		{
		for (Map<String, Object> map : datas) {
			String device = (String) map.get("device");
			DoorLockOpenLog doorLockOpenLog=new DoorLockOpenLog();
			//doorLockOpenLog.setType( map.get("type")+"");
			//密码开门
			if("2".equals(map.get("type").toString()))
			{
				doorLockOpenLog.setType("2");
				
			}else //接口开门
				if("3".equals(map.get("type").toString()))
			{
				doorLockOpenLog.setType("1");
			}else // 机械钥匙开门
				if("4".equals(map.get("type").toString()))
			{
				doorLockOpenLog.setType("6");
			}else //其他开门
			{
				doorLockOpenLog.setType("8");
			}	
			doorLockOpenLog.setCreateTime( new Date(System.currentTimeMillis()));
			doorLockOpenLog.setUpdateTime( new Date(System.currentTimeMillis()));
			doorLockOpenLog.setOpenDoorMobile((String) map.get("operator"));
			doorLockOpenLog.setOpenLockTime(new Date(new Long(map.get("time")+"")*1000)   );
			if ("".equals(device)) {
				Log.i("device为空  置外键为null");
			} else {
				DoorLock doorLock=doorLockService.getDoorLockByDevId(device);
				doorLockOpenLog.setDoorLock(doorLock);
			}
			this.save(doorLockOpenLog);
		}
	 }
		
	}


}
