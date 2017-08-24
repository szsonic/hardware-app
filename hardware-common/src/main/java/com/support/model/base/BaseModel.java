package com.support.model.base;

import com.support.model.RecordStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * model基类
 * 所有model均继承此类
 */
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, nullable = false, unique = true, columnDefinition = "varchar(32) comment '记录主键'")
    private String id;

    /**
     * 记录状态
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(32) default 'AVAILABLE' comment '记录状态'")
    private RecordStatus status;

    /**
     * 创建记录时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime comment '创建记录时间'", updatable = false)
    private Date createTime;
    /**
     * 更新记录时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime comment '更新记录时间'")
    private Date updateTime;

    /**
     * 开始时间（用于查询）
     */
    @Transient
    private Date dateStart;
    /**
     * 结束时间（用于查询）
     */
    @Transient
    private Date dateEnd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
