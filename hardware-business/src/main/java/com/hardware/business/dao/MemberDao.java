package com.hardware.business.dao;

import com.hardware.business.model.Member;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户类
 * 
 * @author zhongqi
 * 
 */
@Repository("memberDao")
public class MemberDao extends BaseHibernateDaoSupport<Member, String> {

	@Override
	public List<Member> findByModel(Member model, Order order, Pager pager) {
		model = model == null ? new Member() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from Member c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getName()) ? " and c.name=:name " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getAccountOpenId()) ? " and c.accountOpenId=:accountOpenId " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getMobile()) ? " and c.mobile=:mobile " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getIdCard()) ? " and c.idCard=:idCard " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public Member getByOpenId(String openId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Member m where 1=1");
		hql.append(" and m.accountOpenId=").append("'" + openId + "'");
		hql.append(" and m.status='AVAILABLE'");
		List<Member> list = this.queryHQL(hql.toString());
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 手机号查用户
	 * 
	 * @author fanjunjian
	 * @param mobile
	 * @return
	 */
	public Member getByMobile(String mobile) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Member m where 1=1");
		hql.append(" and m.mobile=").append("'" + mobile + "'");
		hql.append(" and m.status='AVAILABLE'");
		List<Member> list = this.queryHQL(hql.toString());
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	public Member getByName(String name) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Member m where 1=1");
		hql.append(" and m.name=").append("'" + name + "'");
		hql.append(" and m.status='AVAILABLE'");
		List<Member> list = this.queryHQL(hql.toString());
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	public List<Member> findByNameMobile(String name, String mobile) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Member m where 1=1");
		hql.append(" and m.name=").append("'" + name + "'");
		hql.append(" and m.mobile=").append("'" + mobile + "'");
		hql.append(" and m.status='AVAILABLE'");
		List<Member> list = this.queryHQL(hql.toString());
		return list;
	}

	public List<Member> findByName(String name) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Member m where 1=1");
		hql.append(" and m.name=").append("'" + name + "'");
		hql.append(" and m.status='AVAILABLE'");
		List<Member> list = this.queryHQL(hql.toString());
		return list;
	}

	public Integer saveProjectsMembers(String projectId, String memberId) {
		Integer result = this.executeSQL("insert into projects_members (projectId,memberId) values ( '"
		    + projectId + "','" + memberId + "')");
		return result;
	}

}
