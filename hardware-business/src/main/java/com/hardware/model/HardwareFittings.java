package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "hardware_fittings")
@org.hibernate.annotations.Table(appliesTo = "hardware_fittings", comment = "硬件配件")
public class HardwareFittings extends BaseModel {

	private static final long serialVersionUID = 1L;


	//配件对应的商品
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;

	@Column(columnDefinition = "int(8) comment '数量' default 1")
	private Integer amount;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "ammeterInstallOrderId")
	private AmmeterInstallOrder ammeterInstallOrder;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "doorLockInstallOrderId")
	private DoorLockInstallOrder doorLockInstallOrder;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "routerInstallOrderId")
	private RouterInstallOrder routerInstallOrder;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "flowCardInstallOrderId")
	private FlowCardInstallOrder flowCardInstallOrder;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public FlowCardInstallOrder getFlowCardInstallOrder() {
		return flowCardInstallOrder;
	}

	public void setFlowCardInstallOrder(FlowCardInstallOrder flowCardInstallOrder) {
		this.flowCardInstallOrder = flowCardInstallOrder;
	}

	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}

	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}
}
