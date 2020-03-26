package com.ford.asukathunder.common.entity.jpa.teacher_student.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Teacher
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/24 下午 3:07
 **/
@Getter
@Setter
@Entity(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;
    private String name;
    private String subject;

    @ManyToMany(mappedBy = "teachers",fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();
}
