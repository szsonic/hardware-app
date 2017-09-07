package com.hardware.model;


import com.hardware.enums.DoorType;
import com.hardware.enums.HouseSource;
import com.hardware.enums.HouseStructureType;
import com.hardware.enums.RentType;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 房源信息模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "house")
@org.hibernate.annotations.Table(appliesTo = "house", comment = "房源信息模型")
public class House extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	

	public House() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 线上房同步第三方编号，线下不涉及
	 */
	@Column(columnDefinition = "varchar(16) comment '线上房同步第三方编号，线下不涉及'", unique = true)
	private String onLineSynCode;

	/**
	 * 室
	 */
	@Column(columnDefinition = "varchar(32) comment '室'")
	private String roomNo;

	/**
	 * 门号
	 */
	@Column(columnDefinition = "varchar(32) comment '门号'")
	private String door;
	
	/**
	 * 房源备注
	 */
	@Column(columnDefinition = "varchar(500) comment '房源备注'")
	private String remark;

	/**
	 * 房源来源
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(30) comment '房源来源'")
	private HouseSource source;

	/**
	 * 第三方房源编号
	 */
	@Column(columnDefinition = "varchar(128) comment '房东端房源编号'", unique = true)
	private String sourceHouseCode;

	/**
	 * 硬件中心系统内部父房源ID
	 */
	@Column(columnDefinition = "varchar(128) comment '硬件中心系统内部父房源ID'")
	private String parentId;

	/**
	 * 房源所属的硬件集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<Hardware> hardwareList;


	
	/**
	 * 房源拥有的安裝申請集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<HardwareInstallOrder> hardwareInstallOrderList;

	/**
	 * 房源拥有的电表集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<Ammeter> ammeterList;
	
	/**
	 * 房源拥有的网卡集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<NetworkCard> networkCardList;
	
	/**
	 * 房源拥有的路由器集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<Router> routerList;

	/**
	 * 房源拥有的 门锁集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<DoorLock> doorLockList;

	/**
	 * 房源拥有的 SIM卡集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<FlowCard> flowCardList;
	
	/**
	 * 房源所属的租房退房记录集合
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<RentHouseLog> rentHouseLogList;


	/**
	 * 房源所属用户
	 */
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "houses_members", joinColumns = { @JoinColumn(name = "houseId") }, inverseJoinColumns = { @JoinColumn(name = "memberId") })
	private List<Member> members;

	
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "houseUnitId")
	private HouseUnit houseUnit;
	
	
	

	/**
	 * 房源同步记录	
	 */
	@OneToMany(mappedBy = "house", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<HouseSyncRecord> houseSyncRecordList;

	/**
	 * 房源类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) comment '租房类型'")
	private RentType rentType;

	/**
	 * 房源門類型
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) comment '房源門類型'")
	private DoorType doorType;// 大門  1  小門   2
	//****************************以下属性仅用作查询条件***************************
	

	@javax.persistence.Transient
	private Boolean parentIdNullFlag;

	@javax.persistence.Transient
	private Boolean houseMemberNullFlag;

	@javax.persistence.Transient
	private String mobile;


	@javax.persistence.Transient
	private String name;


	
	@Transient
	private String block;//楼栋

	@Transient
	private String unit;//单元

	@Transient
	private String street;//街道

	@Transient
	private String provinceId;//省id

	@Transient
	private String dist;
	
	@Transient
	private Boolean ammeterWaitForDone;

	@Transient
	private String cityId;

	@Transient
	private String type;

	@Transient
	private String districtName;

	@Transient
	private String districtId;
	
	@Transient
	private List<String> sourceHouseCodeList;
	
	@Transient
	private String doorLockHardwareInstallStatus;
	
	@Transient
	private String ammeterHardwareInstallStatus;

	@Transient
	private String routerHardwareInstallStatus;

	@Transient
	private String flowCardHardwareInstallStatus;

	//****************************以上属性仅用作查询条件***************************

	
	public RentType getRentType() {
		return rentType;
	}

	

	
	
	public String getDoorLockHardwareInstallStatus() {
		return doorLockHardwareInstallStatus;
	}





	public void setDoorLockHardwareInstallStatus(String doorLockHardwareInstallStatus) {
		this.doorLockHardwareInstallStatus = doorLockHardwareInstallStatus;
	}





	public String getAmmeterHardwareInstallStatus() {
		return ammeterHardwareInstallStatus;
	}





	public void setAmmeterHardwareInstallStatus(String ammeterHardwareInstallStatus) {
		this.ammeterHardwareInstallStatus = ammeterHardwareInstallStatus;
	}

	



	public Boolean getAmmeterWaitForDone() {
		return ammeterWaitForDone;
	}





	public void setAmmeterWaitForDone(Boolean ammeterWaitForDone) {
		this.ammeterWaitForDone = ammeterWaitForDone;
	}



	

	public List<NetworkCard> getNetworkCardList() {
		return networkCardList;
	}





	public void setNetworkCardList(List<NetworkCard> networkCardList) {
		this.networkCardList = networkCardList;
	}





	public List<Router> getRouterList() {
		return routerList;
	}





	public void setRouterList(List<Router> routerList) {
		this.routerList = routerList;
	}





	public void setRentType(RentType rentType) {
		this.rentType = rentType;
	}

	public List<String> getSourceHouseCodeList() {
		return sourceHouseCodeList;
	}

	public void setSourceHouseCodeList(List<String> sourceHouseCodeList) {
		this.sourceHouseCodeList = sourceHouseCodeList;
	}



	public List<HouseSyncRecord> getHouseSyncRecordList() {
		return houseSyncRecordList;
	}

	public void setHouseSyncRecordList(List<HouseSyncRecord> houseSyncRecordList) {
		this.houseSyncRecordList = houseSyncRecordList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	

	
	

	public DoorType getDoorType() {
		return doorType;
	}

	public void setDoorType(DoorType doorType) {
		this.doorType = doorType;
	}

	public Boolean getParentIdNullFlag() {
		return parentIdNullFlag;
	}

	public void setParentIdNullFlag(Boolean parentIdNullFlag) {
		this.parentIdNullFlag = parentIdNullFlag;
	}

	public String getOnLineSynCode() {
		return onLineSynCode;
	}

	public void setOnLineSynCode(String onLineSynCode) {
		this.onLineSynCode = onLineSynCode;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public HouseSource getSource() {
		return source;
	}

	public void setSource(HouseSource source) {
		this.source = source;
	}

	public String getSourceHouseCode() {
		return sourceHouseCode;
	}

	public void setSourceHouseCode(String sourceHouseCode) {
		this.sourceHouseCode = sourceHouseCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Hardware> getHardwareList() {
		return hardwareList;
	}

	public void setHardwareList(List<Hardware> hardwareList) {
		this.hardwareList = hardwareList;
	}

	public List<Ammeter> getAmmeterList() {
		return ammeterList;
	}

	public void setAmmeterList(List<Ammeter> ammeterList) {
		this.ammeterList = ammeterList;
	}

	public List<DoorLock> getDoorLockList() {
		return doorLockList;
	}

	public void setDoorLockList(List<DoorLock> doorLockList) {
		this.doorLockList = doorLockList;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public List<HardwareInstallOrder> getHardwareInstallOrderList() {
		return hardwareInstallOrderList;
	}

	public void setHardwareInstallOrderList(List<HardwareInstallOrder> hardwareInstallOrderList) {
		this.hardwareInstallOrderList = hardwareInstallOrderList;
	}

	public Boolean getHouseMemberNullFlag() {
		return houseMemberNullFlag;
	}

	public void setHouseMemberNullFlag(Boolean houseMemberNullFlag) {
		this.houseMemberNullFlag = houseMemberNullFlag;
	}

	public HouseUnit getHouseUnit() {
		return houseUnit;
	}

	public void setHouseUnit(HouseUnit houseUnit) {
		this.houseUnit = houseUnit;
	}

	public String getBlock() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
			return block.getName();
			}
		}
		return null;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getUnit() {
		if(this.houseUnit!=null){
			return houseUnit.getName();
		}
		return null;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStreet() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				
					if(houseDistrict!=null){
						HouseStreet street = houseDistrict.getHouseStreet();
						if(street!=null){
							return street.getStreet();
						}
					
				}
			}
		}
		return null;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getProvinceName() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				if(houseDistrict!=null){
					HouseStreet street = houseDistrict.getHouseStreet();
					if(street!=null){
						return street.getProvinceName();
					}
				}
			}
		}
		return null;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getDistName() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				if(houseDistrict!=null){
					HouseStreet street = houseDistrict.getHouseStreet();
					if(street!=null){
						return street.getDistName();
					}
				}
			}
		}
		return null;
	}



	public String getCityName() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				if(houseDistrict!=null){
					HouseStreet street = houseDistrict.getHouseStreet();
					if(street!=null){
						return street.getCityName();
					}
				}
			}
		}
		return null;
	}



	public String getAddress() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				if(houseDistrict!=null){
					HouseStreet street = houseDistrict.getHouseStreet();
					if(street!=null){
						return street.getAddress();
					}
				}
			}
		}
		return null;
	}

	public String getType() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				if(houseDistrict!=null){
					HouseStructureType houseStructureType= houseDistrict.getHouseStructureType();
					if(houseStructureType!=null){
						return houseStructureType.name();
					}
				}
			}
		}
		return null;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDistrictName() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				if(houseDistrict!=null){
					return houseDistrict.getDistrictName();
				}
			}
		}
		return null;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictId() {
		if(this.houseUnit!=null){
			HouseBlock block=this.houseUnit.getHouseBlock();
			if(block!=null){
				HouseDistrict houseDistrict = block.getHouseDistrict();
				if(houseDistrict!=null){
					return houseDistrict.getId();
				}
			}
		}
		return null;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof House){
			House house=(House)obj;
			if(this.getId().equals(house.getId())){
				return true;
			}
		}else{
			return false;
		}
		return false;
	}

	public List<RentHouseLog> getRentHouseLogList() {
		return rentHouseLogList;
	}

	public void setRentHouseLogList(List<RentHouseLog> rentHouseLogList) {
		this.rentHouseLogList = rentHouseLogList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<FlowCard> getFlowCardList() {
		return flowCardList;
	}

	public void setFlowCardList(List<FlowCard> flowCardList) {
		this.flowCardList = flowCardList;
	}

	public String getRouterHardwareInstallStatus() {
		return routerHardwareInstallStatus;
	}

	public void setRouterHardwareInstallStatus(String routerHardwareInstallStatus) {
		this.routerHardwareInstallStatus = routerHardwareInstallStatus;
	}

	public String getFlowCardHardwareInstallStatus() {
		return flowCardHardwareInstallStatus;
	}

	public void setFlowCardHardwareInstallStatus(String flowCardHardwareInstallStatus) {
		this.flowCardHardwareInstallStatus = flowCardHardwareInstallStatus;
	}
}
