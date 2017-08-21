package com.support;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;
import com.support.model.RecordStatus;
import org.hibernate.annotations.GenericGenerator;

/**
 * 基础模型支持类
 *
 * @author shenpeng
 *
 */
@MappedSuperclass
public class BaseHibernateModelSupport implements Serializable {
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
    @Column(columnDefinition = "varchar(32) default 'AVAILABLE' not null comment '记录状态'")
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

    /**
     * 获取记录主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置记录主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取记录状态
     *
     * @return status 枚举类型记录状态
     */
    public RecordStatus getStatus() {
        return status;
    }

    /**
     * 设置记录状态
     *
     * @param status
     *            枚举类型记录状态
     */
    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    /**
     * 获取创建记录时间
     *
     * @return Date 日期对象
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建记录时间
     *
     * @param createTime
     *            日期对象
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新记录时间
     *
     * @return Date 日期对象
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新记录时间
     *
     * @param updateTime
     *            日期对象
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取开始时间（用于根据时间段进行查询）
     *
     * @return Date 开始时间
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * 设置开始时间（用于根据时间段进行查询）
     *
     * @param dateStart
     *            开始时间
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * 获取结束时间（用于根据时间段进行查询）
     *
     * @return Date 结束时间
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * 设置结束时间（用于根据时间段进行查询）
     *
     * @param dateEnd
     *            结束时间
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * 快速获取泛型模型名称（首字母小写）
     *
     * @return 返回泛型模型名称
     */
    public String getModelName() {
        String modelName = this.getClass().getName();
        return modelName.substring(modelName.lastIndexOf(".") + 1, modelName.lastIndexOf(".") + 2).toLowerCase()
                + modelName.substring(modelName.lastIndexOf(".") + 2);
    }

    /**
     * 快速获取泛型模型名称（首字母大写）
     *
     * @return 返回泛型模型名称
     */
    public String getClazzName() {
        String modelName = this.getClass().getName();
        return modelName.substring(modelName.lastIndexOf(".") + 1);
    }

    /**
     * 快速将实体输出成字符串
     */
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
