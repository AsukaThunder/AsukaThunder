package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.common.entity.user.User;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UpdateUserDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:47
 **/
@Getter
@Setter
public class UpdateUserDTO {

    @NotNull(message = "userId不能为空")
    private String userId;

    @NotNull(message = "用户名不能为空")
    private String account;

    @NotNull(message = "真实姓名不能为空")
    private String realName;

    @NotNull(message = "手机号不能为空")
    private String mobilePhone;

    private Boolean isUse;

    private Integer gender;

    private String email;

    private List<String> roleIds;

    public User convertTo() {
        UpdateUserConverter converter = new UpdateUserConverter();
        return converter.convert(this);
    }

    public UpdateUserDTO convertFrom(User user) {
        UpdateUserConverter converter = new UpdateUserConverter();
        return converter.reverse().convert(user);
    }

    private static class UpdateUserConverter extends Converter<UpdateUserDTO, User> {

        @Override
        protected User doForward(UpdateUserDTO dto) {
            User user = new User();
            user.setUserId(dto.getUserId());
            user.setRealName(dto.getRealName());
            user.setAccount(dto.getAccount());
            user.setMobilePhone(dto.getMobilePhone());
            user.setRealName(dto.getRealName());
            user.setIsUse(dto.getIsUse());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());

            // 设置权限
            List<String> roleIds = dto.getRoleIds();
            List<UserRoleRef> userRoleRefs = new ArrayList<>();
            for (String roleId : roleIds) {
                Role role = new Role();
                role.setRoleId(roleId);

                UserRoleRef ref = new UserRoleRef();
                ref.setRole(role);
                ref.setUser(user);
                userRoleRefs.add(ref);
            }

            user.setUserRoleRef(userRoleRefs);
            return user;
        }

        @Override
        protected UpdateUserDTO doBackward(User user) {
            return null;
        }
    }
}
