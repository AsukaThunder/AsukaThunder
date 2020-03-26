package com.ford.asukathunder.common.entity.jpa.teacher_student.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: Course
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/9 下午 2:41
 **/
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;
    private String name;
    /**
     * 关系被维护方
     */
    @ManyToMany(mappedBy="courses",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();
}
