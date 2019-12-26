package com.ford.asukathunder.common.entity.role;

import com.ford.asukathunder.common.entity.base.BaseEntity;
import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 *  角色权限关联表
 *  @ClassName: RolePermissionRef
 *  @author: Ford.Zhang
 *  @version: 1.0
 *  2019/12/26 下午 6:00
 */
@Entity
@Table(name = "role_permission_ref")
@DynamicUpdate
public class RolePermissionRef extends BaseEntity {
    @Id
    @Column(name = "role_permission_id", length = 32, updatable = false, unique = true)
    private String rolePermissionId;

    /**
     * 系统角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk1_role_permission_ref"))
    private Role role;

    /**
     * 系统权限
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "permission_id", foreignKey = @ForeignKey(name = "fk2_role_permission_ref"))
    private Permission permission;

    public String getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(String rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public void setEntityId(SnowflakeIdWorker snowflakeIdWorker) {
        if (StringUtils.isBlank(rolePermissionId)) {
            rolePermissionId = String.valueOf(snowflakeIdWorker.nextId());
        }
    }
}
