package com.ford.asukathunder.common.entity.jpa.teacher_student.repository;

import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.Teacher;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @ClassName: TeacherRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/24 下午 6:01
 **/
public interface TeacherRepository extends JpaRepositoryImplementation<Teacher,String> {
}
