package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 房源幢信息模型
 *
 * @author yinjiawei
 *
 */
@Entity
@Table(name = "house_block")
@org.hibernate.annotations.Table(appliesTo = "house_block", comment = "房源幢信息模型")
public class HouseBlock extends BaseModel {
    private static final long serialVersionUID = 1L;

    private String name;
    /**
     * 幢包含的单元信息
     */
    @OneToMany(mappedBy = "houseBlock", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<HouseUnit> houseUnitList;



    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "houseDistrictId")
    private HouseDistrict houseDistrict;

    /**
     * 来源id
     */
    @Column(columnDefinition = "varchar(50) comment '来源id'")
    private String sourceHouseBlockCode;

    /**
     * 线上房同步第三方编号，线下不涉及
     */
    @Column(columnDefinition = "varchar(16) comment '线上房同步第三方编号，线下不涉及'", unique = true)
    private String onLineSynCode;





    public String getOnLineSynCode() {
        return onLineSynCode;
    }



    public void setOnLineSynCode(String onLineSynCode) {
        this.onLineSynCode = onLineSynCode;
    }







    public String getSourceHouseBlockCode() {
        return sourceHouseBlockCode;
    }



    public void setSourceHouseBlockCode(String sourceHouseBlockCode) {
        this.sourceHouseBlockCode = sourceHouseBlockCode;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public List<HouseUnit> getHouseUnitList() {
        return houseUnitList;
    }



    public void setHouseUnitList(List<HouseUnit> houseUnitList) {
        this.houseUnitList = houseUnitList;
    }



    public HouseDistrict getHouseDistrict() {
        return houseDistrict;
    }



    public void setHouseDistrict(HouseDistrict houseDistrict) {
        this.houseDistrict = houseDistrict;
    }



}
