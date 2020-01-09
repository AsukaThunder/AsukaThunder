package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.user.User;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sam at 2019-04-26 16:52
 */
@Getter
@Setter
public class PageUserDTO {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 账号
     */
    private String account;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机号码
     */
    private String mobilePhone;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 星座
     */
    private String constellation;
    /**
     * 是否启用
     */
    private Boolean isUse;
    /**
     * 角色列表
     */
    private List<String> roles;
    /**
     * 邮箱
     */
    private String email;

    public User convertTo() {
        PageUserConverter converter = new PageUserConverter();
        return converter.convert(this);
    }

    public PageUserDTO convertFrom(User user) {
        PageUserConverter converter = new PageUserConverter();
        return converter.reverse().convert(user);
    }

    private static class PageUserConverter extends Converter<PageUserDTO, User> {

        @Override
        protected User doForward(PageUserDTO dto) {
            return null;
        }

        @Override
        protected PageUserDTO doBackward(User user) {
            PageUserDTO dto = new PageUserDTO();
            dto.setUserId(user.getUserId());
            dto.setAccount(user.getAccount());
            dto.setMobilePhone(user.getMobilePhone());
            dto.setRealName(user.getRealName());
            dto.setNickName(user.getNickname());
            dto.setAvatar(user.getAvatar());
            dto.setIsUse(user.getIsUse());
            dto.setGender(user.getGender());
            dto.setEmail(user.getEmail());
            dto.setAge(user.getAge());
            dto.setBirthday(user.getBirthday());
            dto.setConstellation(user.getConstellation());
            dto.setRoles(user.getUserRoleRef()
                    .stream()
                    .filter(ref -> ref.getRole().getEnable())
                    .map(ref -> ref.getRole().getRoleName())
                    .collect(Collectors.toList()));
            return dto;
        }
    }
}
