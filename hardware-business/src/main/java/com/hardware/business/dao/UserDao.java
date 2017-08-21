package com.hardware.business.dao;

import com.hardware.business.model.RolesUsers;
import com.hardware.business.model.User;
import org.apache.commons.lang.time.DateFormatUtils;
import com.utils.ValidatorUtils;
import org.iframework.support.domain.order.Order;
import org.iframework.support.domain.pager.Pager;
import com.support.BaseHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据访问实现类
 * 
 * @author shenpeng
 * 
 */
@Repository("userDao")
public class UserDao extends BaseHibernateDaoSupport<User, String> {

	@Override
	public List<User> findByModel(User model, Order order, Pager pager) {
		model = model == null ? new User() : model;
		StringBuilder hql = new StringBuilder();
		hql.append("from User c where 1=1");
		hql.append(ValidatorUtils.isNotEmpty(model.getId()) ? " and c.id=:id " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getLoginName()) ? " and c.loginName=:loginName " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getMobile()) ? " and c.mobile=:mobile " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getEmail()) ? " and c.email=:email " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getStatus()) ? " and c.status=:status " : "");
		hql.append(ValidatorUtils.isNotEmpty(model.getUserName()) ? " and c.userName like '%" + model.getUserName() + "%' " : "");

		hql.append(ValidatorUtils.isNotEmpty(model.getDateStart()) && ValidatorUtils.isNotEmpty(model.getDateEnd()) ? " and (c.createTime between '"
		    + DateFormatUtils.format(model.getDateStart(), "yyyy-MM-dd HH:mm:ss") + "' and '" + DateFormatUtils.format(model.getDateEnd(), "yyyy-MM-dd HH:mm:ss") + "') " : "");
		hql.append(order != null ? order.toString() : "");
		return this.findByQueryString(hql.toString(), model, pager);
	}

	public User findUserByLoginNameAndLoginPwd(String loginName, String loginPwd) {
		List<User> users = this.queryHQL("from User a where a.status='AVAILABLE' and a.loginName='" + loginName + "' and a.loginPwd='" + loginPwd + "'");
		return users != null && users.size() > 0 ? users.get(0) : null;
	}

	public boolean isExistsLoginName(String loginName) {
		List<User> users = this.queryHQL("from User a where a.status='AVAILABLE' and a.loginName='" + loginName + "'");
		return users != null && users.size() > 0 ? true : false;
	}

	public int delete(String id) {
		Integer result = this.executeHQL("delete User a where a.status='AVAILABLE' and a.id='" + id + "'");
		return result;
	}

	public Integer addUserRole(RolesUsers model) {
		return this.executeSQL("INSERT INTO roles_users (roleId,userId) VALUES ( '" + model.getRoleId() + "','" + model.getUserId() + "')");
	}
	
	public User getUserByLoginName(String loginName) {
		List<User> users = this.queryHQL("from User a where a.status='AVAILABLE' and a.loginName='" + loginName + "'");
		if(users!=null&&users.size()>0)
		{
			return users.get(0);
		}
		return null;
	}

}
