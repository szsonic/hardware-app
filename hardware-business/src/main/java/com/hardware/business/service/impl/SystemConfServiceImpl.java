package com.hardware.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hardware.business.dao.SystemConfDao;
import com.hardware.business.model.SystemConf;
import com.hardware.business.service.SystemConfService;
import com.utils.ValidatorUtils;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统配置服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author shenpeng
 * 
 */
@Service("systemConfService")
public class SystemConfServiceImpl extends BaseServiceSupport<SystemConf, String> implements SystemConfService {

	@Resource(name = "systemConfDao")
	@Override
	public void setBaseHibernateDaoSupport(@Qualifier("systemConfDaoImpl") BaseHibernateDaoSupport<SystemConf, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public SystemConf findByParamKey(String paramKey) {
		return ((SystemConfDao)this.baseHibernateDaoSupport).findByParamKey(paramKey);
	}

	@Override
	public List<SystemConf> findLikeByParamKey(String paramKey) {
		return ((SystemConfDao)this.baseHibernateDaoSupport).findLikeByParamKey(paramKey);
	}
	
	/**
	 * 遍历充值金额配置数据
	 * 
	 * @return List<String>
	 */
	public List<Integer> findRechangePriceList(){
		SystemConf systemConf = this.findByParamKey("PROJECT_RECHARGE_CONFIG_ITEMS");
		
		if (ValidatorUtils.isEmpty(systemConf)) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		String paramValue =	systemConf.getParamValue();
		if (ValidatorUtils.isEmpty(paramValue)) {
			return null;
		}	
		JSONObject obj = JSONObject.parseObject(paramValue);
		for (Map.Entry<String, Object> entry : obj.entrySet()) {
			if (ValidatorUtils.isNotEmpty(entry)) {
				list.add( (Integer) entry.getValue());
			}
		}
		return list;
	}

}
