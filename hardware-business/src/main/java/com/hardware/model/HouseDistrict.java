package com.hardware.model;

import com.hardware.enums.HouseStructureType;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 房源街道的小区信息模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "house_district")
@org.hibernate.annotations.Table(appliesTo = "house_district", comment = "房源街道的小区信息模型")
public class HouseDistrict extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	
	
	
	public HouseDistrict() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HouseDistrict(String id,String onLineSynCode, String districtName,HouseStreet houseStreet,Long houseCount){
		 this.setId(id);
		 this.onLineSynCode=onLineSynCode;
	     this.districtName=districtName;
	     this.houseCount=houseCount;
	     this.houseStreet=houseStreet;
	   } 
	/**
	 * 小区的名字
	 */
	@Column(columnDefinition = "varchar(128) comment '小区的名字'")
	private String districtName;

	/**
	 * 线上小区同步第三方编号，线下不涉及
	 */
	@Column(columnDefinition = "varchar(16) comment '线上小区同步第三方编号，线下不涉及'", unique = true)
	private String onLineSynCode;

	/**
	 * 签名
	 */
	@Column(columnDefinition = "varchar(512) comment '签名'")
	private String sign;

	/**
	 * 房源类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) comment '房源类型'")
	private HouseStructureType houseStructureType;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "houseStreetId")
	private HouseStreet houseStreet;

	
	/**
	 * 小区包含的楼栋信息
	 */
	@OneToMany(mappedBy = "houseDistrict", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<HouseBlock> houseBlockList;
	
	
	/**
	 * 小区包含的同步信息
	 */
	@OneToMany(mappedBy = "houseDistrict", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<HouseDistrictSyncRecord> houseDistrictSyncRecordList;
	/**
	 * 来源id
	 */
	@Column(columnDefinition = "varchar(50) comment '来源id'")
	private String sourceHouseDistrictCode;
	
	
	@javax.persistence.Transient
	private Long houseCount;

	public Long getHouseCount() {
		return houseCount;
	}

	public void setHouseCount(Long houseCount) {
		this.houseCount = houseCount;
	}

	public String getSourceHouseDistrictCode() {
		return sourceHouseDistrictCode;
	}

	public void setSourceHouseDistrictCode(String sourceHouseDistrictCode) {
		this.sourceHouseDistrictCode = sourceHouseDistrictCode;
	}

	public List<HouseBlock> getHouseBlockList() {
		return houseBlockList;
	}

	public void setHouseBlockList(List<HouseBlock> houseBlockList) {
		this.houseBlockList = houseBlockList;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public HouseStreet getHouseStreet() {
		return houseStreet;
	}

	public void setHouseStreet(HouseStreet houseStreet) {
		this.houseStreet = houseStreet;
	}

	

	public HouseStructureType getHouseStructureType() {
		return houseStructureType;
	}

	public void setHouseStructureType(HouseStructureType houseStructureType) {
		this.houseStructureType = houseStructureType;
	}

	public String getOnLineSynCode() {
		return onLineSynCode;
	}

	public void setOnLineSynCode(String onLineSynCode) {
		this.onLineSynCode = onLineSynCode;
	}

	public List<HouseDistrictSyncRecord> getHouseDistrictSyncRecordList() {
		return houseDistrictSyncRecordList;
	}

	public void setHouseDistrictSyncRecordList(List<HouseDistrictSyncRecord> houseDistrictSyncRecordList) {
		this.houseDistrictSyncRecordList = houseDistrictSyncRecordList;
	}
	
}