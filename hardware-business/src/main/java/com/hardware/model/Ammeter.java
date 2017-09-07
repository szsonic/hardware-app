package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 电表模型
 * 
 * @author yinjiawei
 * 
 */
@Entity
@Table(name = "ammeter",uniqueConstraints = {@UniqueConstraint(columnNames={"devId", "version"})})
@org.hibernate.annotations.Table(appliesTo = "ammeter", comment = "电表")
public class Ammeter extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 电表的devId
	 */
	@Column(columnDefinition = "varchar(128) comment '电表的devId'")
	private String devId;

	/**
	 * 用于线下同步设备devId,线上同步不涉及
	 */
	@Column(columnDefinition = "varchar(128) comment '用于线下同步设备devId,线上同步不涉及'", unique = true)
	private String offLineSynCode;

	/**
	 * 电表计费单价，多少钱一度电
	 */
	@Column(columnDefinition = "float(11) comment '电表计费单价'", scale = 2)
	private Float electricityPrice;

	/**
	 * 电表的付费模式,0:后付费；1：预付费
	 */
	@Column(columnDefinition = "varchar(8) default 1 comment '电表的付费模式'")
	private String payMod;

	/**
	 * 电表的剩余电量
	 */
	@Column(columnDefinition = "varchar(8)  comment '电表的剩余电量'")
	private String electricityLeftValue;

	/**
	 * 在线状态 on：在线 off：离线
	 */
	@Column(columnDefinition = "varchar(32) comment '在线状态'")
	private String wifiOnlineStatus;

	/**
	 * 电闸状态 on：在线 off：离线
	 */
	@Column(columnDefinition = "varchar(32) comment '在线状态'")
	private String electricDoorSwitchStatus;

	/**
	 * 设备的状态 0:入住 1：未入住
	 */
	@Column(columnDefinition = "varchar(16) default '1' comment '设备的入住状态'")
	private String settledStatus;

	/**
	 * 对应硬件记录
	 */
	@OneToOne(mappedBy = "ammeter", fetch = FetchType.LAZY)
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
	 * 该电表所拥有的wifi在线日志信息
	 */
	@OneToMany(mappedBy = "ammeter", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<HardwareOnlineLog> hardwareOnlineLogList;

	/**
	 * 该电表所拥有的电量日志信息
	 */
	@OneToMany(mappedBy = "ammeter", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<HardwarePowerLog> hardwarePowerLogList;

	/**
	 * 安装的时间
	 */
	@Column(columnDefinition = "datetime comment '安装的时间'")
	private Date installTime;
	
	@javax.persistence.Transient
	private Boolean devIdExists;
	
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

	public Boolean getDevIdExists() {
		return devIdExists;
	}

	public void setDevIdExists(Boolean devIdExists) {
		this.devIdExists = devIdExists;
	}
	
	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public Float getElectricityPrice() {
		return electricityPrice;
	}

	public void setElectricityPrice(Float electricityPrice) {
		this.electricityPrice = electricityPrice;
	}

	public String getWifiOnlineStatus() {
		return wifiOnlineStatus;
	}

	public void setWifiOnlineStatus(String wifiOnlineStatus) {
		this.wifiOnlineStatus = wifiOnlineStatus;
	}

	public String getElectricDoorSwitchStatus() {
		return electricDoorSwitchStatus;
	}

	public void setElectricDoorSwitchStatus(String electricDoorSwitchStatus) {
		this.electricDoorSwitchStatus = electricDoorSwitchStatus;
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

	public String getPayMod() {
		return payMod;
	}

	public void setPayMod(String payMod) {
		this.payMod = payMod;
	}

	public String getElectricityLeftValue() {
		return electricityLeftValue;
	}

	public void setElectricityLeftValue(String electricityLeftValue) {
		this.electricityLeftValue = electricityLeftValue;
	}

	public String getSettledStatus() {
		return settledStatus;
	}

	public void setSettledStatus(String settledStatus) {
		this.settledStatus = settledStatus;
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

	public List<HardwareOnlineLog> getHardwareOnlineLogList() {
		return hardwareOnlineLogList;
	}

	public void setHardwareOnlineLogList(List<HardwareOnlineLog> hardwareOnlineLogList) {
		this.hardwareOnlineLogList = hardwareOnlineLogList;
	}

	public List<HardwarePowerLog> getHardwarePowerLogList() {
		return hardwarePowerLogList;
	}

	public void setHardwarePowerLogList(List<HardwarePowerLog> hardwarePowerLogList) {
		this.hardwarePowerLogList = hardwarePowerLogList;
	}
	
}
