package com.hardware.business.model;

import com.support.BaseHibernateModelSupport;

import javax.persistence.*;

@Entity
@Table(name = "hardware_fittings")
@org.hibernate.annotations.Table(appliesTo = "hardware_fittings", comment = "硬件配件")
public class HardwareFittings extends BaseHibernateModelSupport {

	private static final long serialVersionUID = 1L;


	//配件对应的商品
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierProductDetailId")
	private SupplierProductDetail supplierProductDetail;

	@Column(columnDefinition = "int(8) comment '数量' default 1")
	private Integer amount;

	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "ammeterInstallOrderId")
	private AmmeterInstallOrder ammeterInstallOrder;

	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "doorLockInstallOrderId")
	private DoorLockInstallOrder doorLockInstallOrder;

	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "routerInstallOrderId")
	private RouterInstallOrder routerInstallOrder;

	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "flowCardInstallOrderId")
	private FlowCardInstallOrder flowCardInstallOrder;


	public SupplierProductDetail getSupplierProductDetail() {
		return supplierProductDetail;
	}

	public void setSupplierProductDetail(SupplierProductDetail supplierProductDetail) {
		this.supplierProductDetail = supplierProductDetail;
	}

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
}
