package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.user.User;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: UpdatePersonalUserDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/15 上午 11:03
 **/
@Getter
@Setter
public class UpdatePersonalUserDTO {

    @NotNull(message = "userId不能为空")
    private String userId;

    @NotNull(message = "昵称不能为空")
    private String nickName;

    @NotNull(message = "手机号不能为空")
    private String mobilePhone;

    private Boolean isUse;

    private String motto;
    /**
     * 头像
     */
    private String avatar;

    private Integer gender;

    private String email;

    public User convertTo() {
        UpdatePersonalUserDTO.UpdatePersonalUserConverter converter = new UpdatePersonalUserDTO.UpdatePersonalUserConverter();
        return converter.convert(this);
    }

    public UpdatePersonalUserDTO convertFrom(User user) {
        UpdatePersonalUserDTO.UpdatePersonalUserConverter converter = new UpdatePersonalUserDTO.UpdatePersonalUserConverter();
        return converter.reverse().convert(user);
    }

    private static class UpdatePersonalUserConverter extends Converter<UpdatePersonalUserDTO, User> {

        @Override
        protected User doForward(UpdatePersonalUserDTO dto) {
            User user = new User();
            user.setUserId(dto.getUserId());
            user.setNickname(dto.getNickName());
            user.setAvatar(dto.getAvatar());
            user.setMobilePhone(dto.getMobilePhone());
            user.setIsUse(dto.getIsUse());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setMotto(dto.getMotto());
            return user;
        }

        @Override
        protected UpdatePersonalUserDTO doBackward(User user) {
            return null;
        }
    }
}
