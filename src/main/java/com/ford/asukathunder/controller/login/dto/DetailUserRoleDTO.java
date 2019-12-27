package com.ford.asukathunder.controller.login.dto;

import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色信息详情
 * @ClassName: DetailUserRoleDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 3:19
 **/
@Setter
@Getter
public class DetailUserRoleDTO {
    /**
     * 用户角色id
     */
    private String userRoleId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色code
     */
    private String roleCode;
    /**
     * 角色名称
     */
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
