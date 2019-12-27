package com.ford.asukathunder.service;

import com.ford.asukathunder.common.entity.role.Permission;
import com.ford.asukathunder.common.entity.user.User;

import java.util.List;

/**
 * 权限相关方法
 * @ClassName: PermissionService
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:54
 **/
public interface PermissionService {

    /**
     * 增加权限
     * */
    void addPermission(Permission permission);

    /**
     * 根据ID查询权限
     * */
    Permission findById(String permissionId);

    /**
     * 根据权限编码查找权限
     * */
    Permission findByCode(String permissionCode);

    /**
     * 根据权限编码和id查找权限
     * */
    Permission findByCode(String permissionCode, String permissionId);

    /**
     * 权限管理-关系权限树
     * */
    List<Permission> getPermissionTree();

    /**
     * 更新权限
     * */
    void updatePermission(Permission dbPermission,Permission newPermission);

    /**
     * 禁用某个权限及其子权限
     * */
    void disablePermission(Permission dbPermission);

    /**
     * 启用某个权限及其子权限
     * */
    void enablePermission(Permission dbPermission);

    /**
     * 根据ID删除权限
     * */
    void deleteById(String permissionId);

    /**
     * 查询用户是否拥有某个权限
     */
    Boolean isPermission(User user, String permissionCode);
}
