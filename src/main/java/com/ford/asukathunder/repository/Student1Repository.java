package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: Student1Repository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/9 下午 2:46
 **/
public interface Student1Repository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {

    List<Student> findByName(String name);

}
