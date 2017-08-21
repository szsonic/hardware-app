package com.hardware.service.sdk.dingding.utils;

import com.hardware.business.model.dingding.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 丁盯门锁操作工具类<br>
 *
 * @author sunzhongshuai
 */
public class DingDingDoorLockUtil extends DingDingUtil{

    /**
     * 更新门锁密码，对应接口文档6.8
     * @param updatePasswordRequest 更新密码参数请求实体
     * @return 丁盯接口通用返回类型,详见 {@link BaseResponse}
     */
    public static BaseResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        String url= BASE_URL +"update_password";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id", updatePasswordRequest.getHomeId());
        params.put("room_id",updatePasswordRequest.getRoomId());
        params.put("uuid",updatePasswordRequest.getUuid());
        params.put("password_id", updatePasswordRequest.getPasswordId());
        params.put("password",updatePasswordRequest.getPassword());
        params.put("is_send_location",updatePasswordRequest.getSendLocation());
        params.put("phonenumber",updatePasswordRequest.getPhoneNo());
        params.put("name",updatePasswordRequest.getName());
        params.put("permission_begin",updatePasswordRequest.getPermissionBegin());
        params.put("permission_end",updatePasswordRequest.getPermissionEnd());
        return restTemplate.postForObject(url,params,BaseResponse.class);
    }

    /**
     * 获取锁的详细信息，对应接口文档6.3
     * @param homeId  公寓ID
     * @param roomId 房间ID
     * @param uuid 锁的uuid
     * @return 锁的详细信息,详见 {@link GetLockInfoResponse}
     */
    public static GetLockInfoResponse getLockInfo(String homeId, String roomId, String uuid) {
        StringBuilder url= new StringBuilder(BASE_URL);
        url.append("get_lock_info?access_token={access_token}");
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(homeId)){
            params.put("home_id", homeId);
            url.append("&home_id={home_id}");
        }
        if(StringUtils.isNotBlank(roomId)){
            params.put("room_id", roomId);
            url.append("&room_id={room_id}");
        }
        if(StringUtils.isNotBlank(uuid)){
            params.put("uuid", uuid);
            url.append("&uuid={uuid}");
        }
        return restTemplate.getForObject(url.toString(),GetLockInfoResponse.class,params);
    }

    /**
     * 获取门锁密码列表，对应接口文档6.4
     * @param homeId  公寓ID
     * @param roomId 房间ID
     * @param uuid 锁的uuid
     * @return 密码信息,详见 {@link FetchPasswordsResponse}
     */
    public static FetchPasswordsResponse fetchPasswords(String homeId, String roomId, String uuid) {
        StringBuilder url= new StringBuilder(BASE_URL);
        url.append("fetch_passwords?access_token={access_token}");
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(homeId)){
            params.put("home_id", homeId);
            url.append("&home_id={home_id}");
        }
        if(StringUtils.isNotBlank(roomId)){
            params.put("room_id", roomId);
            url.append("&room_id={room_id}");
        }
        if(StringUtils.isNotBlank(uuid)){
            params.put("uuid", uuid);
            url.append("&uuid={uuid}");
        }
        return restTemplate.getForObject(url.toString(),FetchPasswordsResponse.class,params);
    }


    /**
     * 获取管理员密码明文，对应接口文档6.5
     * @param homeId  公寓ID
     * @param roomId 房间ID
     * @param uuid 锁的uuid
     * @return 密码信息,详见 {@link GetDefaultPasswordPlaintextResponse}
     */
    public static GetDefaultPasswordPlaintextResponse getDefaultPasswordPlaintext(String homeId, String roomId, String uuid) {
        StringBuilder url= new StringBuilder(BASE_URL);
        url.append("get_default_password_plaintext?access_token={access_token}");
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(homeId)){
            params.put("home_id", homeId);
            url.append("&home_id={home_id}");
        }
        if(StringUtils.isNotBlank(roomId)){
            params.put("room_id", roomId);
            url.append("&room_id={room_id}");
        }
        if(StringUtils.isNotBlank(uuid)){
            params.put("uuid", uuid);
            url.append("&uuid={uuid}");
        }
        return restTemplate.getForObject(url.toString(),GetDefaultPasswordPlaintextResponse.class,params);
    }

    /**
     * 获取动态密码明文，对应接口文档6.6
     * @param homeId  公寓ID
     * @param roomId 房间ID
     * @param uuid 锁的uuid
     * @return
     */
    public static GetDynamicPasswordPlaintextResponse getDynamicPasswordPlaintext(String homeId, String roomId, String uuid) {
        StringBuilder url= new StringBuilder(BASE_URL);
        url.append("get_dynamic_password_plaintext?access_token={access_token}");
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(homeId)){
            params.put("home_id", homeId);
            url.append("&home_id={home_id}");
        }
        if(StringUtils.isNotBlank(roomId)){
            params.put("room_id", roomId);
            url.append("&room_id={room_id}");
        }
        if(StringUtils.isNotBlank(uuid)){
            params.put("uuid", uuid);
            url.append("&uuid={uuid}");
        }
        return restTemplate.getForObject(url.toString(),GetDynamicPasswordPlaintextResponse.class,params);
    }

    /**
     * 新增密码，新设备安装完毕后必须调用该接口设置管理员密码，对应接口文档6.7
     * @param addPasswordRequest 新增密码参数实体
     * @return 新增密码返回实体
     */
    public static AddPasswordResponse addPassword(AddPasswordRequest addPasswordRequest) {
        String url= BASE_URL +"add_password";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id", addPasswordRequest.getHomeId());
        params.put("room_id",addPasswordRequest.getRoomId());
        params.put("uuid",addPasswordRequest.getUuid());
        params.put("phonenumber",addPasswordRequest.getPhoneNo());
        params.put("is_default",addPasswordRequest.getIsDefault());
        params.put("is_send_location",addPasswordRequest.getSendLocation());
        params.put("password",addPasswordRequest.getPassword());
        params.put("name",addPasswordRequest.getName());
        params.put("permission_begin",addPasswordRequest.getPermissionBegin());
        params.put("permission_end",addPasswordRequest.getPermissionEnd());
        return restTemplate.postForObject(url,params,AddPasswordResponse.class);
    }

    /**
     * 删除密码，对应接口文档6.9
     * @param homeId  公寓ID
     * @param roomId 房间ID
     * @param uuid 锁的uuid
     * @param passwordId 密码id
     * @return 删除密码返回实体
     */
    public static DeletePasswordResponse deletePassword(String homeId, String roomId, String uuid, String passwordId) {
        String url= BASE_URL +"delete_password";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id", homeId);
        params.put("room_id",roomId);
        params.put("uuid",uuid);
        params.put("password_id",passwordId);
        return restTemplate.postForObject(url,params,DeletePasswordResponse.class);
    }

    /**
     * 冻结密码，对应接口文档6.10
     * @param homeId  公寓ID
     * @param roomId 房间ID
     * @param uuid 锁的uuid
     * @param passwordId 密码id
     * @return 丁盯接口通用返回类型,详见 {@link BaseResponse}
     */
    public static BaseResponse frozenPassword(String homeId, String roomId, String uuid, Integer passwordId) {
        String url= BASE_URL +"frozen_password";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id", homeId);
        params.put("room_id",roomId);
        params.put("uuid",uuid);
        params.put("password_id",passwordId);
        return restTemplate.postForObject(url,params,BaseResponse.class);
    }

    /**
     * 解冻结密码，对应接口文档6.11
     * @param homeId  公寓ID
     * @param roomId 房间ID
     * @param uuid 锁的uuid
     * @param passwordId 密码id
     * @return 丁盯接口通用返回类型,详见 {@link BaseResponse}
     */
    public static BaseResponse unfrozenPassword(String homeId, String roomId, String uuid, Integer passwordId) {
        String url= BASE_URL +"unfrozen_password";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        params.put("home_id", homeId);
        params.put("room_id",roomId);
        params.put("uuid",uuid);
        params.put("password_id",passwordId);
        return restTemplate.postForObject(url,params,BaseResponse.class);
    }

    /**
     * 查询开门历史记录，对应接口文档6.12
     * @param getLockEventsRequest 查询相关参数
     * @return 开门历史记录返回实体
     */
    public static GetLockEventsResponse getLockEvents(GetLockEventsRequest getLockEventsRequest) {
        StringBuilder url= new StringBuilder(BASE_URL);
        url.append("get_lock_events?access_token={access_token}");
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(getLockEventsRequest.getHomeId())){
            params.put("home_id", getLockEventsRequest.getHomeId());
            url.append("&home_id={home_id}");
        }
        if(StringUtils.isNotBlank(getLockEventsRequest.getRoomId())){
            params.put("room_id", getLockEventsRequest.getRoomId());
            url.append("&room_id={room_id}");
        }
        if(StringUtils.isNotBlank(getLockEventsRequest.getUuid())){
            params.put("uuid", getLockEventsRequest.getUuid());
            url.append("&uuid={uuid}");
        }
        if(getLockEventsRequest.getOffset()!=null){
            params.put("offset", getLockEventsRequest.getOffset());
            url.append("&offset={offset}");
        }
        if(getLockEventsRequest.getCount()!=null){
            params.put("count", getLockEventsRequest.getCount());
            url.append("&count={count}");
        }
        if(getLockEventsRequest.getStartTime()!=null){
            params.put("start_time", getLockEventsRequest.getStartTime());
            url.append("&start_time={start_time}");
        }
        if(getLockEventsRequest.getEndTime()!=null){
            params.put("end_time", getLockEventsRequest.getEndTime());
            url.append("&end_time={end_time}");
        }
        System.out.println(url);
        System.out.println(getAccessToken());
        System.out.println(getLockEventsRequest.getStartTime());
        System.out.println( getLockEventsRequest.getEndTime());
 
      //  return null;
        return restTemplate.getForObject(url.toString(),GetLockEventsResponse.class,params);
    }


    /**
     * 下发激活码，对应接口文档6.14
     * @param addPasswordWithoutCenterRequest 请求参数实体
     * @return 下发激活码返回实体
     */
    public static AddPasswordWithoutCenterResponse addPasswordWithoutCenter(AddPasswordWithoutCenterRequest addPasswordWithoutCenterRequest) {
        String url= BASE_URL+"add_password_without_center";
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(addPasswordWithoutCenterRequest.getHomeId())){
            params.put("home_id", addPasswordWithoutCenterRequest.getHomeId());
        }
        if(StringUtils.isNotBlank(addPasswordWithoutCenterRequest.getRoomId())){
            params.put("room_id", addPasswordWithoutCenterRequest.getRoomId());
        }
        if(StringUtils.isNotBlank(addPasswordWithoutCenterRequest.getUuid())){
            params.put("uuid", addPasswordWithoutCenterRequest.getUuid());
        }
        if(StringUtils.isNotBlank(addPasswordWithoutCenterRequest.getPhoneNo())){
            params.put("phonenumber", addPasswordWithoutCenterRequest.getPhoneNo());
        }
        if(addPasswordWithoutCenterRequest.getCMD()!=null){
            params.put("CMD", addPasswordWithoutCenterRequest.getCMD());
        }
        if(StringUtils.isNotBlank(addPasswordWithoutCenterRequest.getName())){
            params.put("name", addPasswordWithoutCenterRequest.getName());
        }
        if(addPasswordWithoutCenterRequest.getSendMsg()){
            params.put("is_send_msg", addPasswordWithoutCenterRequest.getSendMsg());
        }
        if(addPasswordWithoutCenterRequest.getSendLocation()!=null){
            params.put("is_send_location", addPasswordWithoutCenterRequest.getSendLocation());
        }
        if(addPasswordWithoutCenterRequest.getPermissionEnd()!=null){
            params.put("permission_end", addPasswordWithoutCenterRequest.getPermissionEnd().getTime()/1000);
        }
        return restTemplate.postForObject(url,params,AddPasswordWithoutCenterResponse.class);
    }

}
