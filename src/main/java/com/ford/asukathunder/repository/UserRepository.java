package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName: UserRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:27
 **/
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /**
     * 根据账号密码查询用户
     * @param account 账号
     * @param password 密码
     * @return user
     */
    User findByAccountAndPasswordAndIsDeleteFalseAndIsUseTrue(String account, String password);

    /**
     * 根据账号查询
     * @param account 账号
     * @return user
     */
    User findByAccount(String account);

    /**
     * 根据账号和id查询
     * @param account 账号
     * @param userId id
     * @return user
     */
    User findByAccountAndUserIdNot(String account, String userId);

    /**
     * 根据手机号查询
     * @param mobilePhone 手机号
     * @return user
     */
    User findByMobilePhone(String mobilePhone);

    /**
     * 根据手机号和id查询
     * @param mobilePhone 手机号
     * @param userId id
     * @return user
     */
    User findByMobilePhoneAndUserIdNot(String mobilePhone, String userId);

    /**
     * 根据id查询
     * @param userId id
     * @return user
     */
    User findByUserIdAndIsDeleteFalseAndIsUseTrue(String userId);

    /**
     * 查询所有未删除用户
     * @return list
     */
    List<User> findByIsDeleteFalse();

}
