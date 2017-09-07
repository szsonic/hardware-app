package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 门锁模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "doorlock_open_log")
@org.hibernate.annotations.Table(appliesTo = "doorlock_open_log", comment = "门锁开门记录")
public class DoorLockOpenLog extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属门锁信息
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "doorLockId")
	private DoorLock doorLock;
	
	/**
	 * 开门时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '开门时间'")
	private Date openLockTime;

	/**
	 * 开门类型  接口开门 1，密码开门 2 ，第三方平台开门 3 ,一次性密码开门  4, 动态密码  5, 机械钥匙开门   6 ，刷卡开门  7  ,  其他  8 。
	 */
	@Column(columnDefinition = "varchar(32) comment '开门类型'")
	private String type;

	/**
	 * 开门手机号
	 */
	@Column(columnDefinition = "varchar(32) comment '开门手机号'")
	private String openDoorMobile;
	/**
	 * 
	 * @return
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "memberLockPassId")
	private MemberLockPass memberLockPass;
	
	public MemberLockPass getMemberLockPass() {
		return memberLockPass;
	}

	public void setMemberLockPass(MemberLockPass memberLockPass) {
		this.memberLockPass = memberLockPass;
	}

	public Date getOpenLockTime() {
		return openLockTime;
	}

	public void setOpenLockTime(Date openLockTime) {
		this.openLockTime = openLockTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DoorLock getDoorLock() {
		return doorLock;
	}

	public void setDoorLock(DoorLock doorLock) {
		this.doorLock = doorLock;
	}

	public String getOpenDoorMobile() {
		return openDoorMobile;
	}

	public void setOpenDoorMobile(String openDoorMobile) {
		this.openDoorMobile = openDoorMobile;
	}

}
