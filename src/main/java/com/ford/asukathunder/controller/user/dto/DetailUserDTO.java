package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.user.User;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: DetailUserDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:47
 **/
@Getter
@Setter
public class DetailUserDTO {

    private String userId;

    private String account;

    private String nickName;

    private String realName;

    private String mobilePhone;

    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 头像
     */
    private String avatar;

    private String motto;

    private Boolean isUse;

    private Integer gender;

    private String email;

    private String token;

    private List<DetailUserRoleDTO> roles;

    /**
     * 返回给前端判断能否更改角色和停用启用状态
     */
    private Boolean canEdit;

    public User convertTo() {
        DetailUserConverter converter = new DetailUserConverter();
        return converter.convert(this);
    }

    public DetailUserDTO convertFrom(User user) {
        DetailUserConverter converter = new DetailUserConverter();
        return converter.reverse().convert(user);
    }


    private static class DetailUserConverter extends Converter<DetailUserDTO, User> {

        @Override
        protected User doForward(DetailUserDTO dto) {
            return null;
        }

        @Override
        protected DetailUserDTO doBackward(User user) {
            DetailUserDTO dto = new DetailUserDTO();
            dto.setUserId(user.getUserId());
            dto.setAccount(user.getAccount());
            dto.setNickName(user.getNickname());
            dto.setBirthday(user.getBirthday());
            dto.setAvatar(user.getAvatar());
            dto.setMobilePhone(user.getMobilePhone());
            dto.setRealName(user.getRealName());
            dto.setIsUse(user.getIsUse());
            dto.setMotto(user.getMotto());
            dto.setEmail(user.getEmail());
            dto.setGender(user.getGender());
            dto.setRoles(user.getUserRoleRef()
                    .stream()
                    .filter(ref -> ref.getRole().getEnable())
                    .map(ref -> new DetailUserRoleDTO().convertFrom(ref))
                    .collect(Collectors.toList()));
            return dto;
        }
    }
}
