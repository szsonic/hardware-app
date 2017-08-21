package com.hardware.business.service;

import com.hardware.business.model.SystemConf;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 系统配置服务接口<br>
 * 
 * @author shenpeng
 * 
 */
public interface SystemConfService extends IBaseServiceSupport<SystemConf, String> {

	/**
	 * 通过参数名称获取参数信息
	 * 
	 * @param paramKey
	 *            参数名称
	 * @return 参数对象
	 */
	public SystemConf findByParamKey(String paramKey);
	
	/**
	 * 根据参数名称获取参数集合（模糊查询）
	 * @param paramKey 参数名称
	 * @return 参数对象
	 */
	public List<SystemConf> findLikeByParamKey(String paramKey);
	
	/**
	 * 遍历充值金额配置数据
	 * 
	 * @return List<String>
	 */
	public List<Integer> findRechangePriceList();

}
