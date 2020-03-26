package com.ford.asukathunder.common.entity.jpa.other;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @ClassName: Users
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/18 下午 4:57
 **/
@Entity
@Table(name="t_Users")
@Getter
@Setter
public class Users {
    private int id;
    private String name;
    private Group group;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)//解决1+N,级联用ALL
    @JoinColumn(name = "groupId")//指定外键名称，不指定的默认值是group_Id
    public Group getGroup() {
        return group;
    }
}
