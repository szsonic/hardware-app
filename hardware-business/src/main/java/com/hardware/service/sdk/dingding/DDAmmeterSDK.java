package com.hardware.service.sdk.dingding;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.domain.DeviceRechargeRecordResponse;
import com.hardware.business.exception.DataErrorException;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.House;
import com.hardware.business.model.SupplierProduct;
import com.hardware.business.model.dingding.BaseResponse;
import com.hardware.business.model.dingding.EleMeterFetchPowerConsumptionResponse;
import com.hardware.business.model.dingding.EleMeterGetPriceAndWayResponse;
import com.hardware.business.model.dingding.GetEleMeterInfoResponse;
import com.hardware.business.service.HouseService;
import com.hardware.business.service.HouseSyncService;
import com.hardware.service.sdk.IAmmeter;
import com.hardware.service.sdk.dingding.utils.DingDingEleMeterUtils;
import com.hardware.service.sdk.enums.AmmeterSupplier;
import org.apache.commons.lang3.ObjectUtils;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Component
public class DDAmmeterSDK implements IAmmeter{

    @Autowired
    private HouseSyncService houseSyncService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private MemcachedManager memcachedManager;


    @Override
    public Integer electricity(Map<String, Object> params) {
        return null;
    }

    @Override
    public Integer surplusElectricity(Map<String, Object> params) {

        return null;
    }

    @Override
    public AmmeterResult setPayMod(Map<String, Object> params) {
        return null;
    }

    @Override
    public AmmeterResult pay(Map<String, Object> params) {
        return null;
    }

    /**
     * 控制电表的通电、断电
     * @param params 电表控制需要的参数 uuid：电表uuid，action：动作：on，合闸；off，跳闸
     *
     * @return
     */
    @Override
    public AmmeterResult control(Map<String, Object> params) {
        String uuid = ObjectUtils.toString(params.get("uuid"), "");
        String action=ObjectUtils.toString(params.get("action"), "");
        BaseResponse response=null;
        if("off".equals(action)){
             response= DingDingEleMeterUtils.eleMeterSwitchOff(uuid);
        }else if("on".equals(action)){
             response= DingDingEleMeterUtils.eleMeterSwitchOn(uuid);
        }
        AmmeterResult ammeterResult=null;
        if(response!=null&&response.getErrNo()==0){
            ammeterResult=new AmmeterResult();
            ammeterResult.setCode("0");
        }
        return ammeterResult;
    }

    /**
     * 获取电表状态
     * 其中trans_status表示丁盯电表通信状态，1：正常，2：失败，-1：未知(一般不会出现此状态，出现此状态一般是对方接口出了问题)
     * 其中，网关到采集器如果不通，则采集器下的所有电表都会显示“2”。如果网关到采集器是通的，采集器到电表是不通的，
     * 则该trans_status也会显示2。
     * enable_state表示是否合闸，-1：未知，1：通电（合闸），2：断电（跳闸）
     * “td_status”和“line_status”用于之前的蜂电电表逻辑判断，0或1代表异常。
     * 原逻辑：通信状态：line_status：0：在线，1：离线
     *        通电状态：td_status：0：断电，1:通电
     *        付费模式：mode：0：后付费，1:预付费
     */
    @Override
    public Map<String, Object> status(Map<String, Object> params) {
        String uuid = ObjectUtils.toString(params.get("devId"), "");
        String roomId = ObjectUtils.toString(params.get("roomId"), "");
        String homeId = ObjectUtils.toString(params.get("homeId"), "");
        GetEleMeterInfoResponse infoResponse=DingDingEleMeterUtils.getEleMeterInfo(homeId,roomId,uuid);
        EleMeterGetPriceAndWayResponse priceAndWayResponse = DingDingEleMeterUtils.eleMeterGetPriceAndWay(homeId,roomId,uuid);
        Map<String,Object> result=new HashMap<>();
        if(infoResponse.getErrNo()==0&&priceAndWayResponse.getErrNo()==0){
            result.put("success","true");
            if(infoResponse.getTrans_status()==1){
                //判断在线状态
                result.put("wifiOnlineStatus","on");
            }else{
                result.put("wifiOnlineStatus","off");
            }
            if(infoResponse.getEnable_state()==1){
                //判断合闸状态
                result.put("electricDoorSwitchStatus","on");
            }else{
                result.put("electricDoorSwitchStatus","off");
            }
            result.put("price",priceAndWayResponse.getRoomElePrice()==null?priceAndWayResponse.getClientElePrice():priceAndWayResponse.getRoomElePrice());
            result.put("mode",1);//丁盯电表不提供付费模式，所以这里默认全是预付费模式
        }else{
            result.put("msg",infoResponse.getErrMsg());
        }
        return result;
    }

    /**
     * 获取电表用电日志信息
     * @param params 参数
     * uuid：电表UUID；bdate：开始时间；edate:结束时间
     * @return 日志信息
     * @throws ParseException 日期转换异常
     */
    @Override
    public Map<String, Object> log(Map<String, Object> params) throws ParseException{
        String uuid = ObjectUtils.toString(params.get("uuid"), "");
        String bdate = ObjectUtils.toString(params.get("bdate"), "");
        String edate = ObjectUtils.toString(params.get("edate"), "");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date start=sdf.parse(bdate);
        Date end=sdf.parse(edate);
        List<Map<String, Object>> datas = Lists.newArrayList();
        Calendar ca = Calendar.getInstance();
        Map<String,Object> result=Maps.newHashMap();
        result.put("success", "true");
        result.put("data",datas);
        //一次取一天的数据，有多少天就需要调用多少次接口
        while(start.compareTo(end)<=0){
            ca.setTime(start);
            Date tempStart=ca.getTime();
            Calendar tempCa=Calendar.getInstance();
            tempCa.add(Calendar.DATE, 1);
            Date tempEnd=tempCa.getTime();
            Map<String, Object> data = Maps.newHashMap();
            EleMeterFetchPowerConsumptionResponse powerConsumptionResponse = DingDingEleMeterUtils.eleMeterFetchPowerConsumption(uuid,tempStart,tempEnd);
            if(powerConsumptionResponse.getErrNo()==0){
                data.put("Allpower", powerConsumptionResponse.getConsumption()==null?"":powerConsumptionResponse.getConsumption());
                //当前日期的用电量
                data.put("Date",sdf.format(start));
                //当前日期
                data.put("Lastpower","");
                //剩余电量，丁盯提供的接口无法取到这个数据，恒为空。
                datas.add(data);
            }
            ca.add(Calendar.DATE, 1);
            start = ca.getTime();
        }
        return result;
    }


    @Override
    public Map<String, Object> syncHouse(House house, SupplierProduct supplierProduct) throws ThirdPartyRequestException, DataErrorException {
        return houseSyncService.syncDingDingHouse(house,supplierProduct);
    }

    /**
     * 丁盯电量充值
     * @param devId 电表devId
     * @param amount 充电电量
     * @throws Exception
     */
    public void chargeAmount(String devId, Float amount) throws Exception{
        BaseResponse baseResponse=DingDingEleMeterUtils.eleMeterCharge(devId,amount);
        if(baseResponse==null){
            throw new ThirdPartyRequestException("请求丁盯电表充值接口失败");
        }
        if(baseResponse.getErrNo()!=null&&baseResponse.getErrNo()!=0){
            throw new ThirdPartyRequestException("丁盯电表充值返回失败:"+ JSONObject.toJSONString(baseResponse));
        }
    }

	@Override
	public DeviceRechargeRecordResponse getDeviceRechargeRecord(String devId, String bdate, String edate,BigDecimal price)throws Exception {
		// TODO Auto-generated method stub
		return DingDingEleMeterUtils.getDeviceRechargeRecord(devId,bdate,edate,price);
	}

	@Override
	public Double surplusElectricity(String devId) throws Exception{
		// TODO Auto-generated method stub
		GetEleMeterInfoResponse getEleMeterInfoResponse=DingDingEleMeterUtils.getSurplusElectricity(devId);
		double  val=0;
		if(getEleMeterInfoResponse.getErrNo()!=null&&getEleMeterInfoResponse.getErrNo()==0){
			BigDecimal subtract = BigDecimal.valueOf(getEleMeterInfoResponse.getPower_total())
					.subtract(BigDecimal.valueOf(getEleMeterInfoResponse.getConsume_amount()));
			val= subtract.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
			return val;
		}else
		{
			//获取接口信息失败  可以抛出异常
			 throw new ThirdPartyRequestException("丁盯电表查询剩余电量信息失败:"+ JSONObject.toJSONString(getEleMeterInfoResponse));
		}
	}

	@Override
	public JSONObject getUserRechargeRecord(String bdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
    public void reset(String devId) throws Exception {
        BaseResponse baseResponse=DingDingEleMeterUtils.eleMeterResetByCollector(devId);
        if(baseResponse==null){
            throw new ThirdPartyRequestException("请求丁盯电表重置接口失败");
        }
        if(baseResponse.getErrNo()!=null&&baseResponse.getErrNo()!=0){
            throw new ThirdPartyRequestException("丁盯电表重置返回失败:"+ JSONObject.toJSONString(baseResponse));
        }
    }


    @Override
    public AmmeterSupplier getSupplierCode() {
        return AmmeterSupplier.DINGDING;
    }
}
