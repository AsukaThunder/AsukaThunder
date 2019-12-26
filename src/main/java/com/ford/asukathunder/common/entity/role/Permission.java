package com.ford.asukathunder.common.entity.role;

import com.ford.asukathunder.common.entity.base.BaseEntity;
import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 系统用户权限表
 * @ClassName: Permission
 * @author :Ford.Zhang
 * @version: 1.0
 * 2019/12/26 下午 6:00
 */
@Entity
@Table(name = "permission")
@DynamicUpdate
public class Permission extends BaseEntity {

    public enum PlatformType {
        /**
         * 权限类型
         */
        PC("电脑端"),

        MOBILE("手机端");

        private final String value;

        PlatformType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public enum ResourceType {
        /**
         * 资源类型
         */
        MENU("菜单"),

        ELEMENT("元素"),

        DATA("数据");

        private final String value;

        ResourceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    @Id
    @Column(name = "permission_id", length = 32, updatable = false, unique = true)
    private String permissionId;

    /**
     * 权限code
     */
    @Column(name = "permission_code")
    private String permissionCode;

    /**
     * 权限名称
     */
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 平台类型- pc/mobile
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private PlatformType platform;

    /**
     * 资源类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    /**
     * 资源url
     */
    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "display_sort")
    private Integer displaySort;

    @Column(name = "enable", length = 1)
    private Boolean enable;

    /**
     * 能否删除: 某些预置权限不能被删除
     */
    @Column(name = "can_delete", length = 1)
    private Boolean canDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_permission_id", foreignKey = @ForeignKey(name = "fk_parent_permission"))
    private Permission parentPermission;

    @OrderBy("display_sort asc")
    @OneToMany(mappedBy = "parentPermission", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Permission> sonPermissions;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolePermissionRef> rolePermissionRef;

    public Permission getParentPermission() {
        return parentPermission;
    }

    public void setParentPermission(Permission parentPermission) {
        this.parentPermission = parentPermission;
    }

    public List<Permission> getSonPermissions() {
        return sonPermissions;
    }

    public void setSonPermissions(List<Permission> sonPermissions) {
        this.sonPermissions = sonPermissions;
    }

    public List<RolePermissionRef> getRolePermissionRef() {
        return rolePermissionRef;
    }

    public void setRolePermissionRef(List<RolePermissionRef> rolePermissionRef) {
        this.rolePermissionRef = rolePermissionRef;
    }

    public Permission() {
    }

    public Permission(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlatformType getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformType platform) {
        this.platform = platform;
    }


    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(Integer displaySort) {
        this.displaySort = displaySort;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public void setEntityId(SnowflakeIdWorker snowflakeIdWorker) {
        if (StringUtils.isBlank(permissionId)) {
            permissionId = String.valueOf(snowflakeIdWorker.nextId());
        }
    }
}
