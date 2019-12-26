package com.ford.asukathunder.common.entity.user;

import com.ford.asukathunder.common.entity.base.BaseEntity;
import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 系统登陆人员
 * @ClassName: User
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/26 下午 6:00
 **/
@Entity
@Table(name = "user")
@DynamicUpdate
@Getter
@Setter
public class User extends BaseEntity {

    @Override
    public void setEntityId(SnowflakeIdWorker snowflakeIdWorker) {
        if (StringUtils.isBlank(userId)) {
            userId = String.valueOf(snowflakeIdWorker.nextId());
        }
    }
    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id", length = 32, updatable = false, unique = true, columnDefinition = "comment '用户id'")
    private String userId;

    /**
     * 用户名称
     */
    @Column(name = "user_name", length = 30, nullable = false, columnDefinition = "comment '用户账号'")
    private String username;

    /**
     * 用户名称
     */
    @Column(name = "nick_name", length = 50, nullable = false, columnDefinition = "comment '用户昵称'")
    private String nickname;

    /**
     * 用户头像
     */
    @Column(name = "avatar", columnDefinition = "varchar(100) comment '头像地址'")
    private String avatar;

    /**
     * 用户类型
     */
    @Column(name = "user_type", columnDefinition = "varchar(2) comment '用户类型（00管理员）'")
    private String userType;

    /**
     * 用户密码
     */
    @Column(name = "password", length = 32, nullable = false, columnDefinition = "comment '登录密码'")
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 30, columnDefinition = "comment '真实姓名'")
    private String realName;

    /**
     * 性别
     */
    @Column(name = "gender", columnDefinition = "comment '用户性别'")
    private Integer gender;

    /**
     * 手机
     */
    @Column(name = "mobile_phone", length = 20, columnDefinition = "comment '手机号码'")
    private String mobilePhone;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 50, columnDefinition = "comment '用户邮箱'")
    private String email;

    /**
     * 是否启用
     */
    @Column(name = "is_use", columnDefinition = "comment '是否启用'")
    private Boolean isUse;

    /**
     * 最后登录IP
     */
    @Column(name = "login_ip", columnDefinition = "varchar(50) comment '最后登录IP'")
    private String loginIp;

    /**
     * 是否启用
     */
    @Column(name = "login_time", columnDefinition = "datetime comment '最后登录时间'")
    private Date loginTime;

    /**
     * version 2.0
     * 用户角色关系
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<UserRoleRef> userRoleRef;

}
