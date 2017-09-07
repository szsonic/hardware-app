package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 项目信息模型
 * 
 * @author zhongqi
 * 
 */
@Entity
@Table(name = "project")
@org.hibernate.annotations.Table(appliesTo = "project", comment = "项目信息模型")
public class Project extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 项目名称
	 */
	@Column(columnDefinition = "varchar(128) comment '项目名称'")
	private String name;

	/**
	 * 项目code
	 */
	@Column(columnDefinition = "varchar(64) comment '项目code'", unique = true)
	private String code;

	/**
	 * 项目类型 1、SQ 2、舍艺
	 */
	//@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(16) default 'OTHER' comment '项目类型'")
	private String type;

//	/**
//	 * 项目所属用户集合
//	 */
//	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
//	@JoinTable(name = "projects_members", joinColumns = { @JoinColumn(name = "projectId") }, inverseJoinColumns = { @JoinColumn(name = "memberId") })
//	private List<Member> members;
	
	
	/**
	 * 訂單記錄集合
	 */
	@OneToMany(mappedBy = "project", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private List<HardwareInstallOrder> hardwareInstallOrderList;
	
	
	
	public List<HardwareInstallOrder> getHardwareInstallOrderList() {
		return hardwareInstallOrderList;
	}

	public void setHardwareInstallOrderList(List<HardwareInstallOrder> hardwareInstallOrderList) {
		this.hardwareInstallOrderList = hardwareInstallOrderList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	public List<Member> getMembers() {
//		return members;
//	}
//
//	public void setMembers(List<Member> members) {
//		this.members = members;
//	}

}
