package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.entity.role.RolePermissionRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: RolePermissionRefRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:23
 **/
public interface RolePermissionRefRepository extends JpaRepository<RolePermissionRef, String>, JpaSpecificationExecutor<RolePermissionRef> {

    List<RolePermissionRef> findByRole(Role role);
}
