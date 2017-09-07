package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "house_district_sync_record")
@org.hibernate.annotations.Table(appliesTo = "house_district_sync_record", comment = "小区同步信息模型")
public class HouseDistrictSyncRecord extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "houseDistrictId")
	private HouseDistrict houseDistrict;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;


		


	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}


	public HouseDistrict getHouseDistrict() {
		return houseDistrict;
	}


	public void setHouseDistrict(HouseDistrict houseDistrict) {
		this.houseDistrict = houseDistrict;
	}


	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}
	
	
}
