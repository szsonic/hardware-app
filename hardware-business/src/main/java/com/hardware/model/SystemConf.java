package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统配置参数模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "system_conf")
@org.hibernate.annotations.Table(appliesTo = "system_conf", comment = "系统配置参数模型")
public class SystemConf extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数名称
	 */
	@Column(columnDefinition = "varchar(64) not null comment '参数名称'", unique = true)
	private String paramKey;

	/**
	 * 参数内容
	 */
	@Column(columnDefinition = "varchar(512) not null comment '参数内容'")
	private String paramValue;

	/**
	 * 参数备注说明
	 */
	@Column(columnDefinition = "varchar(128) comment '参数备注说明'")
	private String remark;

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
