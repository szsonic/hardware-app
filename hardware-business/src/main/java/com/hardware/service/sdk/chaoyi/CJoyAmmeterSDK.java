package com.hardware.service.sdk.chaoyi;

import com.alibaba.fastjson.JSONObject;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.domain.DeviceRechargeRecordResponse;
import com.hardware.business.exception.ThirdPartyRequestException;
import com.hardware.business.model.House;
import com.hardware.business.model.SupplierProduct;
import com.hardware.business.service.HouseSyncService;
import com.hardware.service.sdk.IAmmeter;
import com.hardware.service.sdk.enums.AmmeterSupplier;
import org.apache.commons.lang3.ObjectUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

@Component
public class CJoyAmmeterSDK implements IAmmeter {
	@Autowired
	private MemcachedManager memcachedManager;
	@Autowired
	private HouseSyncService houseSyncService;

    public CJoyAmmeterSDK(MemcachedManager memcachedManager) {
        super();
        this.memcachedManager = memcachedManager;
    }
    public CJoyAmmeterSDK(HouseSyncService houseSyncService) {
        super();
        this.houseSyncService = houseSyncService;
    }

	public CJoyAmmeterSDK() {
	}

	@Override
	public Integer electricity(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer surplusElectricity(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double surplusElectricity(String devId) throws Exception {
		Map<String,String> result
		   = CJoyServiceUtil.getSurplusElectricity(memcachedManager, devId);
		
		double val = 0;
		if(ValidatorUtils.isEquals("true", result.get("success"))){
			String surplus = result.get("surplus"); //剩余电量
			BigDecimal subtract = BigDecimal.valueOf(Double.valueOf(surplus));
			val = subtract.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return val;
		} else{
			//获取接口信息失败  可以抛出异常
			throw new ThirdPartyRequestException("超仪电表查询剩余电量信息失败:" + result.get("msg"));
		}
	}

	@Override
	public AmmeterResult setPayMod(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AmmeterResult pay(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AmmeterResult control(Map<String, Object> params) {
		String meterNo = params.get("uuid").toString();
		String action = params.get("action").toString();
		
		return CJoyServiceUtil.control(memcachedManager, meterNo, action);
	}

	@Override
	public Map<String, Object> status(Map<String, Object> params) {
		String meterNo = params.get("devId") == null ? "" : params.get("devId").toString();
		return CJoyServiceUtil.status(memcachedManager, meterNo);
	}

	/*
	 * 获取用电日志
	 * @param meterNo: 设备ID
	 * @param bdate:查询区间起始时间
	 * @param edate:查询区间结束时间
	 */
	@Override
	public Map<String, Object> log(Map<String, Object> params) throws ParseException {
		String meterNo = ObjectUtils.toString(params.get("uuid"), "");
		String startDate = ObjectUtils.toString(params.get("bdate"), "");
		String endDate = ObjectUtils.toString(params.get("edate"), "");
		
		return CJoyServiceUtil.findReadInfoByDate(memcachedManager, meterNo, startDate, endDate);
	}

	@Override
	public Map<String, Object> syncHouse(House house, SupplierProduct supplierProduct) throws Exception {
		houseSyncService.syncHouse(house, supplierProduct);
		return null;
	}

	@Override
	public DeviceRechargeRecordResponse getDeviceRechargeRecord(String devId, String bdate, String edate,
			BigDecimal price) throws Exception {
		return CJoyServiceUtil.getDeviceRechargeRecord(memcachedManager, devId, bdate, edate);
	}

	@Override
	public JSONObject getUserRechargeRecord(String bdate, String edate) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 超仪电表只支持费用充值
	 * @param ammout: 充值电量
	 */
	@Override
	public void chargeAmount(String devId, Float ammout) throws Exception {
		CJoyServiceUtil.chargeAmount(memcachedManager, devId, ammout);
	}

	@Override
	public void reset(String meterNo) throws Exception {
		CJoyServiceUtil.clearBalanceByMeterNo(memcachedManager, meterNo);
	}

	@Override
	public AmmeterSupplier getSupplierCode() {
		return AmmeterSupplier.CHAOYI;
	}
}
