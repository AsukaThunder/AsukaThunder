package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.role.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: PermissionRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:58
 **/
public interface PermissionRepository extends JpaRepository<Permission,String>, JpaSpecificationExecutor<Permission> {
    /**
     * 根据权限code查询
     * @param permissionCode 权限code
     * @return 权限
     */
    Permission findByPermissionCode(String permissionCode);

    /**
     * 根据权限code和id查询
     * @param permissionCode 权限code
     * @param permissionId 权限id
     * @return 权限
     */
    Permission findByPermissionCodeAndPermissionId(String permissionCode, String permissionId);

    /**
     * 查找最高权限
     * @return list<Permission>
     */
    List<Permission> findByParentPermissionIsNull();
}
