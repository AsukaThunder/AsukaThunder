package com.ford.asukathunder.common.entity.jpa.teacher_student.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName: ClassRoom
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/24 下午 3:02
 **/
@Entity(name = "class_room")
@Getter
@Setter
public class ClassRoom {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;
    private String name;

    @OneToMany(mappedBy = "classRoom",fetch = FetchType.EAGER)
    private List<Student> students;
}
