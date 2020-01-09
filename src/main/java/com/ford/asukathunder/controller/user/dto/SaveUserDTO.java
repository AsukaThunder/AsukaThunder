package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.common.entity.user.User;
import com.ford.asukathunder.util.CheckoutUtil;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SaveUserDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:47
 **/
@Getter
@Setter
public class SaveUserDTO {

    private String userId;

    @NotNull(message = "账号不能为空")
    private String account;

    @NotNull(message = "真实姓名不能为空")
    private String realName;

    @NotNull(message = "手机号不能为空")
    private String mobilePhone;

    private Boolean isUse;

    private Integer gender;

    private String officePhone;

    private String email;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "角色不能为空")
    private List<String> roleIds;

    public User convertTo() {
        SaveUserConverter converter = new SaveUserConverter();
        return converter.convert(this);
    }

    public SaveUserDTO convertFrom(User user) {
        SaveUserConverter converter = new SaveUserConverter();
        return converter.reverse().convert(user);
    }

    private static class SaveUserConverter extends Converter<SaveUserDTO, User> {

        @Override
        protected User doForward(SaveUserDTO dto) {
            User user = new User();
            user.setRealName(dto.getRealName());
            user.setAccount(dto.getAccount());
            user.setMobilePhone(dto.getMobilePhone());
            user.setRealName(dto.getRealName());
            user.setIsUse(dto.getIsUse());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setPassword(CheckoutUtil.md5(dto.getPassword()));

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
        protected SaveUserDTO doBackward(User user) {
            return null;
        }
    }

    public static String getConstellation(int month, int day) {
        String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
        int[] dayArr = {22, 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
        return day < dayArr[month] ? starArr[month - 1] : starArr[month];
    }
}
