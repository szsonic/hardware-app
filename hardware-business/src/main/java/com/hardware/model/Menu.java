package com.hardware.model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hardware.enums.MenuType;
import com.support.model.base.BaseModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 系统菜单模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "menu")
@org.hibernate.annotations.Table(appliesTo = "menu", comment = "系统菜单信息模型")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu extends BaseModel {
	private static final long serialVersionUID = -7626707213960303318L;
	/**
	 * 标签名
	 */
	@Column(columnDefinition = "varchar(32) comment '标签名'")
	private String tagName;

	/**
	 * 菜单名称
	 */
	@Column(columnDefinition = "varchar(32) comment '菜单名称'")
	private String menuName;

	/**
	 * 菜单类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(32) default 'INTERNAL' not null comment '菜单类型'")
	private MenuType menuType;

	/**
	 * 图标样式
	 */
	@Column(columnDefinition = "varchar(32) comment '图标样式'")
	private String menuIconCss;

	/**
	 * 父级菜单ID
	 */
	@Column(columnDefinition = "varchar(32) comment '父级菜单ID'")
	private String upId;

	/**
	 * 菜单等级
	 */
	@Column(columnDefinition = "int(1) default 0 not null comment '菜单等级'")
	private Integer level;

	/**
	 * 菜单请求地址
	 */
	@Column(columnDefinition = "varchar(256) comment '菜单请求地址'")
	private String menuPath;

	/**
	 * 排序
	 */
	@Column(columnDefinition = "varchar(64) comment '排序'")
	private String sort;

	/**
	 * 所属菜单
	 */
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "upId", nullable = true, insertable = false, updatable = false)
	private Menu upMenu;

	/**
	 * 所属的方法集合
	 */
	@OneToMany(mappedBy = "menu", cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<MenuMethod> menuMethods;

	/**
	 * 菜单备注
	 */
	@Column(length = 128)
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public String getMenuIconCss() {
		return menuIconCss;
	}

	public void setMenuIconCss(String menuIconCss) {
		this.menuIconCss = menuIconCss;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public String getUpId() {
		return upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public Menu getUpMenu() {
		return upMenu;
	}

	public void setUpMenu(Menu upMenu) {
		this.upMenu = upMenu;
	}

	public List<MenuMethod> getMenuMethods() {
		return menuMethods;
	}

	public void setMenuMethods(List<MenuMethod> menuMethods) {
		this.menuMethods = menuMethods;
	}

	/**
	 * 转换成JSON格式字符串
	 * 
	 * @return String JSON格式字符串
	 */
	public String toJson() {
		Gson g = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes field) {
				boolean flag = false;
				flag = field.getDeclaredType() == Menu.class ? true : false;
				return flag;
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		}).setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").create();

		return g.toJson(this);
	}
}
