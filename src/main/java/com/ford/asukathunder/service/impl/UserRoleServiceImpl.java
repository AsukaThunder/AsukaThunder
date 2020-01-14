package com.ford.asukathunder.service.impl;

import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.repository.UserRoleRefRepository;
import com.ford.asukathunder.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: UserRoleServiceImpl
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:50
 **/
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    UserRoleRefRepository userRoleRefRepository;

    @Override
    public List<UserRoleRef> getByUserId(String userId) {
        return userRoleRefRepository.getByUserId(userId);
    }

}
