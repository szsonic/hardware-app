package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "houseunit_sync_record")
@org.hibernate.annotations.Table(appliesTo = "houseunit_sync_record", comment = "房源同步信息模型")
public class HouseUnitSyncRecord extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "houseUnitId")
	private HouseUnit houseUnit;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;


	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}


	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}


	public HouseUnit getHouseUnit() {
		return houseUnit;
	}


	public void setHouseUnit(HouseUnit houseUnit) {
		this.houseUnit = houseUnit;
	}
	
	
}
