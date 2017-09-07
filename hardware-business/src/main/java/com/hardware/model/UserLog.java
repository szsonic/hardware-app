package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户操作日志
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "user_log")
@org.hibernate.annotations.Table(appliesTo = "user_log", comment = "用户操作日志")
public class UserLog extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 访问方法
	 */
	@Column(columnDefinition = "varchar(512) comment '访问方法'")
	private String method;

	/**
	 * 参数
	 */
	@Column(columnDefinition = "varchar(1024) comment '参数'")
	private String args;

	/**
	 * 操作者
	 */
	@Column(columnDefinition = "varchar(128) comment '操作者名字'")
	private String operator;

	/**
	 * 操作者手机号
	 */
	@Column(columnDefinition = "varchar(32) comment '操作者手机号'")
	private String operatorMobile;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(512) comment '备注'")
	private String remark;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorMobile() {
		return operatorMobile;
	}

	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

}
