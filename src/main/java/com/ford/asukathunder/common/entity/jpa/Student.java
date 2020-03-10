package com.ford.asukathunder.common.entity.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: Student
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/9 下午 2:39
 **/
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Student{
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;
    private String name;
    /**
     * 关系维护方
     */
    @ManyToMany(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();
}
