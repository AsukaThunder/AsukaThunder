package com.ford.asukathunder.common.entity.jpa.teacher_student;

import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.Student;
import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.StudentDesk;
import com.ford.asukathunder.common.entity.jpa.teacher_student.repository.StudentRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: ClassController
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/24 下午 3:13
 **/
@RestController
@RequestMapping(value = "/class")
public class ClassController {
    @Resource
    private StudentRepository studentRepository;

    @PostMapping("/student/save")
    public Student saveStudent() {
        Student student = new Student();
        student.setAge(18);
        student.setName("张三");

        StudentDesk desk = new StudentDesk();
        desk.setDeskNum(10);

        // 学生分配座位，如果设置了级联保存，保存学生的时候也会保存座位，如果没设置级联保存，添加课桌会报错
        student.setDesk(desk);
        return studentRepository.save(student);
    }

}
