package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;


/**
 * 路由器模型
 * 
 * @author yinjiawei
 * 
 */
@Entity
@Table(name = "router")
@org.hibernate.annotations.Table(appliesTo = "router", comment = "路由器")
public class Router extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 路由器的devId
	 */
	@Column(columnDefinition = "varchar(128) comment '路由器的devId'")
	private String devId;

	/**
	 * 用于线下同步设备devId,线上同步不涉及
	 */
	@Column(columnDefinition = "varchar(128) comment '用于线下同步设备devId,线上同步不涉及'", unique = true)
	private String offLineSynCode;

	/**
	 * 在线网络类型 1：4G 2：宽带 3：WIFI
	 */
	@Column(columnDefinition = "varchar(32) comment '在线网络类型 1：4G 2：宽带 3：WIFI'")
	private String onlineNetworkType;

	/**
	 * 在线状态 on：在线 off：离线
	 */
	@Column(columnDefinition = "varchar(32) comment '在线状态'")
	private String onlineStatus;

	/**
	 * 运营商ID
	 */
	@Column(name = "operatorId", columnDefinition = "varchar(16) comment '运营商ID'")
	private String operatorId;

	/**
	 * 运营商名称
	 */
	@Column(name = "operatorName", columnDefinition = "varchar(16) comment '运营商名称'")
	private String operatorName;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(500) remark '备注'")
	private String remark;

	

	/**
	 * 对应硬件记录
	 */
	@OneToOne(mappedBy = "router", fetch = FetchType.LAZY)
	private Hardware hardware;


	/**
	 * 所属供应商产品
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "houseId")
	private House house;

	
	

	/**
	 * 安装的时间
	 */
	@Column(columnDefinition = "datetime comment '安装的时间'")
	private Date installTime;
	

	/**
	 * 版本号 有效数据永远为1
	 */
	@Column(columnDefinition = "varchar(50) comment '版本号' default '1'")
	private String version;
	
	
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	
	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getOnlineNetworkType() {
		return onlineNetworkType;
	}

	public void setOnlineNetworkType(String onlineNetworkType) {
		this.onlineNetworkType = onlineNetworkType;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}

	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}

	public Hardware getHardware() {
		return hardware;
	}

	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}




	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	public String getOffLineSynCode() {
		return offLineSynCode;
	}

	public void setOffLineSynCode(String offLineSynCode) {
		this.offLineSynCode = offLineSynCode;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
