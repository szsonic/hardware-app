package com.hardware.model;


import com.hardware.enums.HardwareInstallStatus;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hardware_install_task")
@org.hibernate.annotations.Table(appliesTo = "hardware_install_task", comment = "硬件安装派单")
public class HardwareInstallTask extends BaseModel{
    private static final long serialVersionUID = 1L;
    
	//用户信息
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	//安装订单, 一个订单可能对应多个派单（可以多次派单）
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "installOrderId")
	private HardwareInstallOrder installOrder;
	
	/**
	 * 工单状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) default 'CREATED' comment '硬件安装工单状态'")
	private HardwareInstallStatus hardwareInstallStatus;
	
	
	//描述信息
	@Column(columnDefinition = "varchar(64) comment '描述信息'")
	private String des;

	@Column(columnDefinition = "varchar(16) comment '联系方式'")
	private String contactMobile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '申请安装时间'", updatable = false)
	private Date applyInstallTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '安装时间'", updatable = false)
	private Date installTime;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HardwareInstallOrder getInstallOrder() {
		return installOrder;
	}

	public void setInstallOrder(HardwareInstallOrder installOrder) {
		this.installOrder = installOrder;
	}

	public String getDes() {
		return des;
	}

	public HardwareInstallStatus getHardwareInstallStatus() {
		return hardwareInstallStatus;
	}

	public void setHardwareInstallStatus(HardwareInstallStatus hardwareInstallStatus) {
		this.hardwareInstallStatus = hardwareInstallStatus;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Date getApplyInstallTime() {
		return applyInstallTime;
	}

	public void setApplyInstallTime(Date applyInstallTime) {
		this.applyInstallTime = applyInstallTime;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	public void setDes(String des) {
		this.des = des;

	}


}
