package com.ford.asukathunder.common.entity.jpa.teacher_student.repository;

import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.Student;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @ClassName: Student1Repository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/24 下午 3:14
 **/
public interface StudentRepository extends JpaRepositoryImplementation<Student,String>{
    Student findByName(String name);
}
