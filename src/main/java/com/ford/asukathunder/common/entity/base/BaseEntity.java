package com.ford.asukathunder.common.entity.base;

import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import com.ford.asukathunder.common.util.SpringContextUtils;
import com.ford.asukathunder.common.util.UserUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 表的基类
 * 创建者、更新者、创建时间、更新时间等
 * @ClassName: base
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/26 下午 4:52
 **/
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    /**
     * 创建时间
     */
    @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
    @CreatedBy
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", columnDefinition = "datetime comment '更新时间'")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 创建人
     */
    @Column(name = "create_user", columnDefinition = "varchar(64) comment '创建者'")
    @CreatedBy
    private String createUser;

    /**
     * 修改人
     */
    @Column(name = "update_user", columnDefinition = "varchar(64) comment '更新者'")
    @LastModifiedBy
    private String updateUser;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @Column(name = "is_delete", columnDefinition = "char(1) comment'是否删除（0正常 1已删除）'")
    private Boolean isDelete;

    @Transient
    public abstract void setEntityId(SnowflakeIdWorker snowflakeIdWorker);

    @PrePersist
    public void prePersistent() {
        SnowflakeIdWorker snowflakeIdWorker = SpringContextUtils.getBean("snowflakeIdWorker");
        setEntityId(snowflakeIdWorker);

        String userId = UserUtils.getUserId();
        if (createUser == null) {
            this.createUser = userId;
        }

        if (updateUser == null) {
            this.updateUser = userId;
        }

        if (isDelete == null) {
            setIsDelete(false);
        }
        if (createTime == null) {
            setCreateTime(new Date());
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updateUser = UserUtils.getUserId();
        if (isDelete == null) {
            setIsDelete(false);
        }
    }

    @Transient
    private CurrentUser currentUser;

    /**
     * 用来传输当前登录用户
     */
    public static class CurrentUser {

        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    /**
     * 设置当前登录用户
     *
     * @param user
     */
    public void setCurrentUser(CurrentUser user) {
        this.currentUser = user;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public CurrentUser getCurrentUser() {
        return this.currentUser;
    }

}
