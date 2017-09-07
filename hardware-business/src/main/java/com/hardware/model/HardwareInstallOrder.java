package com.hardware.model;


import com.hardware.enums.NetWorkStatus;
import com.hardware.enums.RentType;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hardware_install_order")
@org.hibernate.annotations.Table(appliesTo = "hardware_install_order", comment = "硬件设备安裝訂單表")
public class HardwareInstallOrder extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "houseId")
	private House house;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "projectId")
	private Project project;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ammeterInstallOrderId")
	private AmmeterInstallOrder ammeterInstallOrder;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "doorLockInstallOrderId")
	private DoorLockInstallOrder doorLockInstallOrder;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "routerInstallOrderId")
	private RouterInstallOrder routerInstallOrder;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "flowCardInstallOrderId")
	private FlowCardInstallOrder flowCardInstallOrder;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '申请安装时间'", updatable = false)
	private Date applyInstallTime;

	//****************************以下属性仅用作查询条件***************************

	@Transient
	private String city;//城市

	@Transient
	private String dist;//区

	@Transient
	private String province;//省

	@Transient
	private String districtName;//小区名

	@Transient
	private String roomNo;//房间


	@Transient
	private String street;//街道

	@Transient
	private String unit;//单元

	@Transient
	private String type;//类型

	@Transient
	private String block;//楼栋

	@Transient
	private NetWorkStatus netWorkStatus;

	//****************************以上属性仅用作查询条件***************************


	public String getCity() {
		HouseStreet houseStreet=getHouseStreet();
		if(houseStreet!=null){
			return houseStreet.getCityName();
		}
		return null;
	}

	private HouseStreet getHouseStreet(){
		if(this.getHouse()!=null){
			HouseUnit houseUnit=this.getHouse().getHouseUnit();
			if(houseUnit!=null){
				HouseBlock houseBlock= houseUnit.getHouseBlock();
				if(houseBlock!=null){
					HouseDistrict houseDistrict=houseBlock.getHouseDistrict();
					if(houseDistrict!=null){
						return houseDistrict.getHouseStreet();
					}
				}
			}
		}
		return null;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDist() {
		HouseStreet houseStreet=getHouseStreet();
		if(houseStreet!=null){
			return houseStreet.getDistName();
		}
		return null;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getProvince() {
		HouseStreet houseStreet=getHouseStreet();
		if(houseStreet!=null){
			return houseStreet.getProvinceName();
		}
		return null;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrictName() {
		if(this.getHouse()!=null){
			HouseUnit houseUnit=this.getHouse().getHouseUnit();
			if(houseUnit!=null){
				HouseBlock houseBlock= houseUnit.getHouseBlock();
				if(houseBlock!=null){
					HouseDistrict houseDistrict=houseBlock.getHouseDistrict();
					if(houseDistrict!=null){
						return houseDistrict.getDistrictName();
					}
				}
			}
		}
		return null;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getRoomNo() {
		if(this.getHouse()!=null){
			return this.getHouse().getRoomNo();
		}
		return null;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getStreet() {
		HouseStreet houseStreet=getHouseStreet();
		if(houseStreet!=null){
			return houseStreet.getStreet();
		}
		return null;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getUnit() {
		if(this.getHouse()!=null){
			HouseUnit houseUnit=this.getHouse().getHouseUnit();
			if(houseUnit!=null){
				return houseUnit.getName();
			}
		}
		return null;

	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getType() {
		if(this.getHouse()!=null){
			RentType rentType=this.getHouse().getRentType();
			if(rentType!=null){
				return rentType.name();
			}
		}
		return null;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlock() {
		if(this.getHouse()!=null){
			HouseUnit houseUnit=this.getHouse().getHouseUnit();
			if(houseUnit!=null){
				HouseBlock houseBlock= houseUnit.getHouseBlock();
				if(houseBlock!=null){
					return houseBlock.getName();
				}
			}
		}
		return null;
	}

	public void setBlock(String block) {
		this.block = block;
	}


	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public AmmeterInstallOrder getAmmeterInstallOrder() {
		return ammeterInstallOrder;
	}

	public void setAmmeterInstallOrder(AmmeterInstallOrder ammeterInstallOrder) {
		this.ammeterInstallOrder = ammeterInstallOrder;
	}

	public DoorLockInstallOrder getDoorLockInstallOrder() {
		return doorLockInstallOrder;
	}

	public void setDoorLockInstallOrder(DoorLockInstallOrder doorLockInstallOrder) {
		this.doorLockInstallOrder = doorLockInstallOrder;
	}

	public RouterInstallOrder getRouterInstallOrder() {
		return routerInstallOrder;
	}

	public void setRouterInstallOrder(RouterInstallOrder routerInstallOrder) {
		this.routerInstallOrder = routerInstallOrder;
	}

	public NetWorkStatus getNetWorkStatus() {
		return netWorkStatus;
	}

	public void setNetWorkStatus(NetWorkStatus netWorkStatus) {
		this.netWorkStatus = netWorkStatus;
	}

	public FlowCardInstallOrder getFlowCardInstallOrder() {
		return flowCardInstallOrder;
	}

	public void setFlowCardInstallOrder(FlowCardInstallOrder flowCardInstallOrder) {
		this.flowCardInstallOrder = flowCardInstallOrder;
	}

	public Date getApplyInstallTime() {
		return applyInstallTime;
	}

	public void setApplyInstallTime(Date applyInstallTime) {
		this.applyInstallTime = applyInstallTime;
	}
}
