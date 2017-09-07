package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 硬件用户模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "member")
@org.hibernate.annotations.Table(appliesTo = "member", comment = "硬件用户模型")
public class Member extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户(租客)的openId
	 */
	@Column(columnDefinition = "varchar(32) comment '用户的openId'", unique = true)
	private String accountOpenId;

	/**
	 * 用户名字
	 */
	@Column(columnDefinition = "varchar(64) comment '用户名字'")
	private String name;

	/**
	 * 手机号
	 */
	@Column(columnDefinition = "varchar(64) comment '手机号'")
	private String mobile;

	/**
	 * 用户身份账号
	 */
	@Column(columnDefinition = "varchar(128) comment '用户身份账号'", unique = true)
	private String idCard;

	/**
	 * 用户来源
	 */
	@Column(columnDefinition = "varchar(128) comment '用户来源'")
	private String memberSource;

	/**
	 * 用户所属项目集合
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "projects_members", joinColumns = { @JoinColumn(name = "memberId") }, inverseJoinColumns = { @JoinColumn(name = "projectId") })
	private List<Project> projects;

	/**
	 * 用户所属房源
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "houses_members", joinColumns = { @JoinColumn(name = "memberId") }, inverseJoinColumns = { @JoinColumn(name = "houseId") })
	private List<House> houses;

	/**
	 * 该用户所拥有的密码
	 */
	@OneToMany(mappedBy = "member", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
	    CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<MemberLockPass> memberLockPassList;

	/**
	 * 该用户所拥有的流量卡
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flowCardId", columnDefinition = "varchar(32) comment '流量卡ID'")
	private FlowCard flowCard;

	/**
	 * 该用户所拥有的租房退房记录
	 */
	@OneToMany(mappedBy = "member", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
	    CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<RentHouseLog> rentHouseLogList;

	public List<MemberLockPass> getMemberLockPassList() {
		return memberLockPassList;
	}

	public void setMemberLockPassList(List<MemberLockPass> memberLockPassList) {
		this.memberLockPassList = memberLockPassList;
	}

	public String getAccountOpenId() {
		return accountOpenId;
	}
	
	public List<RentHouseLog> getRentHouseLogList() {
		return rentHouseLogList;
	}

	public void setRentHouseLogList(List<RentHouseLog> rentHouseLogList) {
		this.rentHouseLogList = rentHouseLogList;
	}

	public void setAccountOpenId(String accountOpenId) {
		this.accountOpenId = accountOpenId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMemberSource() {
		return memberSource;
	}

	public void setMemberSource(String memberSource) {
		this.memberSource = memberSource;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public FlowCard getFlowCard() {
		return flowCard;
	}

	public void setFlowCard(FlowCard flowCard) {
		this.flowCard = flowCard;
	}
}
