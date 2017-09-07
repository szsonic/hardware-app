package com.hardware.model;

import com.hardware.enums.NotifyType;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hardware_online_log")
@org.hibernate.annotations.Table(appliesTo = "hardware_online_log", comment = "硬件在线日志表")
public class HardwareOnlineLog extends BaseModel {
	private static final long serialVersionUID = 1L;

	private String wifiOnlineStatus;

	/**
	 * 所属电表信息（ammeterId非必须）
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH },fetch = FetchType.LAZY)
	@JoinColumn(name = "ammeterId")
	private Ammeter ammeter;
	
	
	/**
	 * 所属门锁信息（ammeterId非必须）
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "doorLockId")
	private DoorLock doorLock;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '离线时间'")
	private Date offlineTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '再次上线时间'")
	private Date onlineTime;

	@Column(columnDefinition = "varchar(8)  comment '离线前剩余电量'")
	private String electricityLeftWhenOffline;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) comment '通知类型'")
	private NotifyType notifyType;


	public String getWifiOnlineStatus() {
		return wifiOnlineStatus;
	}


	public void setWifiOnlineStatus(String wifiOnlineStatus) {
		this.wifiOnlineStatus = wifiOnlineStatus;
	}


	public Ammeter getAmmeter() {
		return ammeter;
	}


	public void setAmmeter(Ammeter ammeter) {
		this.ammeter = ammeter;
	}


	public DoorLock getDoorLock() {
		return doorLock;
	}


	public void setDoorLock(DoorLock doorLock) {
		this.doorLock = doorLock;
	}


	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getElectricityLeftWhenOffline() {
		return electricityLeftWhenOffline;
	}

	public void setElectricityLeftWhenOffline(String electricityLeftWhenOffline) {
		this.electricityLeftWhenOffline = electricityLeftWhenOffline;
	}

	public NotifyType getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(NotifyType notifyType) {
		this.notifyType = notifyType;
	}
}
