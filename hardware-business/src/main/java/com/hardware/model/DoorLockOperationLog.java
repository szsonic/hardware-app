package com.hardware.model;

import com.hardware.enums.DoorLockOperationType;
import com.hardware.enums.PwdOpStatus;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Entity
@Table(name = "doorlock_operation_log")
@org.hibernate.annotations.Table(appliesTo = "doorlock_operation_log", comment = "门锁操作记录表")
public class DoorLockOperationLog extends BaseModel {
    private static final long serialVersionUID = 1L;


    /**
     * 操作时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime comment '操作时间'")
    private Date operationTime;

    /**
     * 参数信息
     */
    @Column(columnDefinition = "varchar(500) comment '参数信息'")
    private String params;


    /**
     * 门锁操作类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) comment '门锁操作类型'")
    private DoorLockOperationType doorLockOperationType;


    /**
     * 同步状态(调用第三方返回是否成功)
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) comment '同步状态'")
    private PwdOpStatus pwdOpStatus;

   
    /**
     * 详细信息
     */
    @Column(columnDefinition = "varchar(2000) comment '详细信息'")
    private String message;


    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinColumn(name = "passwordId")
    private MemberLockPass memberLockPass;

    public MemberLockPass getMemberLockPass() {
        return memberLockPass;
    }

    public void setMemberLockPass(MemberLockPass memberLockPass) {
        this.memberLockPass = memberLockPass;
    }

    



    public PwdOpStatus getPwdOpStatus() {
		return pwdOpStatus;
	}

	public void setPwdOpStatus(PwdOpStatus pwdOpStatus) {
		this.pwdOpStatus = pwdOpStatus;
	}

	public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public DoorLockOperationType getDoorLockOperationType() {
        return doorLockOperationType;
    }

    public void setDoorLockOperationType(DoorLockOperationType doorLockOperationType) {
        this.doorLockOperationType = doorLockOperationType;
    }

    
   

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
