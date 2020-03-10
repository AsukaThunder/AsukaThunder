package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.jpa.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: CourseRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/9 下午 2:54
 **/
public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {
    Course findByName(String name);
}
