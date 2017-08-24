package com.hardware.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Entity
@Table(name = "one")
@org.hibernate.annotations.Table(appliesTo = "one", comment = "测试一对多，一")
public class One {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, nullable = false, unique = true, columnDefinition = "varchar(32) comment '记录主键'")
    private String id;

    @Column(columnDefinition = "varchar(128) not null comment '名字'")
    private String name;

    @OneToMany(mappedBy = "one", cascade = { CascadeType.MERGE,CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<Many> manyList;

}
