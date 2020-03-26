package com.ford.asukathunder.common.entity.jpa.other;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: Group
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/18 下午 4:56
 **/
@Entity
@Getter
@Setter
@Table(name="t_Group")//指定一个表名
public class Group {
    private int id;
    private String name;
    private Set<Users> users = new HashSet<Users>();

    @Id
    @GeneratedValue//主键用自增序列
    public int getId() {
        return id;
    }

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)//以“多”一方为主导管理，级联用ALL
    public Set<Users> getUsers() {
        return users;
    }
}
