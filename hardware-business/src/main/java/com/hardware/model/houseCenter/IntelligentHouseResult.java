package com.hardware.model.houseCenter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 智能硬件
 * @author zhouyu
 * @date 2017-5-3
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntelligentHouseResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1719060848447334601L;
	/**
	 * 消息代码
	 */
	private String code;
	/**
	 * 房源数据
	 */
	private FinalHouseSourceModel [] data;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public FinalHouseSourceModel[] getData() {
		return data;
	}
	public void setData(FinalHouseSourceModel[] data) {
		this.data = data;
	}
	
	
}
