package com.hardware.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Entity
@Table(name = "many")
@org.hibernate.annotations.Table(appliesTo = "many", comment = "测试一对多，多")
public class Many{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, nullable = false, unique = true, columnDefinition = "varchar(32) comment '记录主键'")
    private String id;

    @Column(columnDefinition = "varchar(128) not null comment '名字'")
    private String name;

    @ManyToOne(cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinColumn(name = "one_id")
    private One one;

}
