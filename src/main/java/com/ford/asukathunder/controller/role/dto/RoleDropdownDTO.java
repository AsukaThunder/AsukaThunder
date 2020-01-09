package com.ford.asukathunder.controller.role.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName: RoleDropdownDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:17
 **/
@Getter
@Setter
public class RoleDropdownDTO implements Serializable {
    private String roleId;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    private Boolean enable;

    public Role convertTo() {
        RoleDropdownDTOConverter converter = new RoleDropdownDTOConverter();
        return converter.convert(this);
    }

    public RoleDropdownDTO convertFrom(Role role) {
        RoleDropdownDTOConverter converter = new RoleDropdownDTOConverter();
        return converter.reverse().convert(role);
    }

    private static class RoleDropdownDTOConverter extends Converter<RoleDropdownDTO, Role> {

        @Override
        protected Role doForward(RoleDropdownDTO detailRoleDTO) {
            return null;
        }

        @Override
        protected RoleDropdownDTO doBackward(Role role) {
            RoleDropdownDTO detailRoleDTO = new RoleDropdownDTO();
            detailRoleDTO.setRoleId(role.getRoleId());
            detailRoleDTO.setRoleCode(role.getRoleCode());
            detailRoleDTO.setRoleName(role.getRoleName());
            detailRoleDTO.setRoleDesc(role.getDescription());
            detailRoleDTO.setEnable(role.getEnable());
            return detailRoleDTO;
        }
    }

}
