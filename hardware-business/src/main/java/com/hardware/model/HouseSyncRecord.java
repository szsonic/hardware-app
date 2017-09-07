package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "house_sync_record")
@org.hibernate.annotations.Table(appliesTo = "house_sync_record", comment = "房源同步信息模型")
public class HouseSyncRecord extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "houseId")
	private House house;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;


	public House getHouse() {
		return house;
	}


	public void setHouse(House house) {
		this.house = house;
	}


	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}


	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}
	
	
}
