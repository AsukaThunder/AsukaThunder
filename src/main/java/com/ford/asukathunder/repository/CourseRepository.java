package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @ClassName: CourseRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/9 下午 2:54
 **/
public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {

    /**
     * 使用@Query注解创建查询,将该注解贴在dao的方法上,然后提供一个需要的JPQL语句即可,如:
     */
    @Query("SELECT p FROM Course p WHERE name LIKE %:name%")
    Course findByName(@Param("name") String name);

    /**
     *使用@Modifying注解来标识该方法执行的是更新或者删除操作
     * Sql语句使用 :param ，并且在方法形参使用注解 @Param("param") 注意注解中的值要与Sql中的 :param 一致
     */
    @Modifying
    @Query("UPDATE Course p SET p.name = :name WHERE p.id = :id")
    void updateCourseName(@Param("id") Integer id, @Param("name") String name);

}
