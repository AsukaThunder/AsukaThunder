package com.ford.asukathunder.common.entity.role;


import com.ford.asukathunder.common.entity.base.BaseEntity;
import com.ford.asukathunder.common.entity.user.User;
import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 用户角色关系表
 *  @ClassName: UserRoleRef
 *  @author: Ford.Zhang
 *  @version: 1.0
 *  2019/12/26 下午 6:00
 */
@Entity
@Table(name = "user_role_ref")
@DynamicUpdate
public class UserRoleRef extends BaseEntity {

    /**
     * 角色用户关系表
     */
    @Id
    @Column(name = "user_role_id", length = 32, updatable = false, unique = true)
    private String userRoleId;

    /**
     * 用户角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk1_user_role"))
    private Role role;

    /**
     * 系统用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk2_user_role"))
    private User user;


    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setEntityId(SnowflakeIdWorker snowflakeIdWorker) {
        if (StringUtils.isBlank(userRoleId)) {
            userRoleId = String.valueOf(snowflakeIdWorker.nextId());
        }
    }
}
