package com.hardware.model;

import com.hardware.aop.enums.OperationCategory;
import com.hardware.aop.enums.OperationContent;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 门锁模型
 * 
 * @author yinjiawei
 * 
 */
@Entity
@Table(name = "operation_log")
@org.hibernate.annotations.Table(appliesTo = "operation_log", comment = "操作日志")
public class OperationLog extends BaseModel {
	private static final long serialVersionUID = 1L;


	@Column(columnDefinition = "varchar(32) comment '请求ip'")
	private String ip;

	@Column(name = "client_name",columnDefinition = "varchar(32) comment '客户端名称'")
	private String clientName;

	@Column(name = "operation_time",columnDefinition = "datetime comment '操作时间'")
	private Date operationTime;


	@Column(name = "method_name",columnDefinition = "varchar(200) comment '方法名'")
	private String methodName;

	@Column(name = "log",columnDefinition = "varchar(500) comment '日志信息'")
	private String log;

	@Enumerated(EnumType.STRING)
	@Column(name = "operation_type",columnDefinition = "varchar(50) comment '操作类型'")
	private OperationContent operationContent;

	@Enumerated(EnumType.STRING)
	@Column(name = "operation_category",columnDefinition = "varchar(50) comment '操作所属类别'")
	private OperationCategory operationCategory;

	/**
	 * 门锁
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "doorLockId")
	private DoorLock doorLock;

	/**
	 * 电表
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ammeterId")
	private Ammeter ammeter;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public DoorLock getDoorLock() {
		return doorLock;
	}

	public void setDoorLock(DoorLock doorLock) {
		this.doorLock = doorLock;
	}

	public Ammeter getAmmeter() {
		return ammeter;
	}

	public void setAmmeter(Ammeter ammeter) {
		this.ammeter = ammeter;
	}

	public OperationContent getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(OperationContent operationContent) {
		this.operationContent = operationContent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}


	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public OperationCategory getOperationCategory() {
		return operationCategory;
	}

	public void setOperationCategory(OperationCategory operationCategory) {
		this.operationCategory = operationCategory;
	}
}
