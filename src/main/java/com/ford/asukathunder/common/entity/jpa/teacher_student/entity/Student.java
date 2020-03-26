package com.ford.asukathunder.common.entity.jpa.teacher_student.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "student")
public class Student{
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;
    private Integer age;
    private String name;
    /**
     * 关系维护方
     */
    @Fetch(FetchMode.SELECT)
    @ManyToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Course> courses = new HashSet<>();

    /**
     * 这里是定义学生和课桌的一对一的关系
     * 下面的这个注解用来生成第三张表，来维护学生和课桌的关系
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name="stu_desk",joinColumns = @JoinColumn(name="student_id"),inverseJoinColumns = @JoinColumn(name="desk_id"))
    private StudentDesk desk;

    //@Fetch(FetchMode.SELECT)
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    //@JoinColumn(name = "class_id")
    private ClassRoom classRoom;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="stu_teacher",joinColumns = @JoinColumn(name ="stu_id"),inverseJoinColumns = @JoinColumn(name="teacher_id"))
    private List<Teacher> teachers = new ArrayList<>();
}
