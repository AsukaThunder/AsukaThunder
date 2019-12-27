package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: RoleRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:27
 **/
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    Role findByRoleCode(String roleCode);

    List<Role> findByEnableTrue();

}
