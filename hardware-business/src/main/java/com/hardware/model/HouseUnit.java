package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.List;

/**
 * 房源单元信息模型
 *
 * @author yinjiawei
 *
 */
@Entity
@Table(name = "house_unit")
@org.hibernate.annotations.Table(appliesTo = "house_unit", comment = "房源单元信息模型")
public class HouseUnit extends BaseModel {
    private static final long serialVersionUID = 1L;

    private String name;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "houseBlockId")
    private HouseBlock houseBlock;

    /**
     * 单元包含的房源信息
     */
    @OneToMany(mappedBy = "houseUnit", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<House> houseList;

    /**
     * 该单元同步房源的记录
     */
    @OneToMany(mappedBy = "houseUnit", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<HouseUnitSyncRecord> houseUnitSyncRecordList;

    /**
     * 来源id
     */
    @Column(columnDefinition = "varchar(50) comment '来源id'")
    private String sourceHouseUnitCode;

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


    public String getSourceHouseUnitCode() {
        return sourceHouseUnitCode;
    }

    public void setSourceHouseUnitCode(String sourceHouseUnitCode) {
        this.sourceHouseUnitCode = sourceHouseUnitCode;
    }

    public List<HouseUnitSyncRecord> getHouseUnitSyncRecordList() {
        return houseUnitSyncRecordList;
    }

    public void setHouseUnitSyncRecordList(List<HouseUnitSyncRecord> houseUnitSyncRecordList) {
        this.houseUnitSyncRecordList = houseUnitSyncRecordList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HouseBlock getHouseBlock() {
        return houseBlock;
    }

    public void setHouseBlock(HouseBlock houseBlock) {
        this.houseBlock = houseBlock;
    }

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }


}
