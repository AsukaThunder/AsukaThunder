package com.ford.asukathunder.controller.permission.dto;

import com.ford.asukathunder.common.entity.role.Permission;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName: UpdatePermissionDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:37
 **/
@Getter
@Setter
public class UpdatePermissionDTO implements Serializable {

    private String permissionId;
    @NotBlank
    private String parentPermissionId;
    @NotBlank
    private String permissionCode;
    @NotBlank
    private String permissionName;
    @NotBlank
    private String platformTypeCode;
    @NotBlank
    private String resourceType;
    private String description;
    private Integer displaySort;
    private Boolean enable;
    private Boolean canDel;
    private String resourceUrl;

    public Permission convertTo() {
        UpdatePermissionConverter updatePermissionConverter = new UpdatePermissionConverter();
        return updatePermissionConverter.convert(this);
    }

    public UpdatePermissionDTO convertFrom(Permission permission) {
        UpdatePermissionConverter updatePermissionConverter = new UpdatePermissionConverter();
        return updatePermissionConverter.reverse().convert(permission);
    }

    private static class UpdatePermissionConverter extends Converter<UpdatePermissionDTO, Permission> {
        @Override
        protected Permission doForward(UpdatePermissionDTO updatePermissionDTO) {
            Permission permission = new Permission();
            permission.setEnable(true);
            permission.setCanDelete(true);
            permission.setDescription(updatePermissionDTO.getDescription());
            permission.setDisplaySort(updatePermissionDTO.getDisplaySort());
            permission.setPlatform(Permission.PlatformType.valueOf(updatePermissionDTO.getPlatformTypeCode()));
            permission.setPermissionCode(updatePermissionDTO.getPermissionCode());
            permission.setPermissionName(updatePermissionDTO.getPermissionName());
            permission.setResourceUrl(updatePermissionDTO.getResourceUrl());
            permission.setResourceType(Permission.ResourceType.valueOf(updatePermissionDTO.getResourceType()));
            Permission parent = new Permission();
            parent.setPermissionId(updatePermissionDTO.getPermissionId());
            permission.setParentPermission(parent);
            return permission;
        }

        @Override
        protected UpdatePermissionDTO doBackward(Permission permission) {
            UpdatePermissionDTO updatePermissionDTO = new UpdatePermissionDTO();
            updatePermissionDTO.setEnable(permission.getEnable());
            updatePermissionDTO.setDescription(permission.getDescription());
            updatePermissionDTO.setDisplaySort(permission.getDisplaySort());
            updatePermissionDTO.setPermissionCode(permission.getPermissionCode());
            updatePermissionDTO.setPermissionId(permission.getPermissionId());
            updatePermissionDTO.setPermissionName(permission.getPermissionName());
            updatePermissionDTO.setResourceUrl(permission.getResourceUrl());
            updatePermissionDTO.setResourceType(permission.getResourceType().name());
            return updatePermissionDTO;
        }
    }
}
