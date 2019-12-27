package com.ford.asukathunder.service;

import com.ford.asukathunder.common.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: UserService
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:20
 **/
public interface UserService {

    /**
     * 根据用户名密码查询
     * @param account 用户账号
     * @param password 密码
     * @return User
     */
    User queryByAccountAndPassword(String account, String password);

    /**
     * 根据用户名查询
     * @param account 用户账号
     * @return user
     */
    User queryByAccount(String  account);
    /**
     * 根据id查询
     *
     * @param userId 用户id
     * @return user
     */

    User queryById(String userId);

    /**
     * 查询用户列表
     *
     * @param realName 用户姓名
     * @param pageable 分页
     * @return Page<User>
     */
    Page<User> queryUsers(String realName, String account, String roleId, Pageable pageable);

    /**
     * 用户名是否重复
     *
     * @param account 用户账号
     * @return boolean
     */
    Boolean isAccountDuplicate(String account);

    /**
     * 手机号是否重复
     *
     * @param phone 手机号
     * @return Boolean
     */
    Boolean isPhoneDuplicate(String phone);

    /**
     * 新建用户
     * @param user 用户
     */
    void save(User user);

    /**
     * 更新用户
     *
     * @param dbUser 旧信息
     * @param inputUser 输入的信息
     */
    void update(User dbUser, User inputUser);

    /**
     * 用户名是否重复
     *
     * @param account 用户账号
     * @param userId 用户id
     * @return Boolean
     */
    Boolean isAccountDuplicate(String account, String userId);

    /**
     * 手机号是否重复
     *
     * @param phone 手机号
     * @param userId 用户id
     * @return Boolean
     */
    Boolean isPhoneDuplicate(String phone, String userId);

    /**
     * 判断userId是否合法
     *
     * @param userId 用户id
     * @return user
     */
    User getValidUser(String userId);

    /**
     * 判断当前用户能否编辑
     *
     * @param targetUser 用户
     * @return Boolean
     */
    Boolean getCanEdit(User targetUser);

    /**
     * 判断当前用户能否删除
     *
     * @param targetUser 用户
     * @return Boolean
     */
    Boolean getCanDelete(User targetUser);

    /**
     * 根据角色查询绑定的user
     *
     * @param roleCode 角色code
     * @return list
     */
    List<User> queryUsersByRoleCode(String roleCode);

    /**
     * 删除用户
     * @param user 用户
     */
    void delete(User user);

    /**
     * 查询账号
     * */
    List<String> queryAccount();

    /**
     * 查询所有用户
     * @return list
     */
    List<User> findAllUsers();
}
