package com.ford.asukathunder;

import com.ford.asukathunder.common.encrypt.anno.Encrypt;
import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.Course;
import com.ford.asukathunder.common.entity.jpa.teacher_student.entity.Student;
import com.ford.asukathunder.common.entity.user.User;
import com.ford.asukathunder.repository.*;
import com.sun.org.glassfish.external.statistics.Statistic;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AsukathunderApplicationTests {

    @Resource
    private Student1Repository student1Repository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private UserRepository userRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Resource
    CityEntityRepository cityEntityRepository;
    @Resource
    ArticleRepository articleRepository;

    /**
     * 仅将被维护方对象添加进维护方对象Set中
     * 保存维护方对象
     */
    @Test
    public void 多对多插入1() {
        //CascadeType.PERSIST
        Student s = new Student();
        s.setName("二狗");
        Course c = new Course();
        c.setName("语文");
        s.getCourses().add(c);
        student1Repository.save(s);
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
        List<Student> s = student1Repository.findByName("二狗");
        student1Repository.deleteAll(s);
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

    @Override
    public String toString() {
        return "{\"AsukathunderApplicationTests\":{"
                + "\"userRepository\":"
                + userRepository
                + "}}";

    }

    /**
     * example查询
     */
    @Test
    @Encrypt
    public void contextLoads() {
        User user = new User();

        user.setNickname("飞鸟");
        //user.setPassword("admin");
        Pageable  pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "createTime");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains());//模糊查询匹配开头，即{username}%
               // .withMatcher("address" ,ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
                //.withIgnorePaths("password");//忽略字段，即不管password是什么值都不加入查询条件
        Example<User> example = Example.of(user ,matcher);
        Page<User> list = userRepository.findAll(example,pageable);
        System.out.println(list.getContent().get(0).toString());
        System.out.print("昵称：");
        System.out.println(list.getContent().get(0).getNickname());
        System.out.print("真实姓名：");
        System.out.println(list.getContent().get(0).getRealName());
        System.out.println(list.getContent().get(0).getUserId());
        System.out.println(list.getContent().get(0).getAccount());
        System.out.println(list.getContent().get(0).getEmail());
        System.out.println(list.getContent().get(0).getGender());
        System.out.println(list.getContent().get(0).getMobilePhone());
    }

    /**
     * jpa Sort 使用关联表的字段排序
     * listOrder.add(newOrder(Direction.DESC,"userInfo.auth"));
     * listOrder.add(newOrder(Direction.DESC,"userInfo.lockAccount"));
     */
    @Test
    public Statistic getStatistic() {
        // 查寻结果返回自定义对象
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Statistic> criteriaQuery = criteriaBuilder.createQuery(Statistic.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        Path<Integer> id = userRoot.get("userId");
        Path<String> name = userRoot.get("nickname");
        // 查询需要的字段
        criteriaQuery.multiselect(id,criteriaBuilder.count(name));

        // 根据id分组
        criteriaQuery.groupBy(id);
        // 筛选改组name 统计大于0的数据
        criteriaQuery.having(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(name),0L));
        TypedQuery<Statistic> typedQuery = entityManager.createQuery(criteriaQuery);
        System.out.println(typedQuery.getSingleResult());
        return typedQuery.getSingleResult();
    }
    @Test
    public void getNameAndAccount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> root = q.from(User.class);
        //设定select字段
        q.multiselect(
                root.get("userId"),
                root.get("nickname"),
                root.get("account")
        );
        //设定groupby条件
        q.groupBy(
                root.get("userId").as(String.class),
                root.get("nickname").as(String.class),
                root.get("account").as(String.class)
        );
        List<User> rs = entityManager.createQuery(q).getResultList();
        System.out.println(rs.get(0).toString());
    }

    //排序和分页
    @Test
    public void sortAndPageSearchTest() {

        //排序的几种方式

        //方式一：多字段排序查询，无法自定义顺序，输出语句...order by adminId desc, adminUsername desc
        //Sort sort = new Sort(Sort.Direction.DESC,"adminId","adminUsername");

        //方式二：多字段排序查询，可自定义顺序，x输出语句：...order by adminUsername asc, adminId desc
//        Sort sort = new Sort(Sort.Direction.DESC,"userId");//第一个参数，排序类型：ASC/DESC，第二个参数：按照排序的字段，可以设置多个
//        Sort sort1 = new Sort(Sort.Direction.ASC,"nickname");
//        final Sort mergeSort = sort1.and(sort);

        //方式三：多字段排序查询，先创建order对象，再构造sort
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"userId");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC,"nickname");
        //可以直接单对象创建
        Sort orderSort = Sort.by(order2);//...order by adminUsername asc
        //可以使用order列表创建
        orders.add(order1);
        orders.add(order2);
        Sort orderListSort = Sort.by(orders);//...order by adminId desc, adminUsername desc


        //需要注意的是：page从0开始，不是从1开始！
        PageRequest pageRequest = PageRequest.of(0,5, orderListSort);
        Page<User> content = userRepository.findAll(pageRequest);
        if (content.hasContent()) {
            System.out.println("总记录数："+content.getTotalElements());
            System.out.println("总页数："+content.getTotalPages());
            System.out.println("当前页："+(content.getPageable().getPageNumber()+1));
            System.out.println("当前页面大小："+content.getPageable().getPageSize());
            List<User> list = content.getContent();
            list.forEach(System.out::println);
            System.out.println(content);
        }
        System.out.println("==========================================");
    }

}
