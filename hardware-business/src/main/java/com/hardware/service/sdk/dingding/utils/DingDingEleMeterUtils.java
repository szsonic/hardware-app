package com.hardware.service.sdk.dingding.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hardware.business.domain.DeviceRechargeRecordRes;
import com.hardware.business.domain.DeviceRechargeRecordResponse;
import com.hardware.business.model.dingding.*;
import com.hardware.business.utils.DataUtil;
import com.hardware.business.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.iframework.commons.utils.http.HttpClientUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 丁盯电表操作相关工具类<br>
 *
 * @author sunzhongshuai
 */
public class DingDingEleMeterUtils extends DingDingUtil {

	/**
	 * 获取电表信息,对应接口文档11.1 如果roomId唯一，可以不传homeId roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓ID
	 * @param roomId
	 *            房间ID
	 * @param uuid
	 *            电表uuid
	 * @return
	 */
	public static GetEleMeterInfoResponse getEleMeterInfo(String homeId, String roomId, String uuid) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("get_elemeter_info?access_token={access_token}");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
			url.append("&home_id={home_id}");
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
			url.append("&room_id={room_id}");
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
			url.append("&uuid={uuid}");
		}
		return restTemplate.getForObject(url.toString(), GetEleMeterInfoResponse.class, params);
	}

	/**
	 * 获取电表信息
	 *
	 * @param uuid
	 *            电表uuid
	 * @return 电表信息
	 */
	public static GetEleMeterInfoResponse getEleMeterInfo(String uuid) {
		return getEleMeterInfo(null, null, uuid);
	}

	/**
	 * 电表充电，对应接口文档11.2 如果roomId唯一，可以不传homeId roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @param amount
	 *            充电电量
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterCharge(String homeId, String roomId, String uuid, Float amount) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_charge");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		if (amount != null) {
			params.put("amount", amount);
		}
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 电表充电
	 *
	 * @param uuid
	 *            电表uuid
	 * @param amount
	 *            充电电量
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterCharge(String uuid, Float amount) {
		return eleMeterCharge(null, null, uuid, amount);
	}




    /**
     * 设置电表透支额度，对应接口文档11.3
     * 如果roomId唯一，可以不传homeId
     * roomId和uuid必须要有一个，优先取uuid
     * @param homeId 公寓id
     * @param roomId  房间id
     * @param uuid 电表uuid
     * @param overdraft 透支电量（单位度）
     * @return 基础返回实体
     */
    public static BaseResponse eleMeterUpdateOverdraft(String homeId, String roomId, String uuid, Float overdraft) {
        StringBuilder url= new StringBuilder(BASE_URL);
        url.append("elemeter_update_overdraft");
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(homeId)){
            params.put("home_id", homeId);
        }
        if(StringUtils.isNotBlank(roomId)){
            params.put("room_id", roomId);
        }
        if(StringUtils.isNotBlank(uuid)){
            params.put("uuid", uuid);
        }
        if(overdraft!=null){
            params.put("overdraft", overdraft);
        }
        return  restTemplate.postForObject(url.toString(),params,BaseResponse.class);
    }



    /**
     * 设置电表透支额度，对应接口文档11.3
     * @param uuid 电表uuid
     * @param overdraft 透支电量（单位度）
     * @return 基础返回实体
     */
    public static BaseResponse eleMeterUpdateOverdraft(String uuid, Float overdraft) {
       return eleMeterUpdateOverdraft(null,null,uuid,overdraft);
    }

    /**
     * 电表重置清零，对应11.4
     * 如果roomId唯一，可以不传homeId
     * roomId和uuid必须要有一个，优先取uuid
     * @param homeId 公寓id
     * @param roomId  房间id
     * @param uuid 电表uuid
     * @return 基础返回实体
     */
    public static BaseResponse eleMeterResetByCollector(String homeId, String roomId, String uuid) {
        StringBuilder url= new StringBuilder(BASE_URL);
        url.append("elemeter_reset_by_collector");
        Map<String,Object> params=new HashMap<>();
        params.put("access_token",getAccessToken());
        if(StringUtils.isNotBlank(homeId)){
            params.put("home_id", homeId);
        }
        if(StringUtils.isNotBlank(roomId)){
            params.put("room_id", roomId);
        }
        if(StringUtils.isNotBlank(uuid)){
            params.put("uuid", uuid);
        }
        return restTemplate.postForObject(url.toString(),params,BaseResponse.class);
    }


	/**
	 * 电表重置清零
	 *
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterResetByCollector(String uuid) {
		return eleMeterResetByCollector(null, null, uuid);
	}

	/**
	 * 电表读取，对应接口文档11.6 如果roomId唯一，可以不传homeId roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @return 返回类型未知
	 */
	public static Object eleMeterRead(String homeId, String roomId, String uuid) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_read");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		return restTemplate.postForObject(url.toString(), params, Object.class);
	}

	/**
	 * 设置电表合闸，对应接口文档11.7 如果roomId唯一，可以不传homeId roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSwitchOn(String homeId, String roomId, String uuid) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("eleMeter_switch_on");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 设置电表合闸
	 *
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSwitchOn(String uuid) {
		return eleMeterSwitchOn(null, null, uuid);
	}

	/**
	 * 设置电表跳闸，对应接口文档11.8 如果roomId唯一，可以不传homeId roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSwitchOff(String homeId, String roomId, String uuid) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("eleMeter_switch_off");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 设置电表跳闸
	 *
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSwitchOff(String uuid) {
		return eleMeterSwitchOff(null, null, uuid);
	}

	/**
	 * 获取电表充值历史记录
	 *
	 * @param request
	 *            请求实体
	 * @return 电表充值历史记录
	 */
	public static EleMeterFetchChargeHistoryResponse eleMeterFetchChargeHistory(
			EleMeterFetchChargeHistoryRequest request) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_fetch_charge_history?access_token={access_token}");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(request.getHomeId())) {
			params.put("home_id", request.getHomeId());
			url.append("&home_id={home_id}");
		}
		if (StringUtils.isNotBlank(request.getRoomId())) {
			params.put("room_id", request.getRoomId());
			url.append("&room_id={room_id}");
		}
		if (StringUtils.isNotBlank(request.getUuid())) {
			params.put("uuid", request.getUuid());
			url.append("&uuid={uuid}");
		}
		if (request.getCount() != null) {
			params.put("count", request.getCount());
			url.append("&count={count}");
		}
		if (request.getOffset() != null) {
			params.put("offset", request.getOffset());
			url.append("&offset={offset}");
		}
		if (request.getStartTime() != null) {
			params.put("start_time", request.getStartTime().getTime());
			url.append("&start_time={start_time}");
		}
		if (request.getEndTime() != null) {
			params.put("end_time", request.getEndTime().getTime());
			url.append("&end_time={end_time}");
		}
		return restTemplate.getForObject(url.toString(), EleMeterFetchChargeHistoryResponse.class, params);
	}

	/**
	 * 获取电表上传历史记录数
	 *
	 * @param request
	 * @return
	 */
	public static EleMeterFetchPowerHistoryResponse eleMeterFetchPowerHistory(
			EleMeterFetchPowerHistoryRequest request) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_fetch_power_history?access_token={access_token}");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(request.getHomeId())) {
			params.put("home_id", request.getHomeId());
			url.append("&home_id={home_id}");
		}
		if (StringUtils.isNotBlank(request.getRoomId())) {
			params.put("room_id", request.getRoomId());
			url.append("&room_id={room_id}");
		}
		if (StringUtils.isNotBlank(request.getUuid())) {
			params.put("uuid", request.getUuid());
			url.append("&uuid={uuid}");
		}
		if (request.getCount() != null) {
			params.put("count", request.getCount());
			url.append("&count={count}");
		}
		if (request.getOffset() != null) {
			params.put("offset", request.getOffset());
			url.append("&offset={offset}");
		}
		if (request.getStartTime() != null) {
			params.put("start_time", request.getStartTime().getTime());
			url.append("&start_time={start_time}");
		}
		if (request.getEndTime() != null) {
			params.put("end_time", request.getEndTime().getTime());
			url.append("&end_time={end_time}");
		}
		return restTemplate.getForObject(url.toString(), EleMeterFetchPowerHistoryResponse.class, params);
	}

	/**
	 * 获取电表某个时间段的用电量，对应接口文档11.15 如果roomId唯一，可以不传homeId。
	 * roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @param startTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间
	 * @return 用电信息实体
	 */
	public static EleMeterFetchPowerConsumptionResponse eleMeterFetchPowerConsumption(String homeId, String roomId,
			String uuid, Date startTime, Date endTime) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_fetch_power_consumption?access_token={access_token}");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
			url.append("&home_id={home_id}");
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
			url.append("&room_id={room_id}");
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
			url.append("&uuid={uuid}");
		}
		if (startTime != null) {
			params.put("start_time", startTime.getTime() / 1000);
			url.append("&start_time={start_time}");
		}
		if (endTime != null) {
			params.put("end_time", endTime.getTime() / 1000);
			url.append("&end_time={end_time}");
		}
		return restTemplate.getForObject(url.toString(), EleMeterFetchPowerConsumptionResponse.class, params);
	}

	/**
	 * 获取电表某个时间段的用电量
	 *
	 * @param uuid
	 *            电表uuid
	 * @param startTime
	 *            查询开始时间
	 * @param endTime
	 *            查询结束时间
	 * @return 用电信息实体
	 */
	public static EleMeterFetchPowerConsumptionResponse eleMeterFetchPowerConsumption(String uuid, Date startTime,
			Date endTime) {
		return eleMeterFetchPowerConsumption(null, null, uuid, startTime, endTime);
	}

	/**
	 * 设置电表电费电价 如果roomId唯一，可以不传homeId。 roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @param roomElePrice
	 *            房间电价
	 * @param clientElePrice
	 *            公寓电价
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSetElePrice(String homeId, String roomId, String uuid, Float roomElePrice,
			Float clientElePrice) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("eleMeter_set_eleprice");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		params.put("home_id", homeId);
		params.put("room_id", roomId);
		params.put("uuid", uuid);
		params.put("room_eleprice", roomElePrice);
		params.put("client_eleprice", clientElePrice);
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 设置电表电费电价
	 *
	 * @param uuid
	 *            电表uuid
	 * @param roomElePrice
	 *            房间电价
	 * @param clientElePrice
	 *            公寓电价
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSetElePrice(String uuid, Float roomElePrice, Float clientElePrice) {
		return eleMeterSetElePrice(null, null, uuid, roomElePrice, clientElePrice);
	}

	/**
	 * 设置电表电价计算方式 如果roomId唯一，可以不传homeId。 roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @param elePriceWay
	 *            电价方式，1：房间电价，2：公寓统一电价
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSetElePriceWay(String homeId, String roomId, String uuid, Integer elePriceWay) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("eleMeter_set_eleprice_way");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		if (elePriceWay != null) {
			params.put("eleprice_way", elePriceWay);
		}
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 设置电表电价计算方式
	 *
	 * @param uuid
	 *            电表uuid
	 * @param elePriceWay
	 *            电价方式，1：房间电价，2：公寓统一电价
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSetElePriceWay(String uuid, Integer elePriceWay) {
		return eleMeterSetElePriceWay(null, null, uuid, elePriceWay);
	}

	/**
	 * 获取电价和电价计算方式,对应接口文档11.19 如果roomId唯一，可以不传homeId。 roomId和uuid必须要有一个，优先取uuid
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @return 电价和电价计算方式实体
	 */
	public static EleMeterGetPriceAndWayResponse eleMeterGetPriceAndWay(String homeId, String roomId, String uuid) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_get_price_and_way?access_token={access_token}");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
			url.append("&home_id={home_id}");
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
			url.append("&room_id={room_id}");
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
			url.append("&uuid={uuid}");
		}
		return restTemplate.getForObject(url.toString(), EleMeterGetPriceAndWayResponse.class, params);
	}

	/**
	 * 获取电价和电价计算方式
	 *
	 * @param uuid
	 *            电表uuid
	 * @return 电价和电价计算方式实体
	 */
	public static EleMeterGetPriceAndWayResponse eleMeterGetPriceAndWay(String uuid) {
		return eleMeterGetPriceAndWay(null, null, uuid);
	}

	/**
	 * 电表电费充值
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @param fees
	 *            充值金额
	 * @param elePriceWay
	 *            电费电价方式，1：房间电价，2：公寓统一电价（不传该参数默认按照房间设定的电价计算方式充值）
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterChargeFees(String homeId, String roomId, String uuid, Float fees,
			Integer elePriceWay) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_charge_fees");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		if (fees != null) {
			params.put("fees", fees);
		}
		if (elePriceWay != null) {
			params.put("eleprice_way", elePriceWay);
		}
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 电表电费充值
	 *
	 * @param uuid
	 *            电表uuid
	 * @param fees
	 *            充值金额
	 * @param elePriceWay
	 *            电费电价方式，1：房间电价，2：公寓统一电价（不传该参数默认按照房间设定的电价计算方式充值）
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterChargeFees(String uuid, Float fees, Integer elePriceWay) {
		return eleMeterChargeFees(null, null, uuid, fees, elePriceWay);
	}

	/**
	 * 电表电费充值清零
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterChargeReset(String homeId, String roomId, String uuid) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_charge_reset");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 电表电费充值清零
	 *
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterChargeReset(String uuid) {
		return eleMeterChargeReset(null, null, uuid);
	}

	/**
	 * 电表同步数据
	 *
	 * @param homeId
	 *            公寓id
	 * @param roomId
	 *            房间id
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSynData(String homeId, String roomId, String uuid) {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_syn_data");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(homeId)) {
			params.put("home_id", homeId);
		}
		if (StringUtils.isNotBlank(roomId)) {
			params.put("room_id", roomId);
		}
		if (StringUtils.isNotBlank(uuid)) {
			params.put("uuid", uuid);
		}
		return restTemplate.postForObject(url.toString(), params, BaseResponse.class);
	}

	/**
	 * 电表同步数据
	 *
	 * @param uuid
	 *            电表uuid
	 * @return 基础返回实体
	 */
	public static BaseResponse eleMeterSynData(String uuid) {
		return eleMeterSynData(null, null, uuid);
	}

	/**
	 * 查询电表电费充值记录
	 *
	 * @param devId
	 * @param bdate
	 * @param edate
	 * @return
	 * @throws Exception
	 */

	public static DeviceRechargeRecordResponse getDeviceRechargeRecord(String devId, String bdate, String edate, BigDecimal price)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("elemeter_fetch_charge_history");
		Map<String, Object> params = new HashMap<>();
		params.put("access_token", getAccessToken());
		if (StringUtils.isNotBlank(devId)) {
			params.put("uuid", devId);
		}
		if (StringUtils.isNotBlank(bdate)) {
			params.put("start_time", TimeUtil.dateToStamp(bdate));
		}
		if (StringUtils.isNotBlank(edate)) {
			params.put("end_time", TimeUtil.dateToStamp(edate));
		}
		String result = HttpClientUtils.get(url.toString(), params);
		DeviceRechargeRecordRes deviceRechargeRecordRes = new DeviceRechargeRecordRes();
		DeviceRechargeRecordResponse dev = new DeviceRechargeRecordResponse();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (StringUtils.isNotBlank(result)) {
			JSONObject data = JSON.parseObject(result, JSONObject.class);
			dev.setCode(data.getInteger("ErrNo") + "");
			if (data.getInteger("ErrNo") == 0) {
				deviceRechargeRecordRes.setMsg("操作成功");
				JSONArray array = data.getJSONArray("history");
				for (Object object : array) {
					Map<String, String> map = new HashMap<String, String>();
					JSONObject o = (JSONObject) object;
                //  电表充值电量
					BigDecimal val_amount= DataUtil.getValue(o.getString("amount"));
					map.put("amount",  DataUtil.getValue(o.getString("amount")).toString());
               //   电表充值金额
					BigDecimal total_price=val_amount.multiply(price);
					BigDecimal money = total_price.setScale(2, total_price.ROUND_DOWN);
				//	map.put("money", Integer.parseInt(money.toString().split("\\.")[0])+"");	
			  //    单价
				//	map.put("price",  price.toString());			
			  //    充值时间
					map.put("time", TimeUtil.stampToDate(o.getString("time")));
					list.add(map);
				}
				deviceRechargeRecordRes.setData(list);
				dev.setRes(deviceRechargeRecordRes);
			} else {
				deviceRechargeRecordRes.setMsg(data.getString("ErrMsg"));
				deviceRechargeRecordRes.setData(list);
				dev.setRes(deviceRechargeRecordRes);
			}

		}
		return dev;
	}

	/**
	 * 查询电表信息，用来计算电表剩余电量
	 *
	 * @param devId
	 * @return
	 */
	public static GetEleMeterInfoResponse getSurplusElectricity(String devId) {
		// TODO Auto-generated method stub
		return getEleMeterInfo(devId);
	}
}
