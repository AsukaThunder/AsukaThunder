package com.ford.asukathunder;

import com.ford.asukathunder.common.entity.jpa.Course;
import com.ford.asukathunder.common.entity.jpa.Student;
import com.ford.asukathunder.repository.CourseRepository;
import com.ford.asukathunder.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AsukathunderApplicationTests {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private CourseRepository courseRepository;

    @Test
    void contextLoads() {
    }

    /**
     * 仅将被维护方对象添加进维护方对象Set中
     * 保存维护方对象
     */
    @Test
    public void 多对多插入1() {
        Student s = new Student();
        s.setName("二狗");
        Course c = new Course();
        c.setName("语文");
        s.getCourses().add(c);
        studentRepository.save(s);
    }

    /**
     * 仅将维护方对象添加进被维护方对象Set中
     * 保存被维护方对象
     */
    @Test
    public void 多对多插入2() {
        Student s = new Student();
        s.setName("三汪");
        Course c = new Course();
        c.setName("英语");
        c.getStudents().add(s);
        courseRepository.save(c);
    }

    /**
     * 将双方对象均添加进双方Set中
     * 保存被维护方对象
     */
    @Test
    public void 多对多插入3() {
        Student s = new Student();
        s.setName("一晌");
        Course c = new Course();
        c.setName("数学");
        s.getCourses().add(c);
        c.getStudents().add(s);
        courseRepository.save(c);
    }

    /**
     * 删除维护方对象
     */
    @Test
    public void 多对多删除1(){
        Student s = studentRepository.findByName("二狗");
        studentRepository.delete(s);
    }

    /**
     * 删除被维护方对象
     */
    @Test
    public void 多对多删除2(){
        Course c = courseRepository.findByName("英语");
       // Course c = courseRepository.findByName("数学");
        courseRepository.delete(c);
    }
}
