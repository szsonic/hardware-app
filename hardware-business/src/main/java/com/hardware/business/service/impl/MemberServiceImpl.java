package com.hardware.business.service.impl;

import com.google.common.collect.Lists;
import com.hardware.business.dao.MemberDao;
import com.hardware.business.dao.ProjectDao;
import com.hardware.business.exception.HardwareSdkException;
import com.hardware.business.model.Member;
import com.hardware.business.model.Project;
import com.hardware.business.service.MemberService;
import com.hardware.business.utils.PropertiesUtils;
import net.sf.json.JSONObject;
import org.iframework.commons.utils.http.HttpClientUtils;
import org.iframework.commons.utils.log.Log;
import com.utils.ValidatorUtils;
import com.support.BaseHibernateDaoSupport;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * 会员用户基本信息服务接口实现类<br>
 * 说明：支持方法查阅实现接口
 * 
 * @author fanjunjian
 * 
 */
@Service("memberService")
public class MemberServiceImpl extends BaseServiceSupport<Member, String> implements MemberService {

	@Resource(name = "memberDao")
	private MemberDao memberDao;

	@Resource(name = "projectDao")
	private ProjectDao projectDao;

	@Resource(name = "memberDao")
	@Override
	public void setBaseHibernateDaoSupport(
	    @Qualifier("memberDaoImpl") BaseHibernateDaoSupport<Member, String> baseHibernateDaoSupport) {
		this.baseHibernateDaoSupport = baseHibernateDaoSupport;
	}

	@Override
	public List<Member> findMemberListByIds(String[] ids) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			sBuffer.append("c.id='" + ids[i] + "'");
			if (i < ids.length - 1) {
				sBuffer.append(" or ");
			}
		}
		return this.baseHibernateDaoSupport.queryHQL("SELECT c FROM Member c where " + sBuffer.toString());
	}

	public Member findMemberListById(String id) {
		List<Member> members = this.queryHQL("SELECT o FROM Member o where o.id=" + "'" + id + "'");
		return members != null && members.size() > 0 ? members.get(0) : null;
	}

	@Override
	public Member getByOpenId(String accountOpenId) {
		return ((MemberDao) this.baseHibernateDaoSupport).getByOpenId(accountOpenId);
	}

	@Override
	public Member getByMobile(String mobile) {
		return ((MemberDao) this.baseHibernateDaoSupport).getByMobile(mobile);
	}

	@Override
	public Member getByName(String name) {
		return ((MemberDao) this.baseHibernateDaoSupport).getByName(name);
	}

	@Override
	public String createMember(String projectId, String houseId, Member member) throws HardwareSdkException {
		// ================账号中心验证手机号获取OpenId===========================
		String openId = "";
		/**
		 * 账号中心验证手机号获取OpenId
		 */
		Properties config = PropertiesUtils.getProperties();
		String openIdResult = HttpClientUtils.get(config.getProperty("ACCOUNT_CENTER_PATH") + "/account-service/service/getAccountOpenId.json?mobile=" + member.getMobile());
		Log.i("获取账号中心openId结果：" + openIdResult);
		if (ValidatorUtils.isEmpty(openIdResult)) {
			throw new HardwareSdkException("账户中心手机号验证失败");
		} else {
			JSONObject jo = JSONObject.fromObject(openIdResult);
			String openIdResultStatus = jo.getString("innjia_account_json_response_status");
			if ("success".equals(openIdResultStatus)) {
				openId = jo.getString("accountOpenId");
			} else if ("fail".equals(openIdResultStatus)) {
				String openIdResultErrorMessage = jo.getString("innjia_account_json_error_message");
				throw new HardwareSdkException("账户中心手机号验证失败，" + openIdResultErrorMessage);
			}
		}
		if (ValidatorUtils.isEmpty(openId)) {
			throw new HardwareSdkException("账户中心手机号验证无效");
		}
		// ==================================================================
		Member oneMember = memberDao.getByOpenId(openId);
		if (ValidatorUtils.isNotEmpty(oneMember)) {
			throw new HardwareSdkException("openId已存在", 2);
		}

		member.setAccountOpenId(openId);
		Project project = new Project();
		project.setId(projectId);
		member.setProjects(Lists.newArrayList(project));
		String memberId = this.memberDao.save(member);
		if (ValidatorUtils.isNotEmpty(memberId)) {
			// memberDao.saveProjectsMembers(projectId, memberId);
		} else {
			throw new HardwareSdkException("新建保存操作失败。");
		}
		return memberId;
	}

	@Override
	public List<Member> findByNameMobile(String name, String mobile) {
		return ((MemberDao) this.baseHibernateDaoSupport).findByNameMobile(name, mobile);
	}

	@Override
	public List<Member> findByName(String name) {
		return ((MemberDao) this.baseHibernateDaoSupport).findByName(name);
	}

}
