package com.hardware.business.dao;

import com.hardware.business.model.SystemConf;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统参数数据访问实现类
 * 
 * @author shenpeng
 * 
 */
@Repository("systemConfDao")
public class SystemConfDao extends BaseHibernateDaoSupport<SystemConf, String> {
	@Override
	public List<SystemConf> findByModel(SystemConf model, Order order, Pager pager) {
		model = model == null ? new SystemConf() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from SystemConf c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id='" + model.getId() + "' " : "");
		hql.append(
				ValidatorUtils.isNotEmpty(model.getParamKey()) ? " and c.paramKey='" + model.getParamKey() + "' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getParamValue())
				? " and c.paramValue='" + model.getParamValue() + "' " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getRemark()) ? " and c.remark like '%" + model.getRemark() + "%' "
				: "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	/**
	 * 根据参数key获取配置模型对象
	 * 
	 * @param paramKey
	 *            参数key名称
	 * @return SystemConf 配置模型对象
	 */
	public SystemConf findByParamKey(String paramKey) {
		if (ValidatorUtils.isEmpty(paramKey)) {
			return null;
		}
		List<SystemConf> confs = this
				.queryHQL("from SystemConf c where c.paramKey='" + paramKey + "' and c.status='AVAILABLE'");
		return confs != null && confs.size() > 0 ? confs.get(0) : null;
	}

	/**
	 * 根据参数key模糊查询获取配置模型对象集合
	 * 
	 * @param paramKey
	 *            参数key名称
	 * @return List<SystemConf> 配置模型对象集合
	 */
	public List<SystemConf> findLikeByParamKey(String paramKey) {
		if (ValidatorUtils.isEmpty(paramKey)) {
			return null;
		}
		List<SystemConf> confs = this
				.queryHQL("from SystemConf c where c.paramKey like '%" + paramKey + "%' and c.status='AVAILABLE'");
		return confs != null && confs.size() > 0 ? confs : null;
	}
}
