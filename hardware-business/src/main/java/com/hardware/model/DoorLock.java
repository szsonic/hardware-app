package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 门锁模型
 * 
 * @author yinjiawei
 * 
 */
@Entity
@Table(name = "doorlock",uniqueConstraints = {@UniqueConstraint(columnNames={"devId", "version"})})
@org.hibernate.annotations.Table(appliesTo = "doorlock", comment = "门锁")
public class DoorLock extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备唯一ID
	 */
	@Column(columnDefinition = "varchar(128) comment '设备唯一ID'", unique = true)
	private String devId;

	/**
	 * 用于线下同步设备devId,线上同步不涉及
	 */
	@Column(columnDefinition = "varchar(128) comment '用于线下同步设备devId,线上同步不涉及'")
	private String offLineSynCode;

	/**
	 * 门锁电量
	 */
	@Column(columnDefinition = "int(3) default 0 comment '门锁电量'")
	private int powerLeftValue;
	
	/**
	 * 在线状态 on：在线 off：离线
	 */
	@Column(columnDefinition = "varchar(32) comment '在线状态'")
	private String wifiOnlineStatus;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "houseId")
	private House house;

	/**
	 * 对应硬件记录
	 */
	@OneToOne(mappedBy = "doorLock", fetch = FetchType.LAZY)
	private Hardware hardware;

	/**
	 * 所属供应商产品
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;

	/**
	 * 该门锁所拥有的wifi在线日志信息
	 */
	@OneToMany(mappedBy = "doorLock", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<HardwareOnlineLog> hardwareOnlineLogList;

	/**
	 * 该门锁所拥有的电量日志信息
	 */
	@OneToMany(mappedBy = "doorLock", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<HardwarePowerLog> hardwarePowerLogList;

	/**
	 * 该门锁所拥有的密码
	 */
	@OneToMany(mappedBy = "doorLock", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<MemberLockPass> memberLockPassList;

	/**
	 * 该门锁所拥有的开门日志信息
	 */
	@OneToMany(mappedBy = "doorLock", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<DoorLockOpenLog> doorLockOpenLogList;

	/**
	 * sn回来的时间
	 */
	@Column(columnDefinition = "datetime comment 'sn回来的时间'", updatable = false)
	private Date installTime;
	
	/**
	 * 版本号
	 */
	@Column(columnDefinition = "varchar(50) comment '版本号' default '1'")
	private String version;
	
	@javax.persistence.Transient
	private Boolean devIdExists;

	@javax.persistence.Transient
	private String powerLeftLow;
	
	
	
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

	public List<DoorLockOpenLog> getDoorLockOpenLogList() {
		return doorLockOpenLogList;
	}

	public void setDoorLockOpenLogList(List<DoorLockOpenLog> doorLockOpenLogList) {
		this.doorLockOpenLogList = doorLockOpenLogList;
	}

	public List<MemberLockPass> getMemberLockPassList() {
		return memberLockPassList;
	}

	public void setMemberLockPassList(List<MemberLockPass> memberLockPassList) {
		this.memberLockPassList = memberLockPassList;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getOffLineSynCode() {
		return offLineSynCode;
	}

	public void setOffLineSynCode(String offLineSynCode) {
		this.offLineSynCode = offLineSynCode;
	}

	public int getPowerLeftValue() {
		return powerLeftValue;
	}

	public void setPowerLeftValue(int powerLeftValue) {
		this.powerLeftValue = powerLeftValue;
	}

	public String getWifiOnlineStatus() {
		return wifiOnlineStatus;
	}

	public void setWifiOnlineStatus(String wifiOnlineStatus) {
		this.wifiOnlineStatus = wifiOnlineStatus;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Hardware getHardware() {
		return hardware;
	}

	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}

	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}

	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
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

	public String getPowerLeftLow() {
		return powerLeftLow;
	}

	public void setPowerLeftLow(String powerLeftLow) {
		this.powerLeftLow = powerLeftLow;
	}

	
	
	
	
}
