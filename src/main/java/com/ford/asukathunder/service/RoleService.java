package com.ford.asukathunder.service;

import com.ford.asukathunder.common.entity.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: RoleService
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:19
 **/
public interface RoleService {
    /**
     * 查找所有角色
     */
    List<Role> getKeduRoles();

    /**
     * 角色页面
     */
    Page<Role> getRolePage(Pageable pageable, String roleName, String roleCode);

    /**
     * 查找单个角色
     */
    Role findOne(String roleId);

    /**
     * 添加角色
     */
    void addRole(Role role);

    /**
     * 更新角色信息
     */
    void updateRole(Role role, String roleId);

    /**
     * 删除一个角色
     */
    void deleteById(String roleId);

    /**
     * 修改角色权限关联关系
     *
     * @param roleId
     * @param permissionIds
     */
    void updateRolePermission(String roleId, List<String> permissionIds);

}
