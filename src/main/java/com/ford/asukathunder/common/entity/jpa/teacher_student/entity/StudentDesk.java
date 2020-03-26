package com.ford.asukathunder.common.entity.jpa.teacher_student.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @ClassName: StudentDesk
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/24 下午 2:55
 **/
@Entity(name = "student_desk")
@Getter
@Setter
public class StudentDesk{

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;
    private Integer deskNum;

    @OneToOne(mappedBy = "desk")
    private Student student;
}
