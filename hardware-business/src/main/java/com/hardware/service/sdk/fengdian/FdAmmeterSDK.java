package com.hardware.service.sdk.fengdian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hardware.business.client.HttpRequestUtils;
import com.hardware.business.conf.FengdianConf;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.domain.DeviceRechargeRecordResponse;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.model.House;
import com.hardware.business.model.SupplierProduct;
import com.hardware.business.service.HouseSyncService;
import com.hardware.business.utils.PropertiesUtils;
import com.hardware.service.sdk.enums.AmmeterSupplier;
import com.hardware.service.sdk.fengdian.utils.FengdianUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.iframework.commons.utils.log.Log;
import org.iframework.support.spring.memcached.MemcachedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 蜂电电表SDK实现类
 * 
 * @author zq
 *
 */
@Component
public class FdAmmeterSDK implements FDAmmeter {
	/**
	 * 缓存的key
	 */
	public static String CACHE_KEY = "fengdian_user_token";

	static {
		Properties properties = PropertiesUtils.getProperties();
		String account = properties.getProperty("account");
		if (StringUtils.equals("test", account)) {
			CACHE_KEY = "fengdian_user_token_test";
		}
	}
	@Autowired
	private MemcachedManager memcachedManager;
	@Autowired
	private HouseSyncService houseSyncService;

	public FdAmmeterSDK(MemcachedManager memcachedManager) {
		super();
		this.memcachedManager = memcachedManager;
	}

	public FdAmmeterSDK(HouseSyncService houseSyncService) {
		super();
		this.houseSyncService = houseSyncService;
	}

	public FdAmmeterSDK() {
	}

	@Override
	public Integer electricity(Map<String, Object> params) {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		String uuid = ObjectUtils.toString(params.get("uuid"), "");
		String bdate = ObjectUtils.toString(params.get("bdate"), "");
		String edate = ObjectUtils.toString(params.get("edate"), "");

		AmmeterResult ammeterResult = FengdianUtil.report(uid, token, uuid, bdate, edate);
		if (ammeterResult.getCode().equals("0")) {
			JSONArray list = (JSONArray) ammeterResult.getData();
			Double allpower = 0.00;
			for (Object d : list) {
				JSONObject o = (JSONObject) d;
				allpower += o.getDouble("Allpower");
			}
			return (int) Math.floor(allpower);
		}
		return null;
	}

	@Override
	public Map<String, Object> log(Map<String, Object> params) {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		String uuid = ObjectUtils.toString(params.get("uuid"), "");
		String bdate = ObjectUtils.toString(params.get("bdate"), "");
		String edate = ObjectUtils.toString(params.get("edate"), "");
		Map<String, Object> logMap = FengdianUtil.log(uid, token, uuid, bdate, edate);
		return logMap;
	}

	@Override
	public Integer surplusElectricity(Map<String, Object> params) {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		String uuid = ObjectUtils.toString(params.get("uuid"), "");

		AmmeterResult ammeterResult = FengdianUtil.ammeterList(uid, token, uuid);
		if (ammeterResult.getCode().equals("0")) {
			JSONArray data = (JSONArray) ammeterResult.getData();
			BigDecimal se = null;
			for (Object d : data) {
				JSONObject o = (JSONObject) d;
				JSONObject expand = o.getJSONObject("Expand");
				Double allpower = 0.0;
				if (expand == null) {
					allpower = 0.0;
				} else {
					allpower = expand.getDouble("allpower");
				}
				JSONObject param = o.getJSONObject("Param");
				Double threshold = param.getDouble("threshold") == null ? 0 : param.getDouble("threshold");
				Double apportion = param.getDouble("apportion") == null ? 0 : param.getDouble("apportion");
				BigDecimal multiply = BigDecimal.valueOf(allpower).multiply(BigDecimal.valueOf(100));
				BigDecimal subtract = BigDecimal.valueOf(threshold).subtract(BigDecimal.valueOf(apportion)).subtract(multiply);
				se = subtract.divide(BigDecimal.valueOf(100f));
			}
			return se.intValue();
		}
		return null;
	}

	/**
	 * 从缓存中获取蜂电的用户信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getUserInfoByCache() {
		Object loadObject = memcachedManager.loadObject(CACHE_KEY);
		if (loadObject != null) {
			Map<String, String> parseObject = JSON.parseObject(ObjectUtils.toString(loadObject), Map.class);
			return parseObject;
		}
		return null;
	}

	/**
	 * 将登录成功后的用户信息设置到缓存中
	 * 
	 * @param usrObj
	 */
	private void setUserInfoToCache(Map<String, String> usrObj) {
		memcachedManager.cacheObject(CACHE_KEY, JSON.toJSONString(usrObj), 3500);
	}

	/**
	 * 获取电表用户信息，主要获取token
	 * 
	 * @return
	 */
	private Map<String, String> getUserInfo() {
		Map<String, String> usrObj = getUserInfoByCache();
		if (usrObj == null) {
			usrObj = FengdianUtil.userLogin();
			if (!usrObj.get("code").equals("0")) {
				throw new RuntimeException("电表用户登录不成功，操作失败！");
			}
			setUserInfoToCache(usrObj);
		}
		return usrObj;
	}

	@Override
	public AmmeterResult setPayMod(Map<String, Object> params) {
		String uuid = ObjectUtils.toString(params.get("uuid"), "");
		String mod = ObjectUtils.toString(params.get("mod"), "");

		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");

		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append("/device/ammeter/paymode/");
		url.append(uuid).append("/");
		url.append(mod);
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("uid", uid);
		requestParams.put("token", token);

		String result = HttpRequestUtils.put(url.toString(), requestParams);

		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterDataBean = JSON.parseObject(result, AmmeterResult.class);
			return ammeterDataBean;
		}
		return null;
	}

	@Override
	public AmmeterResult pay(Map<String, Object> params) {
		String uuid = ObjectUtils.toString(params.get("uuid"), "");
		String payMod = ObjectUtils.toString(params.get("payMod"), "");
		int value = NumberUtils.toInt(ObjectUtils.toString(params.get("value"), "0"));

		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		String t_url = "";
		if (payMod.equals("1")) {
			// 预付费
			t_url = "/device/ammeter/threshold/";
			value = value * 100;
		} else if (payMod.equals("0")) {
			// 后付费
			t_url = "/device/ammeter/rechargevalue/";
		}
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append(t_url);
		url.append(uuid).append("/");
		url.append(value);
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("uid", uid);
		requestParams.put("token", token);
		Log.i("===电表充值：" + JSON.toJSONString(requestParams));
		String result = HttpRequestUtils.put(url.toString(), requestParams);

		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterResult = JSON.parseObject(result, AmmeterResult.class);
			return ammeterResult;
		}
		return null;
	}

	@Override
	public AmmeterResult control(Map<String, Object> params) {
		String uuid = ObjectUtils.toString(params.get("uuid"), "");
		String action = ObjectUtils.toString(params.get("action"), "");

		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		String t_url = "";
		if (action.equals("on")) {
			// 通电
			t_url = "/device/switchon/";
		} else if (action.equals("off")) {
			// 断电
			t_url = "/device/switchoff/";
		}
		StringBuilder url = new StringBuilder(FengdianConf.HOST);
		url.append(t_url).append(uuid);
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("uid", uid);
		requestParams.put("token", token);

		String result = HttpRequestUtils.put(url.toString(), requestParams);

		if (StringUtils.isNotBlank(result)) {
			AmmeterResult ammeterResult = JSON.parseObject(result, AmmeterResult.class);
			return ammeterResult;
		}
		return null;
	}

	@Override
	public Map<String, String> bindTerminal(Map<String, Object> params) {
		String cid = ObjectUtils.toString(params.get("cid"), "");
		String macId = "";
		if (cid.length() <= 12) {
			macId = cid;
		} else {
			macId = cid.substring(11, cid.length());
		}

		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		AmmeterResult ammeterResult = FengdianUtil.bindTerminal(uid, token, cid, macId);
		if (ammeterResult.getCode().equals("0")) {
			Map<String, String> data = new HashMap<>();
			JSONObject o = (JSONObject) ammeterResult.getData();
			String cid_u = o.getString("Cid");
			data.put("terminalId", cid_u);
			String uuid = FengdianUtil.getUuid(uid, token, cid_u);
			data.put("devId", uuid);
			return data;
		} else {
			throw new HardwareSdkException("绑定集中器失败：" + ammeterResult.getMessage());
		}
	}

	@Override
	public Map<String, String> bindNode(Map<String, Object> params) {
		String macId = ObjectUtils.toString(params.get("cid"), "");
		String nid = ObjectUtils.toString(params.get("nid"), "");

		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		AmmeterResult ammeterResult = FengdianUtil.bindNode(uid, token, macId, nid);
		if (ammeterResult.getCode().equals("0")) {
			Map<String, String> data = new HashMap<>();
			JSONObject o = (JSONObject) ammeterResult.getData();
			String nid_u = o.getString("Nid");
			data.put("nodeId", nid_u);
			String uuid = FengdianUtil.getUuid(uid, token, nid_u);
			data.put("devId", uuid);
			return data;
		} else {
			throw new HardwareSdkException("绑定集中器失败：" + ammeterResult.getMessage());
		}
	}

	@Override
	public Map<String, Object> status(Map<String, Object> params) {
		String devId = ObjectUtils.toString(params.get("devId"), "");
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		Map<String, Object> result = FengdianUtil.status(uid, token, devId);
		return result;
	}

	@Override
	public String recedeRoom(String devId) {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		AmmeterResult ammeterResult = FengdianUtil.recedeRoom(uid, token, devId);
		if (ammeterResult.getCode().equals("0")) {
			return "true";
		} else {
			throw new HardwareSdkException(ammeterResult.getMessage());
		}
	}

	@Override
	public String stayRoom(String devId) {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		AmmeterResult ammeterResult = FengdianUtil.stayRoom(uid, token, devId);
		if (ammeterResult.getCode().equals("0")) {
			return "true";
		} else {
			throw new HardwareSdkException(ammeterResult.getMessage());
		}
	}

	@Override
	public String getUuid(String pid) {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		String uuid = FengdianUtil.getUuid(uid, token, pid);
		return uuid;
	}

	/**
	 * 由于蜂电不需要同步房源信息，所以这里直接返回null
	 * @param house 房源信息
	 * @param supplierProduct 对应的产品发起
	 * @return null
	 */
	@Override
	public Map<String, Object> syncHouse(House house, SupplierProduct supplierProduct) {
		return null;
	}

	@Override
	public void chargeAmount(String devId, Float amount) throws Exception {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		FengdianUtil.chargeAmount(uid,token,devId,amount);
	}

	@Override
	public DeviceRechargeRecordResponse getDeviceRechargeRecord(String devId, String bdate, String edate, BigDecimal price) throws ParseException {
		// TODO Auto-generated method stub
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		List list=new ArrayList();
		return FengdianUtil.getDeviceRechargeRecord(uid,token,devId,bdate,edate,price);
	}

	@Override
	public Double surplusElectricity(String devId) {
		// TODO Auto-generated method stub
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		String uuid = ObjectUtils.toString(devId, "");

		AmmeterResult ammeterResult = FengdianUtil.ammeterList(uid, token, uuid);
		if (ammeterResult.getCode().equals("0")) {
			JSONArray data = (JSONArray) ammeterResult.getData();
			BigDecimal se = null;
			double  val=0;
			for (Object d : data) {
				JSONObject o = (JSONObject) d;
				JSONObject expand = o.getJSONObject("Expand");
				Double allpower = 0.0;
				if (expand == null) {
					allpower = 0.0;
				} else {
					allpower = expand.getDouble("allpower");
				}
				JSONObject param = o.getJSONObject("Param");
				Double threshold = param.getDouble("threshold") == null ? 0 : param.getDouble("threshold");
				Double apportion = param.getDouble("apportion") == null ? 0 : param.getDouble("apportion");
				BigDecimal multiply = BigDecimal.valueOf(allpower).multiply(BigDecimal.valueOf(100));
				BigDecimal subtract = BigDecimal.valueOf(threshold).subtract(BigDecimal.valueOf(apportion)).subtract(multiply);
				se = subtract.divide(BigDecimal.valueOf(100f));
				val= se.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
			}
			return val;
		}else
		{
			//获取接口信息失败  可以抛出异常
			throw new HardwareSdkException("蜂电电表剩余电表余量查询失败：" + ammeterResult.getMessage());
		}
	}

	@Override
	public JSONObject getUserRechargeRecord(String bdate, String edate) {
		// TODO Auto-generated method stub
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		return FengdianUtil.getUserRechargeRecord(uid,token,bdate,edate);
	}

	@Override
	public void reset(String devId) throws Exception {
		Map<String, String> usrObj = getUserInfo();
		String uid = ObjectUtils.toString(usrObj.get("uid"), "");
		String token = ObjectUtils.toString(usrObj.get("token"), "");
		FengdianUtil.recedeRoom(uid,token,devId);
	}

	@Override
	public AmmeterSupplier getSupplierCode() {
		return AmmeterSupplier.FENGDIAN;
	}
}
