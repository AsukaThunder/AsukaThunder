package com.ford.asukathunder.controller.role.dto;

import com.ford.asukathunder.common.entity.role.Permission;
import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.entity.role.RolePermissionRef;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: DetailRoleDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:17
 **/
@Getter
@Setter
public class DetailRoleDTO implements Serializable {

    private String roleId;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private Boolean enable;

    private Boolean canDelete;

    /**
     * 相关联的权限列表
     */
    private List<String> permissionIds;

    public Role convertTo() {
        GetRoleDTO getRoleDTO = new GetRoleDTO();
        return getRoleDTO.convert(this);
    }

    public DetailRoleDTO convertFrom(Role role) {
        GetRoleDTO getRoleDTO = new GetRoleDTO();
        return getRoleDTO.reverse().convert(role);
    }

    private static class GetRoleDTO extends Converter<DetailRoleDTO, Role> {

        @Override
        protected Role doForward(DetailRoleDTO detailRoleDTO) {
            return null;
        }

        @Override
        protected DetailRoleDTO doBackward(Role role) {
            DetailRoleDTO detailRoleDTO = new DetailRoleDTO();
            detailRoleDTO.setRoleId(role.getRoleId());
            detailRoleDTO.setRoleCode(role.getRoleCode());
            detailRoleDTO.setRoleName(role.getRoleName());
            detailRoleDTO.setRoleDesc(role.getDescription());
            detailRoleDTO.setEnable(role.getEnable());
            detailRoleDTO.setCanDelete(role.getCanDelete());

            List<Permission> permissions = role.getRolePermissionRef().stream().map(RolePermissionRef::getPermission).collect(Collectors.toList());
            detailRoleDTO.setPermissionIds(permissions.stream().map(Permission::getPermissionId).collect(Collectors.toList()));
            return detailRoleDTO;
        }
    }

}
