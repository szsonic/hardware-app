package com.hardware.model;

import com.hardware.enums.PwdOpStatus;
import com.hardware.enums.ThirdPartySyncAction;
import com.support.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Entity
@Table(name = "thirdparty_sync_log")
@org.hibernate.annotations.Table(appliesTo = "thirdparty_sync_log", comment = "第三方同步记录")
public class ThirdPartySyncLog extends BaseModel {
    private static final long serialVersionUID = 1L;


    /**
     * 同步时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime comment '同步时间'")
    private Date syncTime;

    /**
     * 参数信息
     */
    @Column(columnDefinition = "varchar(500) comment '参数信息'")
    private String params;


    /**
     * 同步类型
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) comment '同步类型'")
    private ThirdPartySyncAction thirdPartySyncAction;


    /**
     * 接口地址
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) comment '同步状态'")
    private PwdOpStatus syncStatus;

    /**
     * 方法名
     */
    @Column(columnDefinition = "varchar(30) comment '方法名'")
    private String methodName;

    /**
     * 详细信息
     */
    @Column(columnDefinition = "varchar(2000) comment '详细信息'")
    private String message;

    public ThirdPartySyncAction getThirdPartySyncAction() {
        return thirdPartySyncAction;
    }

    public void setThirdPartySyncAction(ThirdPartySyncAction thirdPartySyncAction) {
        this.thirdPartySyncAction = thirdPartySyncAction;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public PwdOpStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(PwdOpStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
