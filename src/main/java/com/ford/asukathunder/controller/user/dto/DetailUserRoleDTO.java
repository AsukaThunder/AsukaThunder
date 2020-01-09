package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: DetailUserRoleDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:47
 **/
@Getter
@Setter
public class DetailUserRoleDTO {

    private String userRoleId;

    private String userId;

    private String roleId;

    private String roleCode;

    private String roleName;

    public UserRoleRef convertTo() {
        DetailUserRoleConverter converter = new DetailUserRoleConverter();
        return converter.convert(this);
    }

    public DetailUserRoleDTO convertFrom(UserRoleRef ref) {
        DetailUserRoleConverter converter = new DetailUserRoleConverter();
        return converter.reverse().convert(ref);
    }

    private static class DetailUserRoleConverter extends Converter<DetailUserRoleDTO, UserRoleRef> {

        @Override
        protected UserRoleRef doForward(DetailUserRoleDTO dto) {
            return null;
        }

        @Override
        protected DetailUserRoleDTO doBackward(UserRoleRef ref) {
            DetailUserRoleDTO dto = new DetailUserRoleDTO();
            dto.setRoleId(ref.getRole().getRoleId());
            dto.setRoleCode(ref.getRole().getRoleCode());
            dto.setRoleName(ref.getRole().getRoleName());
            dto.setUserId(ref.getUser().getUserId());
            dto.setUserRoleId(ref.getUserRoleId());
            return dto;
        }
    }
}
