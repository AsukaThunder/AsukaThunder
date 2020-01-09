package com.ford.asukathunder.service;

import com.ford.asukathunder.common.entity.role.UserRoleRef;

import java.util.List;

/**
 * @ClassName: UserRoleService
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:49
 **/
public interface UserRoleService {

    /**
     * 根据UserId查询所有的关联的role信息
     */
    List<UserRoleRef> getByUserId(String userId);

}
