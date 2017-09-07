package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

/**
 * 流量卡套餐实体类
 *
 * @author chengzhifeng
 */
@Entity
@Table(name = "flow_card_combo")
@org.hibernate.annotations.Table(appliesTo = "flow_card_combo", comment = "4G流量卡套餐")
public class FlowCardCombo extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 号码的ICCID号
	 */
	@Column(name = "number", nullable = false, columnDefinition = "varchar(32) comment '号码的ICCID号'")
	private String number;

	/**
	 * 套餐开始时间
	 */
	@Column(name = "startTime", nullable = true, columnDefinition = "varchar(16) comment '套餐开始时间'")
	private String startTime;

	/**
	 * 订购套餐名称
	 */
	@Column(name = "offerName", nullable = true, columnDefinition = "varchar(64) comment '订购套餐名称'")
	private String offerName;

	/**
	 * 套餐结束时间
	 */
	@Column(name = "endTime", nullable = true, columnDefinition = "varchar(16) comment '套餐结束时间'")
	private String endTime;

	/**
	 * 套餐总量(单位M)
	 */
	@Column(name = "cumulationTotal", nullable = true, columnDefinition = "varchar(8) comment '套餐总量(单位M)'")
	private String cumulationTotal;

	/**
	 * 套餐已使用量(单位M)
	 */
	@Column(name = "cumulationAlready", nullable = true, columnDefinition = "varchar(8) comment '套餐已使用量(单位M)'")
	private String cumulationAlready;

	/**
	 * 套餐剩余量(单位M)
	 */
	@Column(name = "cumulationLeft", nullable = true, columnDefinition = "varchar(8) comment '套餐剩余量(单位M)'")
	private String cumulationLeft;

	/**
	 * 流量卡
	 */
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "flowCardId", columnDefinition = "varchar(32) comment '流量卡ID'")
	private FlowCard flowCard;


	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}


	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getCumulationTotal() {
		return cumulationTotal;
	}

	public void setCumulationTotal(String cumulationTotal) {
		this.cumulationTotal = cumulationTotal;
	}

	public String getCumulationAlready() {
		return cumulationAlready;
	}

	public void setCumulationAlready(String cumulationAlready) {
		this.cumulationAlready = cumulationAlready;
	}


	public String getCumulationLeft() {
		return cumulationLeft;
	}

	public void setCumulationLeft(String cumulationLeft) {
		this.cumulationLeft = cumulationLeft;
	}


	public FlowCard getFlowCard() {
		return flowCard;
	}

	public void setFlowCard(FlowCard flowCard) {
		this.flowCard = flowCard;
	}
}
