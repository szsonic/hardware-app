package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 流量卡实体类
 *
 * @author chengzhifeng
 */
@Entity
@Table(name = "flow_card")
@org.hibernate.annotations.Table(appliesTo = "flow_card", comment = "4G流量卡")
public class FlowCard extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 0不用复通 1需要复通
     */
    @Column(name = "isOver", nullable = true, columnDefinition = "int(2) comment '0不用复通 1需要复通'")
    private Integer isOver;

    /**
     * 所属用户
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", columnDefinition = "varchar(32) comment '用户ID'")
    private Member member;

    /**
     * 运营商ID
     */
    @Column(name = "operatorId", columnDefinition = "varchar(16) comment '运营商ID'")
    private String operatorId;

    /**
     * 运营商名称
     */
    @Column(name = "operatorName", columnDefinition = "varchar(16) comment '运营商名称'")
    private String operatorName;

    /**
     * 流量卡套餐
     */
    @OneToMany(mappedBy = "flowCard", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<FlowCardCombo> flowCardCombos;

    /**
     * 所属供应商产品
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "supplierProductId", columnDefinition = "varchar(32) comment '供应商产品ID'")
    private SupplierProduct supplierProduct;

    /**
     * 设备唯一ID
     */
    @Column(name = "devId", unique = true, columnDefinition = "varchar(128) comment '设备唯一ID'")
    private String devId;

    /**
     * 所属房间
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "houseId", columnDefinition = "varchar(32) comment '房间ID'")
    private House house;

    /**
     * 对应硬件记录
     */
    @OneToOne(mappedBy = "flowCard", fetch = FetchType.LAZY)
    private Hardware hardware;

    /**
     * 套餐内总流量
     */
    @Column(name = "cumulationTotal", columnDefinition = "varchar(32) comment '套餐内总流量'")
    private String cumulationTotal;

    /**
     * 套餐内已用总流量
     */
    @Column(name = "cumulationTotalAlready", columnDefinition = "varchar(32) comment '套餐内已用总流量'")
    private String cumulationTotalAlready;

    /**
     * 流量状态(1正常 0异常)
     */
    @Column(name = "cumulationStatus", columnDefinition = "varchar(32) comment '流量状态(1正常 0异常)'")
    private String cumulationStatus;

    public Integer getIsOver() {
        return isOver;
    }

    public void setIsOver(Integer isOver) {
        this.isOver = isOver;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public List<FlowCardCombo> getFlowCardCombos() {
        return flowCardCombos;
    }

    public void setFlowCardCombos(List<FlowCardCombo> flowCardCombos) {
        this.flowCardCombos = flowCardCombos;
    }

    public SupplierProduct getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(SupplierProduct supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public String getCumulationTotal() {
        return cumulationTotal;
    }

    public void setCumulationTotal(String cumulationTotal) {
        this.cumulationTotal = cumulationTotal;
    }

    public String getCumulationTotalAlready() {
        return cumulationTotalAlready;
    }

    public void setCumulationTotalAlready(String cumulationTotalAlready) {
        this.cumulationTotalAlready = cumulationTotalAlready;
    }

    public String getCumulationStatus() {
        return cumulationStatus;
    }

    public void setCumulationStatus(String cumulationStatus) {
        this.cumulationStatus = cumulationStatus;
    }
}