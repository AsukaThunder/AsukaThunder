package com.ford.asukathunder.common.entity.jpa.other;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName: Authority
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/18 下午 3:26
 **/
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name; //权限名

    @ManyToMany(mappedBy = "authorityList")
    private List<User1> user1List;
}
