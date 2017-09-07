package com.hardware.model;

import com.hardware.enums.SupplierAccountType;
import com.support.model.base.BaseModel;

import javax.persistence.*;

/**
 * 供应商产品信息模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "supplier_account")
@org.hibernate.annotations.Table(appliesTo = "supplier_account", comment = "供应商账户信息")
public class SupplierAccount extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 供应商帐号
	 */
	@Column(columnDefinition = "varchar(128) comment '供应商帐号'")
	private String account;

	/**
	 * 供应商帐号密码
	 */
	@Column(columnDefinition = "varchar(128) comment '供应商帐号密码'")
	private String accountPass;

	/**
	 * 供应商HOST
	 */
	@Column(columnDefinition = "varchar(128) comment '供应商HOST'")
	private String host;

	/**
	 * 供应商版本号
	 */
	@Column(columnDefinition = "varchar(128) comment '供应商版本号'")
	private String version;

	/**
	 * 供应商合作者secret
	 */
	@Column(columnDefinition = "varchar(128) comment '供应商合作者secret'")
	private String secret;

	
	/**
	 * 所属供应商
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "supplierId")
	private Supplier supplier;

	/**
	 * 供应商id
	 */
	@Column(columnDefinition = "varchar(128) comment '账号类型'")
	private SupplierAccountType type;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountPass() {
		return accountPass;
	}

	public void setAccountPass(String accountPass) {
		this.accountPass = accountPass;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public SupplierAccountType getType() {
		return type;
	}

	public void setType(SupplierAccountType type) {
		this.type = type;
	}

}
