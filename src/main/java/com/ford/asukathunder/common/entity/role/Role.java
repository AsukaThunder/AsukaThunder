package com.ford.asukathunder.common.entity.role;

import com.ford.asukathunder.common.entity.base.BaseEntity;
import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 系统用户角色表
 * @author :Ford.Zhang
 */
@Entity
@Table(name = "role")
@DynamicUpdate
@Getter
@Setter
public class Role extends BaseEntity {

    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id", length = 32, updatable = false, unique = true)
    private String roleId;

    /**
     * 角色编码
     */
    @Column(name = "role_code", length = 32)
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否启用
     */
    @Column(name = "enable", length = 1)
    private Boolean enable;

    /**
     * 能否删除: 超级管理员
     */
    @Column(name = "can_delete", length = 1)
    private Boolean canDelete;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolePermissionRef> rolePermissionRef;


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleRef> userRoleRef;


    public Role() {
    }

    public Role(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public void setEntityId(SnowflakeIdWorker snowflakeIdWorker) {
        if (StringUtils.isBlank(roleId)) {
            roleId = String.valueOf(snowflakeIdWorker.nextId());
        }
    }
}
