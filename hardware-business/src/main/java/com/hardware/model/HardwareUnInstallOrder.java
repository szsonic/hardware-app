package com.hardware.model;

import com.hardware.enums.HardwareUnInstallStatus;
import com.support.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "hardware_un_install_order")
@org.hibernate.annotations.Table(appliesTo = "hardware_un_install_order", comment = "硬件设备卸除记录表")
public class HardwareUnInstallOrder extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     *
        狀態
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(32) comment '狀態'")
    private HardwareUnInstallStatus hardwareUnInstallStatus;


    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "doorLockId",columnDefinition = "varchar(50) ")
    private DoorLock doorLock;


    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ammeterId",columnDefinition = "varchar(50) ")
    private Ammeter ammeter;


    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinColumn(name = "projectId")
    private Project project;

     /**
     * 参数信息
     */
    @Column(columnDefinition = "varchar(500) comment '参数信息'")
    private String params;


    /**
     * 详细信息
     */
    @Column(columnDefinition = "varchar(2000) comment '详细信息'")
    private String message;


    public HardwareUnInstallStatus getHardwareUnInstallStatus() {
        return hardwareUnInstallStatus;
    }


    public void setHardwareUnInstallStatus(HardwareUnInstallStatus hardwareUnInstallStatus) {
        this.hardwareUnInstallStatus = hardwareUnInstallStatus;
    }


    public DoorLock getDoorLock() {
        return doorLock;
    }


    public void setDoorLock(DoorLock doorLock) {
        this.doorLock = doorLock;
    }


    public Ammeter getAmmeter() {
        return ammeter;
    }


    public void setAmmeter(Ammeter ammeter) {
        this.ammeter = ammeter;
    }


    public Project getProject() {
        return project;
    }


    public void setProject(Project project) {
        this.project = project;
    }


    public String getParams() {
        return params;
    }


    public void setParams(String params) {
        this.params = params;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }



}
