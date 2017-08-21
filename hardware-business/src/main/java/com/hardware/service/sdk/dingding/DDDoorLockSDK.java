package com.hardware.service.sdk.dingding;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hardware.business.enums.DoorLockOperationType;
import com.hardware.business.enums.PwdStatus;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.*;
import com.hardware.business.model.dingding.*;
import com.hardware.business.service.DoorLockOperationLogService;
import com.hardware.business.service.HouseSyncService;
import com.hardware.business.service.MemberLockPassService;
import com.hardware.service.sdk.IDoorLock;
import com.hardware.service.sdk.dingding.utils.DingDingDoorLockUtil;
import com.hardware.service.sdk.enums.DoorLockSupplier;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 丁盯门锁相关sdk<br>
 *
 * @author sunzhongshuai
 * @since v1.1.2
 */
@Component
public class DDDoorLockSDK implements IDoorLock{


    @Autowired
    private HouseSyncService houseSyncService;
    @Autowired
    private MemberLockPassService memberLockPassService;
    @Autowired
    private DoorLockOperationLogService doorLockOperationLogService;

    public DDDoorLockSDK(HouseSyncService houseSyncService) {
        super();
        this.houseSyncService = houseSyncService;
    }

    public DDDoorLockSDK(DoorLockOperationLogService doorLockOperationLogService) {
        this.doorLockOperationLogService = doorLockOperationLogService;
    }
    public DDDoorLockSDK(MemberLockPassService memberLockPassService) {
        this.memberLockPassService=memberLockPassService;
    }
    public DDDoorLockSDK() {

    }


    @Override
    public Map<String, Object> controlLock(Map<String, Object> params) {
        throw new HardwareSdkException("丁盯门锁不支持一键开锁");
    }

    /**
     * 获取锁的状态
     * @param params 相关参数，如uuid等。
     * @return 拼接后的状态信息
     */
    @Override
    public Map<String, Object> status(Map<String, Object> params) {
        String uuid=ObjectUtils.toString(params.get("sn"), "");
        GetLockInfoResponse response = DingDingDoorLockUtil.getLockInfo(null,null,uuid);
        Map<String,Object> resultData=new HashMap<>();
        if(response.getErrNo()==0){
            resultData.put("wifiOnlineStatus", "on");
            resultData.put("powerLeftValue",response.getPower());
            resultData.put("state",response.getOnoff_line()==1?"success":"fail");
        } else {
            resultData.put("wifiOnlineStatus", "off");
        }
        return resultData;
    }



    /**
     * 清除所有门锁密码
     * @param params  相关参数，如锁的uui等
     * @return 清除后的结果map
     */
    @Override
    public Map<String, Object> clearAllPassword(Map<String, Object> params) {
        String uuid=ObjectUtils.toString(params.get("sn"), "");
        FetchPasswordsResponse passwordsResponse=DingDingDoorLockUtil.fetchPasswords(null,null,uuid);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success","false");
        if(passwordsResponse.getErrNo()==0){
            LinkedHashMap<String,FetchPasswordsResponse.Password> passwords= passwordsResponse.getPasswords();
            Set<String> passwordIds= passwords.keySet();
            passwordIds.remove("999");//“999”固定为管理员密码id，删不掉。
            resultMap.put("success","true");
            for (String passwordId : passwordIds) {
                DeletePasswordResponse passwordResponse= DingDingDoorLockUtil.deletePassword(null,null,uuid,passwordId);
                if(passwordResponse.getErrNo()!=0){
                    resultMap.put("success","false");
                }
            }
        }
        return resultMap;
    }


    /**
     * 获取开门日志
     * @param sn 锁的devId
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public List<JSONObject> openLog(String sn, Date startTime, Date endTime){
        GetLockEventsRequest getLockEventsRequest = new GetLockEventsRequest();
        getLockEventsRequest.setUuid(sn);
        getLockEventsRequest.setStartTime(startTime.getTime()/1000);
        getLockEventsRequest.setEndTime(endTime.getTime()/1000);
        GetLockEventsResponse getLockEventsResponse = DingDingDoorLockUtil.getLockEvents(getLockEventsRequest);
        List<GetLockEventsResponse.LockEventsBean> lockEventsBeans = getLockEventsResponse.getLock_events();
        List<JSONObject> newObjects = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (lockEventsBeans != null && lockEventsBeans.size() > 0) {
            for (GetLockEventsResponse.LockEventsBean lockEventsBean : lockEventsBeans) {
                String mobile = "";
                JSONObject temp = new JSONObject();
                temp.put("opTime", sdf.format(lockEventsBean.getTime()));
                temp.put("user", (mobile));
                temp.put("action", lockEventsBean.getSource() == 2 ? 2 : "");
                temp.put("passIndex", lockEventsBean.getSourceid());
                // 丁盯的开门方式：“2：密码开锁”，目前api上只有这一种开锁方式。
                newObjects.add(temp);
            }
        }
        return newObjects;
    }

    /**
     * 丁盯同步房源
     * 步骤1：先判断房源是分散式还是集中式
     * 1.1如果是分散式，找其parentId作为丁盯接口的homeId，如果没有parentId，
     * 代表这是大门，先判断houseSourceCode是否已经同步过，
     * 如果没有，判断syncode是否有值，如果没有值生成一个，
     * 然后把 houseSourceCode传过去
     * 1.2如果是集中式，直接找其unitId作为homeId，判断取unit表中的syc判断是否已经同步
     * @param houses
     *          房源
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    @Override
    public Map<String, Object> syncHouse(List<House> houses,SupplierProduct supplierProduct) throws ThirdPartyRequestException, DataErrorException {
        for (House house : houses) {
            houseSyncService.syncDingDingHouse(house,supplierProduct);
        }
        return null;
    }

    /**
     * 接口文档支持传homeId和roomId来冻结门锁，但是一般我们只针对门锁操作，所以这里homeId和roomId都传null。
     * @param uuid 门锁devId
     * @param memberLockPass  密码实体
     * @return
     */
    @Override
    public void frozenPassword(String uuid, MemberLockPass memberLockPass,DoorLockOperationLog doorLockOperationLog) throws DataErrorException,ThirdPartyRequestException{
        BaseResponse baseResponse=DingDingDoorLockUtil.frozenPassword(null,null,uuid,memberLockPass.getPassIndex());
        if(baseResponse!=null){
            if(baseResponse.getErrNo()!=0) {
                //如果请求冻结密码返回的不是0，那么密码记录表不作任何操作，只记录日志表
                throw new DataErrorException(JSONObject.toJSONString(baseResponse));
            }else{
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.FROZEN_CALLBACK);
                doorLockOperationLog.setMessage("丁盯申请冻结密码成功，等待丁盯回调");
            }
        }else{
            throw new ThirdPartyRequestException("丁盯申请冻结密码请求失败");
        }

    }

    @Override
    public void unfrozenPassword(String uuid,  MemberLockPass  memberLockPass,DoorLockOperationLog doorLockOperationLog) throws DataErrorException,ThirdPartyRequestException{
        BaseResponse baseResponse=DingDingDoorLockUtil.unfrozenPassword(null,null,uuid,memberLockPass.getPassIndex());
        if(baseResponse!=null){
            if(baseResponse.getErrNo()!=0) {
                throw new DataErrorException(JSONObject.toJSONString(baseResponse));
            }else{
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.UNFROZEN_CALLBACK);
                doorLockOperationLog.setMessage("丁盯申请解冻密码成功，等待丁盯回调");
            }
        }else{
            throw new ThirdPartyRequestException("丁盯解冻密码接口请求失败");
        }
    }

    @Override
    public Map<String,Object> addPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog doorLockOperationLog, MemberLockPass memberLockPass, String pwdType) throws ThirdPartyRequestException,DataErrorException {
        if(!"1".equals(pwdType)){
            throw new DataErrorException("新增密码失败:丁盯门锁只支持普通密码");
        }
        AddPasswordRequest addPasswordRequest=new AddPasswordRequest();
        addPasswordRequest.setUuid(doorLock.getDevId());
        if(start!=null){
            addPasswordRequest.setPermissionBegin(start.getTime()/1000);
        }
        if(end!=null){
            addPasswordRequest.setPermissionEnd(end.getTime()/1000);
        }
        addPasswordRequest.setPassword(pwd);
        addPasswordRequest.setIsDefault(0);//0代表非管理员密码,1代表管理员密码
        AddPasswordResponse response=DingDingDoorLockUtil.addPassword(addPasswordRequest);
        Map<String,Object> result=new HashMap<>();
        if(response==null){
            throw new ThirdPartyRequestException("请求丁盯接口新增密码异常");
        }else{
            if(response.getErrNo()==0){
                result.put("passIndex",response.getId());
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.ADDPASSWORD_CALLBACK);
                doorLockOperationLog.setMessage("请求丁盯接口新增密码接口成功，等待丁盯回调");
            }else{
                throw new DataErrorException("新增密码失败:"+JSONObject.toJSONString(response));
            }
        }
        return result;
    }


    @Override
    public void delPassword(MemberLockPass  memberLockPass, DoorLockOperationLog doorLockOperationLog, String devId) throws DataErrorException, ThirdPartyRequestException {
        DeletePasswordResponse response= DingDingDoorLockUtil.deletePassword(null,null,devId,memberLockPass.getPassIndex()+"");
        if(response==null){
            throw new ThirdPartyRequestException("请求丁盯接口删除密码异常");
        }else{
            if(response.getErrNo()!=0){
                throw new DataErrorException("丁盯申请删除密码失败:"+JSONObject.toJSONString(response));
            }else{
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setMessage("请求丁盯接口删除密码接口成功，等待丁盯回调");
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.DELPASSWORD_CALLBACK);
            }
        }
    }

    @Override
    public void modifyPassword(Date start, Date end, String pwd, DoorLock doorLock, DoorLockOperationLog doorLockOperationLog, MemberLockPass memberLockPass) throws DataErrorException, ThirdPartyRequestException {
        //针对于丁盯密码修改要多加一条校验规则：新密码不能和原密码连续4位以上相同
        //比如原密码：123456，新密码不能为123467,723457,903456等
        String oldPwd=memberLockPass.getPass();
        String [] old={oldPwd.substring(0,4),oldPwd.substring(1,5),oldPwd.substring(2,6)};
        for (String temp : old) {
            if(StringUtils.contains(pwd,temp)){
                throw new DataErrorException("新密码不能和原密码连续四位以上相同");
            }
        }


        UpdatePasswordRequest request=new UpdatePasswordRequest();
        request.setUuid(doorLock.getDevId());
        request.setPasswordId(memberLockPass.getPassIndex()+"");
        if(start!=null){
            request.setPermissionBegin(start.getTime()/1000);
        }
        if(end!=null){
            request.setPermissionEnd(end.getTime()/1000);
        }
        request.setPassword(pwd);
        BaseResponse response=DingDingDoorLockUtil.updatePassword(request);
        if(response==null){
            throw new ThirdPartyRequestException("请求丁盯接口更新密码异常");
        }else{
            if(response.getErrNo()==0){
                memberLockPass.setPwdStatus(PwdStatus.WAIT_CALLBACK);
                memberLockPass.setMaxCallBackTime(DateUtils.addMinutes(new Date(),15));
                doorLockOperationLog.setDoorLockOperationType(DoorLockOperationType.MODIFYPASSWORD_CALLBACK);
                doorLockOperationLog.setMessage("请求丁盯接口更新密码接口成功，等待丁盯回调");
            }else{
                throw new DataErrorException("丁盯更新密码失败:"+JSONObject.toJSONString(response));
            }
        }
    }

    @Override
    public DoorLockSupplier getSupplierCode() {
        return DoorLockSupplier.DINGDING;
    }
}
