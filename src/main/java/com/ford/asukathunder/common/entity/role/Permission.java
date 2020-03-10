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
 * 系统用户权限表
 * @ClassName: Permission
 * @author :Ford.Zhang
 * @version: 1.0
 * 2019/12/26 下午 6:00
 */
@Entity
@Table(name = "permission")
@Getter
@Setter
@DynamicUpdate
public class Permission extends BaseEntity {

    /**
     * 权限类型
     */
    public enum PlatformType {
        /**
         * 电脑端
         */
        PC("电脑端"),

        /**
         * 手机端
         */
        MOBILE("手机端");

        private final String value;

        PlatformType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * 资源类型
     */
    public enum ResourceType {
        /**
         * 菜单
         */
        MENU("菜单"),

        /**
         * 元素
         */
        ELEMENT("元素"),

        /**
         * 数据
         */
        DATA("数据");

        private final String value;

        ResourceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 权限id
     */
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

    /**
     * 展示排序
     */
    @Column(name = "display_sort")
    private Integer displaySort;

    /**
     * 是否可用
     */
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

    @Override
    public void setEntityId(SnowflakeIdWorker snowflakeIdWorker) {
        if (StringUtils.isBlank(permissionId)) {
            permissionId = String.valueOf(snowflakeIdWorker.nextId());
        }
    }
}
