package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.role.UserRoleRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ClassName: UserRoleRefRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/20 下午 4:10
 **/
public interface UserRoleRefRepository extends JpaRepository<UserRoleRef, String>, JpaSpecificationExecutor<UserRoleRef> {

    /**
     * 获取用户角色
     */
    @Query(nativeQuery = true,value = "select * from user_role_ref where user_id=:userId")
    List<UserRoleRef> getByUserId(@Param("userId") String userId);
}
