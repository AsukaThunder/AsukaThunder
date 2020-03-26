package com.ford.asukathunder;

import com.alibaba.fastjson.JSON;
import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.*;
import com.ford.asukathunder.common.entity.jpa.teacher_student.repository.StudentRepository;
import com.ford.asukathunder.common.entity.jpa.teacher_student.repository.TeacherRepository;
import com.ford.asukathunder.common.json.JSONFormat;
import com.ford.asukathunder.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: TeacherTests
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/24 下午 3:25
 **/
@SpringBootTest
class TeacherTests {
    private JSONFormat jsonFormat = new JSONFormat();
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private TeacherRepository teacherRepository;

    /**
     * 仅将被维护方对象添加进维护方对象Set中
     * 保存维护方对象
     */
    @Test
    public void 多对多插入1() {//运行后发现数据库创建了3表，其中有一张时中间表
        Student s = studentRepository.findByName("二狗");
        s.setName("三狗");
        s.setAge(10);
        Course c = new Course();
        c.setName("数学");
        s.getCourses().add(c);
        studentRepository.save(s);//由于操作对象是维护方，成功地在student、course以及中间表student_courses中分别添加了数据
        System.out.println(jsonFormat.responseFormat(JSON.toJSONString(s)));
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
        courseRepository.save(c);//操作对象在这里换成了被维护方。不负众望，出问题了。保存的时候，student表和course表倒是都成功地插入了数据，但是中间表中，并未产生对两者数据的关联。
    }

    @Test
    public void saveStudent(){

        Student student = new Student();
        student.setAge(18);
        student.setName("张三");

        StudentDesk desk = new StudentDesk();
        desk.setDeskNum(10);
        JSONFormat jsonFormat = new JSONFormat();
        // 学生分配座位，如果设置了级联保存，保存学生的时候也会保存座位，如果没设置级联保存，添加课桌会报错
        student.setDesk(desk);
        studentRepository.save(student);
        System.out.println(jsonFormat.responseFormat(JSON.toJSONString(student)));
    }

    @Test
    public void deleteStudent(){
        List<Teacher> teachers = teacherRepository.findAll();
        System.out.println(jsonFormat.responseFormat(JSON.toJSONString(teachers)));
        Optional<Student> student = studentRepository.findById("4028a952710bf79c01710bf7aa400000");
        student.ifPresent(student1 -> {
            studentRepository.delete(student1);
        });
        List<Teacher> teachers1 = teacherRepository.findAll();
        System.out.println(jsonFormat.responseFormat(JSON.toJSONString(teachers1)));

    }

    @Test
    public void updateStudent(){
        Optional<Student> student = studentRepository.findById("4028a952710b76ed01710b77019e0000");
        student.ifPresent(student1 -> {
            student1.setAge(11);
            student1.setName("dagou");

            ClassRoom classRoom1 = student1.getClassRoom();
            classRoom1.setName("class_3");
            studentRepository.save(student1);
        });
        JSONFormat jsonFormat = new JSONFormat();
        System.out.println(jsonFormat.responseFormat(JSON.toJSONString(student)));
    }

    @Test
    public void getStudents() {
        JSONFormat jsonFormat = new JSONFormat();
        List<Student> students = studentRepository.findAll();
        System.out.println(jsonFormat.responseFormat(JSON.toJSONString(students.get(0))));
    }

    @Test
    public void getTeachers() {
        JSONFormat jsonFormat = new JSONFormat();
        List<Teacher> teachers = teacherRepository.findAll();
        System.out.println(jsonFormat.responseFormat(JSON.toJSONString(teachers)));
    }
}
