package com.ford.asukathunder.common.entity.user;

import com.ford.asukathunder.common.entity.base.BaseEntity;
import com.ford.asukathunder.common.entity.role.RoleCode;
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
 * @ClassName: User1
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
    @Column(name = "user_id", length = 32, updatable = false, unique = true)
    private String userId;

    /**
     * 用户账号
     */
    @Column(name = "user_account", length = 30, nullable = false)
    private String account;

    /**
     * 用户名称
     */
    @Column(name = "nick_name", length = 50, nullable = false)
    private String nickname;

    /**
     * 出生日期
     */
    @Column(name = "birthday", columnDefinition = "date comment '出生日期'")
    private Date birthday;

    /**
     * 用户年龄
     */
    @Column(name = "age", columnDefinition = "int(5) comment '年龄'")
    private Integer age;

    /**
     * 用户星座
     */
    @Column(name = "constellation", columnDefinition = "varchar(10) comment '星座'")
    private String constellation;

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
    @Column(name = "password", length = 32, nullable = false)
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 30)
    private String realName;

    /**
     * 性别
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 手机
     */
    @Column(name = "mobile_phone", length = 20)
    private String mobilePhone;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 50)
    private String email;

    /**
     * 格言
     */
    @Column(name = "motto", columnDefinition = "varchar(500) comment '格言'")
    private String motto;

    /**
     * 是否启用
     */
    @Column(name = "is_use")
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
     * version 1.0
     * 用户角色关系
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<UserRoleRef> userRoleRef;

    /**
     * 是否超级管理员
     */
    @Transient
    public Boolean isRoot() {
        return this.isCurrentRole(RoleCode.ROOT.name());
    }

    /**
     * 是否管理员
     */
    @Transient
    public Boolean isAdmin() {
        return this.isCurrentRole(RoleCode.ADMIN.name());
    }

    @Transient
    private Boolean isCurrentRole(String roleCode) {
        List<UserRoleRef> roleRefs = this.getUserRoleRef();
        for (UserRoleRef ref : roleRefs) {
            if (roleCode.equals(ref.getRole().getRoleCode())) {
                return true;
            }
        }
        return false;
    }

}
