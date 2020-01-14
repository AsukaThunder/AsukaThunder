package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.common.entity.user.User;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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

    @NotNull(message = "昵称不能为空")
    private String nickName;

    @NotNull(message = "真实姓名不能为空")
    private String realName;

    @NotNull(message = "手机号不能为空")
    private String mobilePhone;

    private Boolean isUse;

    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 星座
     */
    private String constellation;

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
            user.setNickname(dto.getNickName());
            user.setBirthday(dto.getBirthday());
            if (dto.getBirthday() != null) {
                user.setAge(SaveUserDTO.getAgeByBirthDay(dto.getBirthday()));
                user.setConstellation(SaveUserDTO.getConstellation(dto.getBirthday()));
            }
            user.setRealName(dto.getRealName());
            user.setAvatar(dto.getAvatar());
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
