package com.hardware.business.dao;

import com.hardware.business.model.MemberLockPass;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.enums.RecordStatus;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户门锁密码
 * 
 * @author zq
 * 
 */
@Repository("memberLockPassDao")
public class MemberLockPassDao extends BaseHibernateDaoSupport<MemberLockPass, String> {

	@Override
	public List<MemberLockPass> findByModel(MemberLockPass model, Order order, Pager pager) {
		model = model == null ? new MemberLockPass() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from MemberLockPass c where 1=1");
		if(model.getDoorLock()!=null){
			hql.append(" and c.doorLock.id='"+model.getDoorLock().getId()+"'");
		}
		if(model.getPassIndex()!=null){
			hql.append(" and c.passIndex=:passIndex ");
		}
		if(model.getFrozenStatus()!=null){
			hql.append(" and c.frozenStatus=:frozenStatus ");
		}
		if(model.getPwdStatus()!=null){
			hql.append(" and c.pwdStatus=:pwdStatus ");
		}
		if(model.getMember()!=null){
			hql.append(" and c.member.id = '"+model.getMember().getId()+"'");
		}
		if(model.getStatus()!=null){
			hql.append(" and c.status = '"+ RecordStatus.AVAILABLE+"'");
		}
		
		if(ValidatorUtils.isNotEmpty(model.getPassType())){
			hql.append(" and c.passType = '" + model.getPassType() + "'");
		}
		
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}


	public List<MemberLockPass> validateEffectivePwdExist(MemberLockPass model) {
		model = model == null ? new MemberLockPass() : model;
		StringBuilder hql = new StringBuilder();
		Date now=new Date();
		hql.append("from MemberLockPass c where 1=1");
		if(model.getDoorLock()!=null){
			hql.append(" and c.doorLock.id='"+model.getDoorLock().getId()+"'");
		}
		if(model.getMember()!=null){
			hql.append(" and c.member.id = '"+model.getMember().getId()+"'");
		}
		if(StringUtils.isNotBlank(model.getPassType())){
			hql.append(" and c.passType = '"+model.getPassType()+"'");
		}
		if(ValidatorUtils.isNotEmpty(model.getFrozenStatus())){
			hql.append(" and c.frozenStatus='" + model.getFrozenStatus().name() + "' ");
		}
		hql.append(" and c.status = '"+ RecordStatus.AVAILABLE+"'");
		return this.findByQueryString(hql.toString(), null,null);
	}


//	public List<MemberLockPass> validatePwdExist(MemberLockPass model) {
//		model = model == null ? new MemberLockPass() : model;
//		StringBuilder hql = new StringBuilder();
//		hql.append("from MemberLockPass c where 1=1");
//		if(model.getDoorLock()!=null){
//			hql.append(" and c.doorLock.id='"+model.getDoorLock().getId()+"'");
//		}
//		if(model.getMember()!=null){
//			hql.append(" and c.member.id = '"+model.getMember().getId()+"'");
//		}
//		if(model.getStatus()!=null){
//			hql.append(" and c.status = '"+ RecordStatus.AVAILABLE+"'");
//		}
//		hql.append(" and (c.syncStatus='"+ PwdOpStatus.SUCCESS+"'");
//		hql.append(" or (c.syncStatus='"+ PwdOpStatus.WAIT_CALLBACK+"' and c.maxCallBackTime >'"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"'))");
//		return this.findByQueryString(hql.toString(), null,null);
//	}


//	public Number maxPasswordIndex(DoorLock doorLock) {
//		StringBuilder hql=new StringBuilder();
//		hql.append("SELECT MAX(m.passIndex) FROM MemberLockPass AS m where m.doorLock.id='"+doorLock.getId()+"'");
//		hql.append(" and m.status = '"+ RecordStatus.AVAILABLE+"'");
//		hql.append(" and (m.syncStatus='"+ SyncStatus.SUCCESS+"'");
//		hql.append(" or (m.syncStatus='"+ SyncStatus.WAIT_CALLBACK+"' and m.maxCallBackTime >'"+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+"'))");
//		return this.sumHQL(hql.toString());
//	}

	public List<MemberLockPass> findByOpenIdDevId(String openId, String devId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from MemberLockPass m where 1=1");
		hql.append(" and m.openId=").append("'" + openId + "'");
		hql.append(" and m.devId=").append("'" + devId + "'");
		List<MemberLockPass> list = this.queryHQL(hql.toString());
		return list;
	}

	public MemberLockPass getByMoibleType(String mobile, String devId, String type) {
		StringBuilder hql = new StringBuilder();
		hql.append("from MemberLockPass m where 1=1");
		hql.append(" and m.mobile=").append("'" + mobile + "'");
		hql.append(" and m.devId=").append("'" + devId + "'");
		hql.append(" and m.passType=").append("'" + type + "'");
		List<MemberLockPass> list = this.queryHQL(hql.toString());
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public List<MemberLockPass> findByDevIdAndPasswordId(String passwordId, String devId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from MemberLockPass m where 1=1");
		hql.append(" and m.passwordId=").append("'" + passwordId + "'");
		hql.append(" and m.doorLock.devId=").append("'" + devId + "'");
		List<MemberLockPass> list = this.queryHQL(hql.toString());
		return list;
	}
}
