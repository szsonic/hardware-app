package com.hardware.model;

import com.hardware.enums.HardwareInstallStatus;
import com.hardware.enums.NetWorkStatus;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "flowcard_install_order")
@org.hibernate.annotations.Table(appliesTo = "flowcard_install_order", comment = "SIM卡安装订单")
public class FlowCardInstallOrder extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) comment '安装状态'")
	private HardwareInstallStatus installStatus;

	@OneToOne(mappedBy = "flowCardInstallOrder", fetch = FetchType.LAZY)
	private HardwareInstallOrder hardwareInstallOrder;

	@Column(columnDefinition = "int(8) comment '安装数量' default 1")
	private Integer amount;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) comment '网络状态'")
	private NetWorkStatus netWorkStatus;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;

	@OneToMany(mappedBy = "flowCardInstallOrder", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<HardwareFittings> hardwareFittings;

	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}

	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}

	public HardwareInstallStatus getInstallStatus() {
		return installStatus;
	}

	public void setInstallStatus(HardwareInstallStatus installStatus) {
		this.installStatus = installStatus;
	}

	public HardwareInstallOrder getHardwareInstallOrder() {
		return hardwareInstallOrder;
	}

	public void setHardwareInstallOrder(HardwareInstallOrder hardwareInstallOrder) {
		this.hardwareInstallOrder = hardwareInstallOrder;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public NetWorkStatus getNetWorkStatus() {
		return netWorkStatus;
	}

	public void setNetWorkStatus(NetWorkStatus netWorkStatus) {
		this.netWorkStatus = netWorkStatus;
	}

	public List<HardwareFittings> getHardwareFittings() {
		return hardwareFittings;
	}

	public void setHardwareFittings(List<HardwareFittings> hardwareFittings) {
		this.hardwareFittings = hardwareFittings;
	}
}
