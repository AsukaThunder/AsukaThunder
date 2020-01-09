package com.ford.asukathunder.controller.role.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName: SaveRoleDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:17
 **/
@Getter
@Setter
public class SaveRoleDTO implements Serializable {
    private String roleId;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    private Boolean enable;

    public Role convertTo() {
        AddOneRoleDTO addOneRoleDTO = new AddOneRoleDTO();
        return addOneRoleDTO.convert(this);
    }

    public SaveRoleDTO convertFrom(Role role) {
        AddOneRoleDTO addOneRoleDTO = new AddOneRoleDTO();
        return addOneRoleDTO.reverse().convert(role);
    }

    private static class AddOneRoleDTO extends Converter<SaveRoleDTO, Role> {

        @Override
        protected Role doForward(SaveRoleDTO saveRoleDTO) {
            Role role = new Role();

            role.setRoleId(saveRoleDTO.getRoleId());
            role.setRoleCode(saveRoleDTO.getRoleCode());
            role.setRoleName(saveRoleDTO.getRoleName());
            role.setDescription(saveRoleDTO.getRoleDesc());
            role.setEnable(saveRoleDTO.getEnable());
            role.setCanDelete(true);
            return role;
        }

        @Override
        protected SaveRoleDTO doBackward(Role role) {
            return null;
        }
    }
}
