package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "excelimportlog")
@org.hibernate.annotations.Table(appliesTo = "excelimportlog", comment = "excel导入错误日志")
public class ExcelImportLog extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 导入房源ID
	 */
	@Column(columnDefinition = "varchar(128) comment '房源ID'")
	private String houseId;
	@Column(columnDefinition = "varchar(128) comment '第三方节点名'")
	private String nodeName;
	@Column(columnDefinition = "varchar(1280) comment '导入提示信息'")
	private String message;

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
