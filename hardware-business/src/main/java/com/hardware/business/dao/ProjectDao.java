package com.hardware.business.dao;

import com.hardware.business.model.Project;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目类
 * 
 * @author zhongqi
 * 
 */
@Repository("projectDao")
public class ProjectDao extends BaseHibernateDaoSupport<Project, String> {

	@Override
	public List<Project> findByModel(Project model, Order order, Pager pager) {
		model = model == null ? new Project() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from Project c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getName()) ? " and c.name=:name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getCode()) ? " and c.code=:code " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public Project getByCode(String code) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Project p where 1=1");
		hql.append(" and p.code=").append("'" + code + "'");
		hql.append(" and p.status='AVAILABLE'");
		List<Project> list = this.queryHQL(hql.toString());
		return list == null || list.size() == 0 ? null : list.get(0);
	}

}
