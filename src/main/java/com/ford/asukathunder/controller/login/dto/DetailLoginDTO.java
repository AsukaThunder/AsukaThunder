package com.ford.asukathunder.controller.login.dto;

import com.ford.asukathunder.common.entity.role.Permission;
import com.ford.asukathunder.common.entity.role.RolePermissionRef;
import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.common.entity.user.User;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 用户登录信息详情
 * @ClassName: DetailLoginDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 3:15
 **/
@Getter
@Setter
public class DetailLoginDTO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码
     */
    private String mobilePhone;
    /**
     * 是否启用
     */
    private Boolean isUse;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * token令牌
     */
    private String token;
    /**
     * 是否是管理员
     */
    private Boolean isAdmin;
    /**
     * 用户角色
     */
    private List<DetailUserRoleDTO> roles;
    /**
     * 用户权限
     */
    private List<DetailLoginPermissionDTO> permissions;

    public User convertTo() {
        DetailUserConverter converter = new DetailUserConverter();
        return converter.convert(this);
    }

    public DetailLoginDTO convertFrom(User user) {
        DetailUserConverter converter = new DetailUserConverter();
        return converter.reverse().convert(user);
    }


    private static class DetailUserConverter extends Converter<DetailLoginDTO, User> {

        @Override
        protected User doForward(DetailLoginDTO dto) {
            return null;
        }

        @Override
        protected DetailLoginDTO doBackward(User user) {
            DetailLoginDTO dto = new DetailLoginDTO();
            dto.setUserId(user.getUserId());
            dto.setAccount(user.getAccount());
            dto.setNickName(user.getNickname());
            dto.setMobilePhone(user.getMobilePhone());
            dto.setRealName(user.getRealName());
            dto.setIsUse(user.getIsUse());
            dto.setEmail(user.getEmail());
            dto.setGender(user.getGender());
            List<UserRoleRef> userRoleRefs = user.getUserRoleRef();
            List<RolePermissionRef> rolePermissionRefs = new ArrayList<>();
            if (!CollectionUtils.isEmpty(userRoleRefs)) {
                dto.setRoles(userRoleRefs.stream()
                        .filter(ref -> ref.getRole().getEnable())
                        .map(ref -> new DetailUserRoleDTO().convertFrom(ref)).collect(Collectors.toList()));

                for (UserRoleRef ref : userRoleRefs) {
                    rolePermissionRefs.addAll(ref.getRole().getRolePermissionRef());
                }
                List<Permission> permissions = rolePermissionRefs.stream()
                        .filter(rolePermissionRef -> rolePermissionRef.getRole().getEnable())
                        .map(RolePermissionRef::getPermission).collect(Collectors.toList());
                dto.setPermissions(permissions
                        .stream()
                        .filter(permission -> permission.getEnable())
                        .map(permission -> new DetailLoginPermissionDTO().convertFrom(permission))
                        .collect(Collectors.collectingAndThen(
                                Collectors.toCollection(
                                        () -> new TreeSet<>(Comparator.comparing(DetailLoginPermissionDTO::getPermissionCode))
                                ), ArrayList::new)));
            }

            dto.setIsAdmin(user.isAdmin() || user.isRoot());
            return dto;
        }
    }

}
