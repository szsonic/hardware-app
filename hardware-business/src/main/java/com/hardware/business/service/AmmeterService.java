package com.hardware.business.service;


import com.hardware.business.domain.AmmeterResult;
import com.hardware.business.model.Ammeter;
import com.hardware.business.model.Supplier;
import com.hardware.business.model.SupplierProduct;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 电表业务接口<br>
 * 
 * @author zhongqi
 *
 */

public interface AmmeterService extends IBaseServiceSupport<Ammeter, String> {

	/**
	 * 根据用户的手机号码查询电表的devid
	 * 
	 * @param mobile
	 *          用户的手机号码
	 * @return 返回电表的devid
	 */
	public String getDevidByUserMobile(String mobile);

	/**
	 * 根据设备号查找供应商ID
	 * 
	 * @param devId
	 *          设备号
	 * @return 返回电表的Supplier
	 */
	public Supplier getSupplierByDevId(String devId);

	/**
	 * 根据设备号查找供应商产品ID
	 * 
	 * @param devId
	 *          设备号
	 * @return 返回电表的Supplier
	 */
	public SupplierProduct getSupplierProByDevId(String devId);

	/**
	 * 根据设备号查找电表
	 * 
	 * @param devId
	 *          设备号
	 * @return 返回电表
	 */
	public Ammeter getAmmeterByDevId(String devId);

	/**
	 * 
	 * @param devId
	 *          电表唯一ID
	 * @param amount
	 *          充值金额
	 * @return 返回支付结果
	 */
	public String pay(String devId, String amount);

	/**
	 * 设置电表付费模式
	 * 
	 * @param devId
	 *          电表唯一ID
	 * @param mod
	 *          付费模式：0是后付费，1是预付费
	 * @return
	 */
	public String setPayMod(String devId, String mod);

	
	public void updateelectricityStatus(String devId, String status);

	public void updateInlineStatus(String devId, String status);


	/*
	 * 第三方派遣师傅安装电表后回调接口：更新电表信息
	 * @param syncOlineCode: 同步Code #注意：每个房源对应自己的onlineCode
	 * @param doorType：1 大门  2 小门
	 * @param devId：设备唯一标识
	 */
	public void updateByDevIdAndSyncCode(String syncOlineCode, String doorType, String devId) throws Exception;
	
	
	public Integer getAmmeterByInstalling();
	
	public Integer getAmmeterByInstalled();
	
	public Integer getAmmeterOfOn();
	
	public Integer getAmmeterOfOff();
	
	/*
	 * 分页查询已安装电表
	 */
	public List<Ammeter> listByModel(Ammeter model, Order order, Pager pager) throws Exception;

	//获取所有已安装的电表
	public List<Ammeter> findAllInstalledAmmeters() throws Exception;


	AmmeterResult control(Ammeter ammeter, String action, SupplierProduct supplierProduct) ;

	void reset(Ammeter ammeter, SupplierProduct supplierProduct) throws Exception;

	void  updatePrice(Ammeter ammeter, Float price);

	void chargeAmount(Ammeter ammeter, Float amount, SupplierProduct supplierProduct) throws Exception;

}
