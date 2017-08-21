package com.hardware.service.sdk;

import com.alibaba.fastjson.JSONObject;
import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.domain.DeviceRechargeRecordResponse;
import com.hardware.business.model.House;
import com.hardware.business.model.SupplierProduct;
import com.hardware.service.sdk.enums.AmmeterSupplier;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

/**
 * 电表的SDK接口
 * 
 * @author zhongqi
 *
 */
public interface IAmmeter {
	/**
	 * 获取电表已用电量
	 * 
	 * @param params
	 *          电表查询电量需要的参数
	 * @return 返回电表的电量
	 */
	public Integer electricity(Map<String, Object> params);

	/**
	 * 获取电表剩余电量
	 * 
	 * @param params
	 *          电表查询电量需要的参数
	 * @return 返回电表的电量
	 */
	public Integer surplusElectricity(Map<String, Object> params);
    /**
     *  * 获取电表剩余电量(精度保留两位小数)
	 * 
	 * @param params
	 *          电表查询电量需要的参数
	 * @return 返回电表的电量
     */
	public Double surplusElectricity(String devId)  throws Exception;
	/**
	 * 设置电表付费模式
	 * 
	 * @param params
	 *          设置电表付费模式需要的参数
	 * @return {"success":"true","msg":"成功"}
	 */
	public AmmeterResult setPayMod(Map<String, Object> params);

	/**
	 * 电表付费
	 * 
	 * @param params
	 *          电表付费需要的参数
	 * @return {"success":"true","msg":"成功"}
	 */
	public AmmeterResult pay(Map<String, Object> params);

	/**
	 * 电表控制（断电，通电）
	 * 
	 * @param params
	 *          电表控制需要的参数
	 * @return {"success":"true","msg":"成功"}
	 */
	public AmmeterResult control(Map<String, Object> params);

	/**
	 * 获取电表的状态
	 * 
	 * @param params
	 *          获取设备状态的参数
	 * @return {"success":"true","msg":"成功"}
	 */
	public Map<String, Object> status(Map<String, Object> params);

	/**
	 * 电表用电历史记录
	 * 
	 * @param params
	 * @return
	 */

	public Map<String, Object> log(Map<String, Object> params) throws ParseException;


	/**
	 * 同步房源接口，由于丁盯创建电表时也需要房源的存在，所以父接口中需要该同步房源的方法
	 * 如果第三方供应商系统中不需要对应的房源信息，这里可以直接返回null，不作任何处理（比如蜂电）
	 * @param house 房源信息
	 * @param supplierProduct 对应的产品发起
	 * @return null 暂不处理返回值
	 * @since V1.1.2
	 */
	Map<String,Object> syncHouse(House house, SupplierProduct supplierProduct) throws Exception;
	
	/**
	 * 查询设备充值记录
	 */
	public DeviceRechargeRecordResponse getDeviceRechargeRecord(String devId, String bdate, String edate, BigDecimal price) throws   Exception;
	/**
	 * 查询用户充值记录
	 */
	public JSONObject  getUserRechargeRecord(String bdate, String edate);


	void chargeAmount(String devId, Float amount) throws Exception;

	void reset(String devId) throws Exception;

	AmmeterSupplier getSupplierCode();

}
