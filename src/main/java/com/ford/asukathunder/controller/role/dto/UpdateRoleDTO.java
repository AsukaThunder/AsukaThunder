package com.ford.asukathunder.controller.role.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName: UpdateRoleDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:17
 **/
@Getter
@Setter
public class UpdateRoleDTO implements Serializable{
    private String roleId;
    @NotBlank
    private String roleCode;
    @NotBlank
    private String roleName;
    private String roleDesc;
    private Boolean enable;

    public Role convertTo(){
        UpdateOneRoleDTO updateOneRoleDTO = new UpdateOneRoleDTO();
        return updateOneRoleDTO.convert(this);
    }

    public UpdateRoleDTO convertFrom(Role role){
        UpdateOneRoleDTO updateOneRoleDTO = new UpdateOneRoleDTO();
        return updateOneRoleDTO.reverse().convert(role);
    }

    private static class UpdateOneRoleDTO extends Converter<UpdateRoleDTO,Role> {

        @Override
        protected Role doForward(UpdateRoleDTO updateRoleDTO) {
            Role role = new Role();
            role.setRoleId(updateRoleDTO.getRoleId());
            role.setRoleCode(updateRoleDTO.getRoleCode());
            role.setRoleName(updateRoleDTO.getRoleName());
            role.setDescription(updateRoleDTO.getRoleDesc());
            role.setEnable(updateRoleDTO.getEnable());
            return role;
        }

        @Override
        protected UpdateRoleDTO doBackward(Role role) {
            return null;
        }
    }
}
