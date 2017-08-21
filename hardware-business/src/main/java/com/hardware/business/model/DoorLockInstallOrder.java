package com.hardware.business.model;

import com.hardware.business.enums.DoorMaterial;
import com.hardware.business.enums.HardwareInstallStatus;
import com.hardware.business.enums.NetWorkStatus;
import com.support.BaseHibernateModelSupport;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doorlock_install_order")
@org.hibernate.annotations.Table(appliesTo = "doorlock_install_order", comment = "门锁安装订单")
public class DoorLockInstallOrder extends BaseHibernateModelSupport {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) comment '安装状态'")
	private HardwareInstallStatus installStatus;

	@OneToOne(mappedBy = "doorLockInstallOrder", fetch = FetchType.LAZY)
	private HardwareInstallOrder hardwareInstallOrder;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "image", columnDefinition = "MEDIUMBLOB comment '门锁图片'", nullable=true)
	private byte[] image;

	@Column(columnDefinition = "varchar(16) comment '房东联系方式'")
	private String contactMobile;

	@Column(columnDefinition = "varchar(16) comment '房门朝向'")
	private String doorDirection;

	@Column(columnDefinition = "float(11) comment '门厚度'", scale = 2)
	private Float doorThickness;

	@Column(columnDefinition = "int(8) comment '安装数量' default 1")
	private Integer amount;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) comment '网络状态'")
	private NetWorkStatus netWorkStatus;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) comment '门的材质'")
	private DoorMaterial doorMaterial;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;

	@OneToMany(mappedBy = "doorLockInstallOrder", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<HardwareFittings> hardwareFittings;

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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(String doorDirection) {
		this.doorDirection = doorDirection;
	}

	public Float getDoorThickness() {
		return doorThickness;
	}

	public void setDoorThickness(Float doorThickness) {
		this.doorThickness = doorThickness;
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

	public DoorMaterial getDoorMaterial() {
		return doorMaterial;
	}

	public void setDoorMaterial(DoorMaterial doorMaterial) {
		this.doorMaterial = doorMaterial;
	}

	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}

	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}

	public List<HardwareFittings> getHardwareFittings() {
		return hardwareFittings;
	}

	public void setHardwareFittings(List<HardwareFittings> hardwareFittings) {
		this.hardwareFittings = hardwareFittings;
	}
}
