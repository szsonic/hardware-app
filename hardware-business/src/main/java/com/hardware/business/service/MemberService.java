package com.hardware.business.service;

import com.hardware.business.model.Member;
import org.iframework.support.spring.hibernate.service.IBaseServiceSupport;

import java.util.List;

/**
 * 【盈家】会员用户基本信息服务接口<br>
 * 
 * @author fanjunjian
 * 
 */
public interface MemberService extends IBaseServiceSupport<Member, String> {

	/**
	 * 根据ids主键集合获取用户集合
	 * 
	 * @param ids
	 *          主键集合
	 * @return List<Member> 用户集合
	 */
	public List<Member> findMemberListByIds(String[] ids);

	/**
	 * 根据id主键集合获取用户 单个
	 * 
	 * @param id主键
	 * 
	 * @return Member 用户
	 */
	public Member findMemberListById(String ids);

	/**
	 * 使用openId查询信息
	 * 
	 * @param accountOpenId
	 *          openId
	 * 
	 * @return List<Member>
	 */
	public Member getByOpenId(String accountOpenId);

	/**
	 * 使用手机号查询信息
	 * 
	 * @param mobile
	 * 
	 * @return List<Member>
	 */
	public Member getByMobile(String mobile);

	/**
	 * 使用姓名查询信息
	 * 
	 * @param mobile
	 * 
	 * @return List<Member>
	 */
	public Member getByName(String name);

	public List<Member> findByNameMobile(String name, String mobile);

	public List<Member> findByName(String name);

	/**
	 * 创建用户
	 * 
	 * @param u
	 * @param member
	 * @return
	 */
	public String createMember(String projectId, String landlordHouseCode, Member member);
}
