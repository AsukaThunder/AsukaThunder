package com.ford.asukathunder.controller.user.dto;


import com.ford.asukathunder.common.entity.user.User;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SimpleUserDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:47
 **/
@Getter
@Setter
public class SimpleUserDTO {

    private String userId;

    private String realName;

    public User convertTo() {
        DetailUserConverter converter = new DetailUserConverter();
        return converter.convert(this);
    }

    public SimpleUserDTO convertFrom(User user) {
        DetailUserConverter converter = new DetailUserConverter();
        return converter.reverse().convert(user);
    }


    private static class DetailUserConverter extends Converter<SimpleUserDTO, User> {

        @Override
        protected User doForward(SimpleUserDTO dto) {
            return null;
        }

        @Override
        protected SimpleUserDTO doBackward(User user) {
            SimpleUserDTO dto = new SimpleUserDTO();
            dto.setUserId(user.getUserId());
            dto.setRealName(user.getRealName());
            return dto;
        }
    }
}
