package com.hardware.business.dao;

import com.hardware.business.model.OperationLog;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.enums.RecordStatus;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作日志类
 *
 * @author zhongqi
 *
 */
@Repository("operationLogDao")
public class OperationLogDao extends BaseHibernateDaoSupport<OperationLog, String> {

    @Override
    public List<OperationLog> findByModel(OperationLog model, Order order, Pager pager) {
    	
    	model = model == null ? new OperationLog() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from OperationLog c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");

		if(ValidatorUtils.isEmpty(model.getStatus())){
			model.setStatus(RecordStatus.AVAILABLE);
		}
		
		hql.append(" and c.clientName is not null ");
		
		if(ValidatorUtils.isNotEmpty(model.getOperationContent())){
			/*if(ValidatorUtils.isEquals(OperationContent.AMMETER.name(), model.getOperationContent().name())){
				//查询所有电表日志
				hql.append(" and c.operationContent in").append(OperationContent.getAmmeterOptTypeHQLCondition());
			}else if(ValidatorUtils.isEquals(OperationContent.DOORLOCK.name(), model.getOperationContent().name())){
				//查询所有门锁日志
				hql.append(" and c.operationContent in").append(OperationContent.getDoorLockOptTypeHQLCondition());
			}else{
				//查询相应的操作日志
				hql.append(" and c.operationContent=:operationContent ");
			}*/
			hql.append(" and c.operationContent=:operationContent ");
		}
		
		//查询具体分类下的所有操作日志(电表  或者 门锁)
		if(ValidatorUtils.isNotEmpty(model.getOperationCategory())){
			hql.append(" and c.operationCategory=:operationCategory ");
		}
		//查询某个电表的日志
		if(ValidatorUtils.isNotEmpty(model.getAmmeter())
				&& ValidatorUtils.isNotEmpty(model.getAmmeter().getId())){
			hql.append(" and c.ammeter=:ammeter ");
		}
		//查询某个门锁的日志
		if(ValidatorUtils.isNotEmpty(model.getDoorLock())
				&& ValidatorUtils.isNotEmpty(model.getDoorLock().getId())){
			hql.append(" and c.doorLock=:doorLock ");
		}
		
		hql.append(!ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
    }
}
