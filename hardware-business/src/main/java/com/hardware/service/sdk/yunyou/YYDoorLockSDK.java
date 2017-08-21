package com.hardware.service.sdk.yunyou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hardware.business.conf.YunYouConf;
import com.hardware.business.enums.DoorLockOperationType;
import com.hardware.business.enums.FrozenStatus;
import com.hardware.business.enums.PwdStatus;
import com.hardware.business.enums.YunYouPwdModifyType;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.exception.ValidateException;
import com.hardware.business.model.*;
import com.hardware.business.service.*;
import com.hardware.business.utils.SpringContextUtil;
import com.hardware.service.sdk.IDoorLock;
import com.hardware.service.sdk.enums.DoorLockSupplier;
import com.hardware.service.sdk.yunyou.utils.YunYouUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.http.HttpClientUtils;
import org.iframework.support.domain.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 门锁SDK实现类
 * 
 * @author zhongqi
 *
 */
@Component
public class YYDoorLockSDK implements IDoorLock {
	@Autowired
	private HouseSyncService houseSyncService;
	@Autowired
	private MemberLockPassService memberLockPassService;

	public YYDoorLockSDK(HouseSyncService houseSyncService) {
		this.houseSyncService = houseSyncService;
	}
	private DoorLockOperationLogService doorLockOperationLogService;

	public YYDoorLockSDK(DoorLockOperationLogService doorLockOperationLogService) {
		this.doorLockOperationLogService = doorLockOperationLogService;
	}
	public YYDoorLockSDK(MemberLockPassService memberLockPassService) {
		this.memberLockPassService=memberLockPassService;
	}
	public YYDoorLockSDK() {

	}

	@Override
	public Map<String, Object> controlLock(Map<String, Object> params) {
		String sn = ObjectUtils.toString(params.get("sn"), "");
		// 在开锁之前检查锁是否已经被打开了，后面得加上

		String action = ObjectUtils.toString(params.get("action"), "");

		StringBuilder url = new StringBuilder(YunYouConf.HOST);
		url.append("/v1/locks").append("?");
		url.append("key=").append(YunYouConf.KEY).append("&");
		url.append("sn=").append(sn).append("&");
		url.append("action=").append(action);
		Map<String, Object> requestParams = new HashMap<>();
		requestParams.put("key", YunYouConf.KEY);
		requestParams.put("sn", sn);
		requestParams.put("action", action);
		String data = HttpClientUtils.post(url.toString(), requestParams);
		Map<String, Object> resultData = new HashMap<>();
		if (StringUtils.isNotBlank(data)) {
			JSONObject result = JSON.parseObject(data, JSONObject.class);
			resultData.put("msg", result.get("desc"));
			if (result.getBooleanValue("success")) {
				resultData.put("success", "true");
			} else {
				resultData.put("success", "false");
			}
		}
		return resultData;

	}

	@Override
	public Map<String, Object> syncHouse(List<House> houses, SupplierProduct supplierProduct) throws Exception {
		for (House house : houses) {
			houseSyncService.syncYunYouHouse(house,supplierProduct);
		}
		return null;
	}

	@Override
	public Map<String, Object> status(Map<String, Object> params) {
		String sn = ObjectUtils.toString(params.get("sn"));
		Map<String, Object> result = YunYouUtil.getState(sn);
		return result;
	}

//	@Override
//	public Map<String, Object> sitePass(Map<String, Object> params) throws DataErrorException,ThirdPartyRequestException{
//		String sn = ObjectUtils.toString(params.get("devId"));
//		String startTime = ObjectUtils.toString(params.get("startTime"));
//		String endTime = ObjectUtils.toString(params.get("endTime"));
//		String type = ObjectUtils.toString(params.get("type"));
//		String passIndex = ObjectUtils.toString(params.get("index"));
//		String password = ObjectUtils.toString(params.get("password"));
//		String action = ObjectUtils.toString(params.get("action"), "0");
//		String times = ObjectUtils.toString(params.get("times"));
//		Map<String, Object> data = YunYouUtil.sitePass(sn, startTime, endTime, type, Integer.parseInt(passIndex), password, action, times);
//		return data;
//	}



	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> openLog(String sn, Date startTime, Date endTime) {
		DoorLockService doorLockService = (DoorLockService) SpringContextUtil.getBean("doorLockService");
		DoorLock doorLock = doorLockService.getDoorLockByDevId(sn);
		if(doorLock == null){
			return null;
		}
		
		DoorLockOpenLogService doorLockOperationLogService = (DoorLockOpenLogService) SpringContextUtil.getBean("doorLockOpenLogService");
		DoorLockOpenLog doorLockOpenLog = new DoorLockOpenLog();
		doorLockOpenLog.setDoorLock(doorLock);
		doorLockOpenLog.setDateStart(startTime);
		doorLockOpenLog.setDateEnd(endTime);
		List<DoorLockOpenLog> list = doorLockOperationLogService.findByModel(doorLockOpenLog, null, null);
		List<JSONObject> jsonObjectList= Lists.newArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (DoorLockOpenLog openLog : list) {
			JSONObject temp = new JSONObject();
			temp.put("opTime", sdf.format(openLog.getOpenLockTime()));
			temp.put("user", "");
			temp.put("action",openLog.getType());
			if(openLog.getMemberLockPass()!=null)
			{
				temp.put("passIndex",openLog.getMemberLockPass().getPassIndex());
			}else
			{
				temp.put("passIndex","");
			}
			jsonObjectList.add(temp);
		}
		return jsonObjectList;
	}

	@Override
	public Map<String, Object> clearAllPassword(Map<String, Object> params) {
		String sn = ObjectUtils.toString(params.get("sn"));
		Map<String, Object> result = YunYouUtil.clearAllPassword(sn);
		return result;
	}

	public static void main(String[] args) {

//	     List<Integer> integers=new ArrayList<>();
//        integers.add(1);
//        integers.add(2);
//        integers.add(6);
//        System.out.println(getPassIndex(integers,1));
    }

	@Override
	public void frozenPassword(String uuid, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws DataErrorException,ThirdPartyRequestException {
		if(YunYouUtil.modifyPasswordProperty(uuid,memberLockPass.getPassIndex(), YunYouPwdModifyType.FROZEN.getValue())){
			memberLockPass.setFrozenStatus(FrozenStatus.FROZEN);//密码状态无需改变，依然是SUCCESS
			doorLockOperationLog.setMessage("云柚冻结密码成功");
			doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.FROZEN);
		}
	}

	@Override
	public void unfrozenPassword(String uuid, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws DataErrorException,ThirdPartyRequestException {
		if(YunYouUtil.modifyPasswordProperty(uuid,memberLockPass.getPassIndex(), YunYouPwdModifyType.UNFROZEN.getValue())) {
			memberLockPass.setFrozenStatus(FrozenStatus.UNFROZEN);//密码状态无需改变，依然是SUCCESS
			doorLockOperationLog.setMessage("云柚解冻密码成功");
			doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.UNFROZEN);
		}
	}

	@Override
	public Map<String,Object> addPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog log, MemberLockPass memberLockPass, String pwdType) throws Exception {

		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String startTime=simpleDateFormat.format(start);
		String endTime=simpleDateFormat.format(end);
		String times=null;
		int index=1;
		//获取当前门锁密码的有效序号位
		if("2".equals(pwdType)){
			index=46;
			//入如果是次数密码序号从46开始
			times="1";
		}
		MemberLockPass searchPwd = new MemberLockPass();
		searchPwd.setDoorLock(doorLock);
		List<MemberLockPass> memberLockPassList = memberLockPassService.findPwdMaybeEffectiveByDoorLock(doorLock, pwdType);
		if (memberLockPassList.size() > 0) {
			List<Integer> indexList = new ArrayList<>();
			for (MemberLockPass lockPass : memberLockPassList) {
				indexList.add(lockPass.getPassIndex());
			}
			index = getPassIndex(indexList, index);
		}
        if(index>50){
			if("2".equals(pwdType)){
				throw new ValidateException("次数密码位数已满");
			}else{
				throw new ValidateException("普通密码位数已满");
			}

        }

        Map<String,Object> result= YunYouUtil.sitePass(doorLock.getDevId(),startTime,endTime,pwdType,index,pwd,"0",times);
		if(result.get("passIndex")!=null){
			memberLockPass.setPwdStatus(PwdStatus.CALLBACK_SUCCESS);
			log.setDoorLockOperationType(DoorLockOperationType.ADDPASSWORD);
			log.setMessage("云柚新增密码成功");
		}
		return result;
	}


	@Override
	public void delPassword(MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog, String devId) throws DataErrorException, ThirdPartyRequestException {
		if(YunYouUtil.delPassword(devId,memberLockPass.getPassIndex())){
			memberLockPass.setStatus(RecordStatus.DELETE);//密码状态变为删除
			doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.DELPASSWORD);
			doorLockOperationLog.setMessage("云柚删除密码成功");
		}
	}


	@Override
	public void modifyPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog doorLockOperationLog, MemberLockPass memberLockPass) throws DataErrorException, ThirdPartyRequestException {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String startTime=simpleDateFormat.format(start);
		String endTime=simpleDateFormat.format(end);
		Map<String,Object> result= YunYouUtil.sitePass(doorLock.getDevId(),startTime,endTime,"1",memberLockPass.getPassIndex(),pwd,"0",null);
		if(result.get("passIndex")!=null){
			memberLockPass.setPwdStatus(PwdStatus.CALLBACK_SUCCESS);
			doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.MODIFYPASSWORD);
			doorLockOperationLog.setMessage("云柚修改密码成功");
		}
	}

	/**
     * 由于云柚一把门锁上只有50个密码位，这时候需要寻找有效的密码位
     * 比如1,2,5,10被占用，那么本次新增密码需要的是密码位是3。
     * @param indexList
     * @param index
     * @return
     */
	private  Integer getPassIndex(List<Integer> indexList,Integer index){

	    if(indexList.contains(index)){
	        index++;
            index=getPassIndex(indexList,index);
        }
        return index;
    }

	@Override
	public DoorLockSupplier getSupplierCode() {
		return DoorLockSupplier.YUNYOU;
	}
}
