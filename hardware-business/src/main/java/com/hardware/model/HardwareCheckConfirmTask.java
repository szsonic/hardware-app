package com.hardware.model;

import com.hardware.enums.HardwareStatusCheckOrderStatus;
import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "hardware_check_confirm_task", uniqueConstraints = {@UniqueConstraint(columnNames={"installOrderId", "type"})})
@org.hibernate.annotations.Table(appliesTo = "hardware_check_confirm_task", comment = "硬件信息监测任务")
public class HardwareCheckConfirmTask extends BaseModel{
    private static final long serialVersionUID = 1L;
    
	//用户信息
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	//设备类型 0代表门锁  1代表电表
	@Column(columnDefinition = "varchar(8) comment '设备类型'")
	private String type;
	
	/*//设备类型 0未确认安装  1确认安装
	@Column(columnDefinition = "varchar(8) comment '硬件安装状态' default '0'")
	private String checkStatus = "0";*/
	
	//安装订单, 一个订单可能包含同事包含门锁和电表
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "installOrderId")
	private HardwareInstallOrder installOrder;
	
	/**
	 * 监工状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) default 'REVIEW' comment '监工订单状态'")
	private HardwareStatusCheckOrderStatus hardwareStatusCheckOrderStatus;
	
	
	//描述信息
	@Column(columnDefinition = "varchar(64) comment '描述信息'")
	private String des;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HardwareInstallOrder getInstallOrder() {
		return installOrder;
	}

	public void setInstallOrder(HardwareInstallOrder installOrder) {
		this.installOrder = installOrder;
	}

	/*public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}*/

	public HardwareStatusCheckOrderStatus getHardwareStatusCheckOrderStatus() {
		return hardwareStatusCheckOrderStatus;
	}

	public void setHardwareStatusCheckOrderStatus(HardwareStatusCheckOrderStatus hardwareStatusCheckOrderStatus) {
		this.hardwareStatusCheckOrderStatus = hardwareStatusCheckOrderStatus;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
