package com.ford.asukathunder.controller.permission.dto;

import com.ford.asukathunder.common.entity.role.Permission;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName: DetailPermissionDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:37
 **/
@Getter
@Setter
public class DetailPermissionDTO implements Serializable{
    private String permissionId;
    private String parentPermissionId;
    private String permissionCode;
    private String permissionName;
    private String platformTypeCode;
    private String resourceType;
    private String description;
    private Integer displaySort;
    private Boolean enable;
    private Boolean canDel;
    private String resourceUrl;

    public Permission convertTo(){
        DetailPermissionConverter detailPermissionConverter = new DetailPermissionConverter();
        return detailPermissionConverter.convert(this);
    }
    public DetailPermissionDTO convertFrom(Permission permission){
        DetailPermissionConverter detailPermissionConverter = new DetailPermissionConverter();
        return detailPermissionConverter.reverse().convert(permission);
    }

    private static class DetailPermissionConverter extends Converter<DetailPermissionDTO,Permission> {

        @Override
        protected Permission doForward(DetailPermissionDTO detailPermissionDTO) {
            return null;
        }

        @Override
        protected DetailPermissionDTO doBackward(Permission permission) {
            DetailPermissionDTO dto = new DetailPermissionDTO();
            dto.setEnable(permission.getEnable());
            dto.setDescription(permission.getDescription());
            dto.setDisplaySort(permission.getDisplaySort());
            dto.setPermissionCode(permission.getPermissionCode());
            dto.setPermissionId(permission.getPermissionId());
            dto.setPermissionName(permission.getPermissionName());
            dto.setResourceUrl(permission.getResourceUrl());
            dto.setResourceType(permission.getResourceType().name());
            dto.setPlatformTypeCode(permission.getPlatform().name());
            return dto;
        }
    }
}
