package com.ford.asukathunder.controller.permission.dto;

import com.ford.asukathunder.common.entity.role.Permission;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName: SavePermissionDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:37
 **/
@Getter
@Setter
public class SavePermissionDTO implements Serializable{
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
        AddPermissionConverter addPermissionConverter = new AddPermissionConverter();
        return addPermissionConverter.convert(this);
    }

    public SavePermissionDTO convertFrom(Permission permission) {
        AddPermissionConverter addPermissionConverter = new AddPermissionConverter();
        return addPermissionConverter.reverse().convert(permission);
    }

    private static class AddPermissionConverter extends Converter<SavePermissionDTO, Permission> {

        @Override
        protected Permission doForward(SavePermissionDTO savePermissionDTO) {
            Permission permission = new Permission();
            permission.setEnable(true);
            permission.setCanDelete(true);
            permission.setDescription(savePermissionDTO.getDescription());
            permission.setDisplaySort(savePermissionDTO.getDisplaySort());
            permission.setPlatform(Permission.PlatformType.valueOf(savePermissionDTO.getPlatformTypeCode()));
            permission.setPermissionCode(savePermissionDTO.getPermissionCode());
            permission.setPermissionName(savePermissionDTO.getPermissionName());
            permission.setResourceUrl(savePermissionDTO.getResourceUrl());
            permission.setResourceType(Permission.ResourceType.valueOf(savePermissionDTO.getResourceType()));
            Permission parent = new Permission();
            parent.setPermissionId(savePermissionDTO.getParentPermissionId());
            permission.setParentPermission(parent);
            return permission;
        }

        @Override
        protected SavePermissionDTO doBackward(Permission permission) {
            SavePermissionDTO savePermissionDTO = new SavePermissionDTO();
            savePermissionDTO.setEnable(permission.getEnable());
            savePermissionDTO.setDescription(permission.getDescription());
            savePermissionDTO.setDisplaySort(permission.getDisplaySort());
            savePermissionDTO.setPermissionCode(permission.getPermissionCode());
            savePermissionDTO.setPermissionId(permission.getPermissionId());
            savePermissionDTO.setPermissionName(permission.getPermissionName());
            savePermissionDTO.setResourceUrl(permission.getResourceUrl());
            savePermissionDTO.setResourceType(permission.getResourceType().name());
            return savePermissionDTO;
        }
    }
}

