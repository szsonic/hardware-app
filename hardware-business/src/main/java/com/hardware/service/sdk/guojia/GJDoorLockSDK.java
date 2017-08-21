package com.hardware.service.sdk.guojia;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hardware.business.enums.DoorLockOperationType;
import com.hardware.business.enums.PwdStatus;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.*;
import com.hardware.business.model.guojia.*;
import com.hardware.business.service.DoorLockOperationLogService;
import com.hardware.business.service.HouseSyncService;
import com.hardware.business.service.MemberLockPassService;
import com.hardware.service.sdk.IDoorLock;
import com.hardware.service.sdk.enums.DoorLockSupplier;
import com.hardware.service.sdk.guojia.utils.GuoJiaDoorLockUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Component
public class GJDoorLockSDK implements IDoorLock {

    public   static final String SUCCESS="HH0000";
    @Autowired
    private HouseSyncService houseSyncService;
    @Autowired
    private MemberLockPassService memberLockPassService;
    @Autowired
    private DoorLockOperationLogService doorLockOperationLogService;

    public GJDoorLockSDK(HouseSyncService houseSyncService) {
        super();
        this.houseSyncService = houseSyncService;
    }

    public GJDoorLockSDK(DoorLockOperationLogService doorLockOperationLogService) {
        this.doorLockOperationLogService = doorLockOperationLogService;
    }
    public GJDoorLockSDK(MemberLockPassService memberLockPassService) {
        this.memberLockPassService=memberLockPassService;
    }
    public GJDoorLockSDK() {

    }

    /**
     * 一键开锁
     * @param params
     *          一键开锁需要的参数
     * @return
     */
    @Override
    public Map<String, Object> controlLock(Map<String, Object> params) throws ThirdPartyRequestException {
        String sn=params.get("sn").toString();
        String mobile="";//暂时先写死一个
        RemoteOpenResponse remoteOpenResponse = GuoJiaDoorLockUtil.remoteOpen(sn,mobile);
        Map<String,Object> result = new HashMap<>();
        if(remoteOpenResponse!=null&&SUCCESS.equals(remoteOpenResponse.getRtlCode())){
            result.put("success","true");
        }else{
            throw new ThirdPartyRequestException("调用果加一键开锁失败："+ JSONObject.toJSONString(remoteOpenResponse));
        }
        return result;
    }

    @Override
    public Map<String, Object> status(Map<String, Object> params) throws ThirdPartyRequestException {
        String devId=params.get("sn").toString();
        Map<String,Object> resultData=new HashMap<>();
        GetLockInfoResponse response= GuoJiaDoorLockUtil.getLockInfo(devId);
        if("00".equals(response.getData().getComu_status())){
            resultData.put("wifiOnlineStatus", "on");
            resultData.put("powerLeftValue",response.getData().getPower());
            resultData.put("state","success");
        }else{
            resultData.put("wifiOnlineStatus", "off");
        }
        return resultData;
    }

    @Override
    public Map<String, Object> clearAllPassword(Map<String, Object> params) throws ThirdPartyRequestException {
        String sn=params.get("sn").toString();
        Map<String,Object> result=new HashMap<>();
        result.put("success","true");
        PwdListResponse response= GuoJiaDoorLockUtil.getPwdList(sn);
        List<PwdListResponse.DataBean> pwdList = response.getData();
        for (PwdListResponse.DataBean pwd : pwdList) {
            DelPwdResponse delPwdResponse= GuoJiaDoorLockUtil.clearPwd(sn,pwd.getPwd_no());
            if(delPwdResponse==null||!SUCCESS.equals(delPwdResponse.getRtlCode())){
                result.put("success","false");
            }
        }
        return result;
    }




    /**
     * 果加开门记录
     * @param sn 设备devId
     * @param startTime 开门记录开始时间
     * @param endTime 开门记录结束时间
     */
    @Override
    public List<JSONObject> openLog(String sn, Date startTime, Date endTime) throws ThirdPartyRequestException {
        OpenLogResponse openLogResponse = GuoJiaDoorLockUtil.getOpenLog(sn,startTime,endTime);
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        if(openLogResponse!=null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<OpenLogResponse.DataBean.OpenLog> openLogs=openLogResponse.getData().getRows();
            for (OpenLogResponse.DataBean.OpenLog openLog : openLogs) {
                JSONObject temp = new JSONObject();
                temp.put("opTime", sdf.format(openLog.getOp_time()));
                temp.put("user", "");
                String action;
                //果加开门方式定义 0:蓝牙APP开门 1：自定义密码开门 2：一次性密码开门 3：远程开门 4：时效密码开门 5：门禁卡开门 6：物理钥匙开门
                //硬件前端渲染定义 1：手机开门 2：密码开门 3：第三方平台开门 4：一次性密码 5：动态密码 6：机械钥匙开门 7：刷卡开门 8：其他
                switch (openLog.getOp_way()){
                    case "0":
                        action="1";
                        break;
                    case "1":
                        action="2";
                        break;
                    case "2":
                        action="4";
                        break;
                    case "3":
                        action="3";
                        break;
                    case "4":
                        action="2";
                        break;
                    case "5":
                        action="7";
                        break;
                    case "6":
                        action="6";
                        break;
                    default:
                        action="8";
                }
                temp.put("action", action);
                temp.put("passIndex", openLog.getPwd_no());
                jsonObjectList.add(temp);
            }
        }
        return jsonObjectList;
    }

    @Override
    public Map<String, Object> syncHouse(List<House> houses, SupplierProduct supplierProduct) throws Exception {
        for (House house : houses) {
            houseSyncService.syncGuoJiaHouse(house);
        }
        return null;
    }

    @Override
    public void frozenPassword(String uuid, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws DataErrorException, ThirdPartyRequestException {
        throw new HardwareSdkException("果加门锁暂不支持密码冻结");
    }

    @Override
    public void unfrozenPassword(String uuid, MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog) throws DataErrorException, ThirdPartyRequestException {
        throw new HardwareSdkException("果加门锁暂不支持密码解冻");
    }

    @Override
    public Map<String, Object> addPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog doorLockOperationLog, MemberLockPass memberLockPass, String pwdType) throws Exception {
        if(!"1".equals(pwdType)){
           //代表前端传的是一次性密码，果加不支持一次性密码
            throw new HardwareSdkException("果加不支持一次性密码");
        }
        AddPwdResponse response= GuoJiaDoorLockUtil.addPassword(doorLock.getDevId(),pwd,start,end,"13641688076");
        Map<String,Object> result=new HashMap<>();
        if(response==null){
            throw new ThirdPartyRequestException("请求果加接口新增密码异常");
        }else{
            if(SUCCESS.equals(response.getRtlCode())){
                result.put("passIndex",response.getData().getPwd_no());
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.ADDPASSWORD_CALLBACK);
                doorLockOperationLog.setMessage("请求果加接口新增密码接口成功，等待果加回调");
            }else{
                throw new DataErrorException("新增密码失败:"+JSONObject.toJSONString(response));
            }
        }
        return result;
    }

    @Override
    public void delPassword(MemberLockPass memberLockPass, DoorLockOperationLog doorLockOperationLog, String devId) throws DataErrorException, ThirdPartyRequestException {
        DelPwdResponse response= GuoJiaDoorLockUtil.delPwd(devId,memberLockPass.getPassIndex()+"");
        if(response==null){
            throw new ThirdPartyRequestException("请求果加接口删除密码异常");
        }else{
            if(!SUCCESS.equals(response.getRtlCode())){
                throw new DataErrorException("果加申请删除密码失败:"+JSONObject.toJSONString(response));
            }else{
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setMessage("请求果加接口删除密码接口成功，等待果加回调");
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.DELPASSWORD_CALLBACK);
            }
        }
    }

    @Override
    public void modifyPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog doorLockOperationLog, MemberLockPass memberLockPass) throws DataErrorException, ThirdPartyRequestException {
        ModifyPwdResponse response = GuoJiaDoorLockUtil.modifyPwdResponse(doorLock.getDevId(),memberLockPass.getPassIndex()+"",pwd,start,end);
        if(response==null){
            throw new ThirdPartyRequestException("请求果加接口更新密码异常");
        }else{
            if(SUCCESS.equals(response.getRtlCode())){
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.MODIFYPASSWORD_CALLBACK);
                doorLockOperationLog.setMessage("请求果加接口更新密码接口成功，等待丁盯回调");
            }else{
                throw new DataErrorException("果加更新密码失败:"+JSONObject.toJSONString(response));
            }
        }
    }

    @Override
    public DoorLockSupplier getSupplierCode() {
        return DoorLockSupplier.GUOJIA;
    }
}
