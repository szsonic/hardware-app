package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

/**
 * 供应商产品详细信息模型
 * 
 * @author sunzhongshuai
 * 
 */
@Entity
@Table(name = "supplier_product_detail")
@org.hibernate.annotations.Table(appliesTo = "supplier_product_detail", comment = "供应商产品信息详细信息")
public class SupplierProductDetail extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属产品
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "supplierProductId")
	private SupplierProduct supplierProduct;

	/**
     * 参数单位
	 */
	@Column(columnDefinition = "varchar(128) comment '参数单位'")
	private String unit;

	/**
	 * 参数名
	 */
	@Column(columnDefinition = "varchar(128) comment '参数名'")
	private String param;

	/**
	 * 参数值
	 */
	@Column(columnDefinition = "varchar(128) comment '参数值'")
	private String value;

	public SupplierProduct getSupplierProduct() {
		return supplierProduct;
	}

	public void setSupplierProduct(SupplierProduct supplierProduct) {
		this.supplierProduct = supplierProduct;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
