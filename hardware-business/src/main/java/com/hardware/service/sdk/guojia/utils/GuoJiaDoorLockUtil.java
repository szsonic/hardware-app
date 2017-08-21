package com.hardware.service.sdk.guojia.utils;

import com.google.common.collect.Lists;
import com.hardware.business.conf.GuoJiaConf;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.House;
import com.hardware.business.model.guojia.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.*;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
public class GuoJiaDoorLockUtil extends GuoJiaUtil{

    /**
     * 一键开门
     * @param sn 锁的设备编码sn
     * @param mobile 开门人手机号码
     * @return 一键开门返回格式
     */
    public static RemoteOpenResponse remoteOpen(String sn, String mobile) throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        params.put("pwd_user_mobile",mobile);
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/lock/remote_open",getHttpEntity(params),RemoteOpenResponse.class);
    }


    /**
     * 获取门锁详细信息（用于获取门锁在线状态等）
     * @param sn 设备sn
     * @return
     * @throws ThirdPartyRequestException
     */
    public static GetLockInfoResponse getLockInfo(String sn) throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/lock/view",getHttpEntity(params),GetLockInfoResponse.class);
    }



    /**
     * 新增门锁密码
     * @param sn 设备sn
     * @param pwd 密码明文
     * @param startTime 密码有效期开始时间
     * @param endTime 密码有效期结束时间
     * @param mobile 手机号码
     * @return
     * @throws ThirdPartyRequestException
     */
    public static AddPwdResponse addPassword(String sn, String pwd, Date startTime,Date endTime,String mobile) throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        DESEncrypt desPlus2 = new DESEncrypt(GuoJiaConf.KEY);
        params.put("pwd_text",desPlus2.encrypt(pwd));
        params.put("similarity_check",false);//是否校验密码是否相似，这里统一不校验
        params.put("valid_time_start",startTime.getTime());
        params.put("valid_time_end",endTime.getTime());
        params.put("pwd_user_mobile",mobile);
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/pwd/add",getHttpEntity(params),AddPwdResponse.class);
    }


    /**
     * 获取密码列表（用于删除密码）
     * @param sn 设备sn
     *
     */
    public static PwdListResponse getPwdList(String sn)throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/pwd/list",getHttpEntity(params),PwdListResponse.class);
    }


    /**
     * 获取开门日志
     * @param sn
     * @param startTime
     * @param endTime
     * @return
     * @throws ThirdPartyRequestException
     */
    public static OpenLogResponse getOpenLog(String sn, Date startTime, Date endTime)throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        params.put("search_time_start",startTime.getTime());
        params.put("search_time_end",endTime.getTime());
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/lock/open_lock_his",getHttpEntity(params),OpenLogResponse.class);
    }

    /**
     * 删除密码
     * @param sn
     * @param pwdNo
     * @return
     * @throws ThirdPartyRequestException
     */
    public static DelPwdResponse delPwd(String sn, String pwdNo) throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        params.put("pwd_no",pwdNo);
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/pwd/delete",getHttpEntity(params),DelPwdResponse.class);
    }

    /**
     * 修改密码
     * @param sn
     * @param pwdNo
     * @param pwd
     * @param startTime
     * @param endTime
     * @return
     * @throws ThirdPartyRequestException
     */
    public static ModifyPwdResponse modifyPwdResponse(String sn,String pwdNo, String pwd, Date startTime,Date endTime) throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        params.put("pwd_no",pwdNo);
        DESEncrypt desPlus2 = new DESEncrypt(GuoJiaConf.KEY);
        params.put("pwd_text",desPlus2.encrypt(pwd));
        params.put("similarity_check",false);//是否校验密码是否相似，这里统一不校验
        params.put("valid_time_start",startTime.getTime());
        params.put("valid_time_end",endTime.getTime());
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/pwd/update",getHttpEntity(params),ModifyPwdResponse.class);
    }


    public static DelPwdResponse clearPwd(String sn, Integer pwdNo) throws ThirdPartyRequestException {
        Map<String,Object> params=new HashMap<>();
        params.put("lock_no",sn);
        params.put("pwd_no",pwdNo);
        return restTemplate.postForObject(GuoJiaConf.BASE_URL+"/pwd/delete",getHttpEntity(params),DelPwdResponse.class);


    }

    public static String addOrder(House house,String parentHouseCode)throws ThirdPartyRequestException {

        Map<String,Object> params=new HashMap<>();
        params.put("rcv_order_no",UUID.randomUUID().toString().replace("-",""));
        params.put("house_code",parentHouseCode);
        params.put("inst_name","李善杰");
        params.put("inst_phone","13801852039");
        params.put("inst_address",house.getProvinceName()+house.getCityName()+house.getDistName()+
                house.getDistrictName()+house.getAddress()+house.getBlock()+house.getUnit()+house.getRoomNo());
        //params.put("inst_address","上海上海市黄浦区外滩SOHO G座");
        params.put("inst_time",DateUtils.addDays(new Date(),7).getTime());


        Map<String,Object> subParams=new HashMap<>();
        subParams.put("goods_id","5");//产品编码都写5，由果加的人来改
        subParams.put("goods_num",1);//数量是1个
        subParams.put("room_code",StringUtils.isBlank(house.getParentId())?"":house.getOnLineSynCode());
        List<Map<String,Object>> goodsList=Lists.newArrayList();
        goodsList.add(subParams);
        params.put("goods_list",goodsList);

        restTemplate.postForObject(GuoJiaConf.BASE_URL+"/order/add",getHttpEntity(params),String.class);
        return null;
    }

//    public static void main(String[] args) {
//        try {
//            //蓝牙锁设备ID：20.1.14.76
//            //wifi小门锁设备ID：10.6.40.180
//           // PwdListResponse s=getPwdList("10.6.40.180");//获取门锁信息
//                //AddPwdResponse s=addPassword("10.6.40.180","223344",new Date(), DateUtils.addDays(new Date(),2),"18605167728");//新增密码
//            //String s= getPwdList("20.1.14.76");//获取密码列表
//            //OpenLogResponse s= getOpenLog("10.6.40.180",DateUtils.addDays(new Date(),-20),new Date());
//            //DelPwdResponse s=delPwd("10.6.40.180","3");//删除密码
//            //ModifyPwdResponse s=modifyPwdResponse("10.6.40.180","3","445566",new Date(), DateUtils.addDays(new Date(),2));//修改密码
//            //clearPwd("10.6.40.180");
//            House house=new House();
//            house.setSourceHouseCode("33333");
//            addOrder(house,"222222");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
