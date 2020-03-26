package com.ford.asukathunder.common.entity.jpa.other;

import javax.persistence.*;
import java.security.Timestamp;

/**
 * @ClassName: People
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/18 下午 3:09
 **/
@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;//id

    @Column(name = "name", nullable = true, length = 20)
    private String name;//姓名

    @Column(name = "sex", nullable = true, length = 1)
    private String sex;//性别

    @Column(name = "birthday", nullable = true)
    private Timestamp birthday;//出生日期

    @OneToOne(cascade=CascadeType.ALL)//People是关系的维护端，当删除 people，会级联删除 address
    @JoinColumn(name = "address_id", referencedColumnName = "id")//people中的address_id字段参考address表中的id字段
    private Address address;//地址

    @OneToOne(cascade=CascadeType.ALL)//People是关系的维护端
    @JoinTable(name = "people_address",
            joinColumns = @JoinColumn(name="people_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))//通过关联表保存一对一的关系
    private Address address1;//地址
}