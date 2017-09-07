package com.hardware.model;

import com.support.model.base.BaseModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 系统菜单支持方法模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "menu_method")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.hibernate.annotations.Table(appliesTo = "menu_method", comment = "系统菜单支持方法信息模型")
public class MenuMethod extends BaseModel {
	private static final long serialVersionUID = 2116756061589883398L;

	/**
	 * 操作方法名称
	 */
	@Column(length = 32)
	private String methodName;
	/**
	 * 操作方法标签名
	 */
	@Column(length = 32)
	private String methodTagName;

	/**
	 * 是否选择使用
	 */
	@Column
	private Integer isSelected;

	/**
	 * 方法备注
	 */
	@Column(length = 128)
	private String remark;

	/**
	 * 所属菜单
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "menuId")
	private Menu menu;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodTagName() {
		return methodTagName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setMethodTagName(String methodTagName) {
		this.methodTagName = methodTagName;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Integer getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}

}
