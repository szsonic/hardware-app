package com.hardware.model;

import com.hardware.enums.FrozenStatus;
import com.hardware.enums.PwdStatus;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户门锁密码
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "member_lock_pass",uniqueConstraints = {@UniqueConstraint(columnNames={"doorLockId","memberId","passIndex","version"})})
@org.hibernate.annotations.Table(appliesTo = "member_lock_pass", comment = "用户门锁密码")
public class MemberLockPass extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 所属门锁信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "doorLockId")
	private DoorLock doorLock;

	/**
	 * 所属用户信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "memberId")
	private Member member;

	/**
	 * 密码类型： 1  2 次数密码 3动态密码
	 */
	@Column(columnDefinition = "varchar(8) comment '密码类型：1普通密码 2 次数密码 3动态密码'")
	private String passType;

	/**
	 * 用户门锁密码
	 */
	@Column(columnDefinition = "varchar(128) comment '用户门锁密码'")
	private String pass;

	/**
	 * 用户门锁密码对应云柚的位数
	 */
	@Column(columnDefinition = "int comment '用户门锁密码对应云柚的位数'")
	private Integer passIndex;

	/**
	 * 
	 */
	@Column(columnDefinition = "varchar(512) comment ''")
	private String remark;

	/**
	 * 密码冻结状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(20) comment '冻结状态'")
	private FrozenStatus frozenStatus;

	/**
	 * 密码回调状态(调用第三方返回是否成功)
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(20) comment '同步状态'")
	private PwdStatus pwdStatus;

	/**
	 * 密码回调超时时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '密码回调超时时间'")
	private Date maxCallBackTime;


	/**
	 * 版本号
	 */
	@Column(columnDefinition = "varchar(50) comment '版本号' default '1'")
	private String version;
	
	
	public String getVersion() {
		return version;
	}

	/**
	 * 密码有效期开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '密码有效期开始时间'")
	private Date effectiveStart;

	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * 密码有效期结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '密码有效期结束时间'")
	private Date effectiveEnd;

	public Date getEffectiveStart() {
		return effectiveStart;
	}
	
	@Column(columnDefinition = "varchar(16) comment '设备商提供的密码ID'")
	private String passwordId;

	public void setEffectiveStart(Date effectiveStart) {
		this.effectiveStart = effectiveStart;
	}

	public Date getEffectiveEnd() {
		return effectiveEnd;
	}

	public void setEffectiveEnd(Date effectiveEnd) {
		this.effectiveEnd = effectiveEnd;
	}

	public Date getMaxCallBackTime() {
		return maxCallBackTime;
	}

	public void setMaxCallBackTime(Date maxCallBackTime) {
		this.maxCallBackTime = maxCallBackTime;
	}

	
	
	public PwdStatus getPwdStatus() {
		return pwdStatus;
	}

	public void setPwdStatus(PwdStatus pwdStatus) {
		this.pwdStatus = pwdStatus;
	}

	public FrozenStatus getFrozenStatus() {
		return frozenStatus;
	}

	public void setFrozenStatus(FrozenStatus frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPassIndex() {
		return passIndex;
	}

	public void setPassIndex(Integer passIndex) {
		this.passIndex = passIndex;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public DoorLock getDoorLock() {
		return doorLock;
	}

	public void setDoorLock(DoorLock doorLock) {
		this.doorLock = doorLock;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(String passwordId) {
		this.passwordId = passwordId;
	}
	
}
