package com.hardware.model;

import com.hardware.enums.HardwareStatus;
import com.hardware.enums.HardwareType;
import com.support.model.base.BaseModel;

import javax.persistence.*;

/**
 * 硬件设备信息模型
 * 
 * @author yinjiawei
 * 
 */
@Entity
@Table(name = "hardware")
@org.hibernate.annotations.Table(appliesTo = "hardware", comment = "硬件设备信息模型")
public class Hardware extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 电表所属硬件
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ammeterId")
	private Ammeter ammeter;
	
	/**
	 * 網絡卡所属硬件
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "networkCardId")
	private NetworkCard networkCard;

	/**
	 * 流量卡所属硬件
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "flowCardId")
	private FlowCard flowCard;

	/**
	 * 路由器所属硬件
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "routerId")
	private Router router;
	
	/**
	 * 门锁所属硬件
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "doorLockId")
	private DoorLock doorLock;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "houseId")
	private House house;

	/**
	 * 硬件设备类型 1、门锁 2、电表 3、其它
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) comment '硬件设备类型'")
	private HardwareType type;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) comment '硬件设备状态'")
	private HardwareStatus hardwareStatus;

	public HardwareStatus getHardwareStatus() {
		return hardwareStatus;
	}

	public void setHardwareStatus(HardwareStatus hardwareStatus) {
		this.hardwareStatus = hardwareStatus;
	}

	public HardwareType getType() {
		return type;
	}

	public void setType(HardwareType type) {
		this.type = type;
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

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public NetworkCard getNetworkCard() {
		return networkCard;
	}

	public void setNetworkCard(NetworkCard networkCard) {
		this.networkCard = networkCard;
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public FlowCard getFlowCard() {
		return flowCard;
	}

	public void setFlowCard(FlowCard flowCard) {
		this.flowCard = flowCard;
	}
}
