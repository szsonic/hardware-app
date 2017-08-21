package com.hardware.business.model;

import com.hardware.business.enums.HardwareType;
import com.support.BaseHibernateModelSupport;

import javax.persistence.*;
import java.util.List;

/**
 * 供应商产品信息模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "supplier_product")
@org.hibernate.annotations.Table(appliesTo = "supplier_product", comment = "供应商产品信息")
public class SupplierProduct extends BaseHibernateModelSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * 产品名
	 */
	@Column(columnDefinition = "varchar(128) comment '产品名'")
	private String name;

	/**
	 * 供应商sdk
	 */
	@Column(columnDefinition = "varchar(128) comment '供应商sdk'")
	private String sdk;

	/**
	 * 产品code
	 */
	@Column(columnDefinition = "varchar(128) comment '产品code'", unique = true)
	private String code;

	/**
	 * 供应商产品所属硬件中心的分类
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) comment '供应商产品所属硬件中心的分类'")
	private HardwareType hardwareType;

	/**
	 * 所属供应商
	 */
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "supplierId")
	private Supplier supplier;


	/**
	 * 供应商拥有的产品集合
	 */
	@OneToMany(mappedBy = "supplierProduct", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<SupplierProductDetail> supplierProductDetails;


    /**
     * 备注
	 */
	@Column(columnDefinition = "varchar(128) comment '备注'")
	private String remark;
//
//	/**
//	 * 电表所属供应商集合
//	 */
//	@OneToMany(mappedBy = "supplierProduct", cascade = { CascadeType.PERSIST,
//			CascadeType.MERGE }, fetch = FetchType.LAZY)
//	private List<Ammeter> ammeters;
//
//	/**
//	 * 电表所属供应商集合
//	 */
//	@OneToMany(mappedBy = "supplierProduct", cascade = { CascadeType.PERSIST,
//			CascadeType.MERGE }, fetch = FetchType.LAZY)
//	private List<DoorLock> doorlocks;
	
	

//	/**
//	 * 该产品同步房源的记录	
//	 */
//	@OneToMany(mappedBy = "supplierProduct", cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.LAZY)
//	private List<HouseSyncRecord> houseSyncRecordList;
	
	
//	
//	public List<HouseSyncRecord> getHouseSyncRecordList() {
//		return houseSyncRecordList;
//	}
//
//	public void setHouseSyncRecordList(List<HouseSyncRecord> houseSyncRecordList) {
//		this.houseSyncRecordList = houseSyncRecordList;
//	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSdk() {
		return sdk;
	}

	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HardwareType getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(HardwareType hardwareType) {
		this.hardwareType = hardwareType;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<SupplierProductDetail> getSupplierProductDetails() {
		return supplierProductDetails;
	}

	public void setSupplierProductDetails(List<SupplierProductDetail> supplierProductDetails) {
		this.supplierProductDetails = supplierProductDetails;
	}

	//	public List<Ammeter> getAmmeters() {
//		return ammeters;
//	}
//
//	public void setAmmeters(List<Ammeter> ammeters) {
//		this.ammeters = ammeters;
//	}
//
//	public List<DoorLock> getDoorlocks() {
//		return doorlocks;
//	}
//
//	public void setDoorlocks(List<DoorLock> doorlocks) {
//		this.doorlocks = doorlocks;
//	}

}
