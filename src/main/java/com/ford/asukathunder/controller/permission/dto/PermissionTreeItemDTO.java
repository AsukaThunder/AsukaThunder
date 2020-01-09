package com.ford.asukathunder.controller.permission.dto;

import com.ford.asukathunder.common.entity.role.Permission;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @ClassName: PermissionTreeItemDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:37
 **/
@Getter
@Setter
public class PermissionTreeItemDTO implements Serializable {
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
    private List<PermissionTreeItemDTO> sonItems;

    public Permission convertTO() {
        PermissionTreeItemConverter permissionTreeItemConverter = new PermissionTreeItemConverter();
        return permissionTreeItemConverter.convert(this);
    }

    public PermissionTreeItemDTO convertFrom(Permission permission) {
        PermissionTreeItemConverter permissionTreeItemConverter = new PermissionTreeItemConverter();
        return permissionTreeItemConverter.reverse().convert(permission);
    }

    private static class PermissionTreeItemConverter extends Converter<PermissionTreeItemDTO, Permission> {

        @Override
        protected Permission doForward(PermissionTreeItemDTO permissionTreeItemDTO) {
            return null;
        }

        @Override
        protected PermissionTreeItemDTO doBackward(Permission permission) {
            PermissionTreeItemDTO dto = new PermissionTreeItemDTO();
            dto.setEnable(permission.getEnable());
            dto.setDescription(permission.getDescription());
            dto.setDisplaySort(permission.getDisplaySort());
            dto.setPermissionCode(permission.getPermissionCode());
            dto.setPermissionId(permission.getPermissionId());
            dto.setPermissionName(permission.getPermissionName());
            dto.setResourceUrl(permission.getResourceUrl());
            dto.setResourceType(permission.getResourceType().name());
            dto.setPlatformTypeCode(permission.getPlatform().name());
            dto.setCanDel(permission.getCanDelete());
            if (null != permission.getParentPermission()) {
                dto.setParentPermissionId(permission.getParentPermission().getPermissionId());
            }

            if (!CollectionUtils.isEmpty(permission.getSonPermissions())) {
                dto.setSonItems(permission.getSonPermissions().stream().map(p -> new PermissionTreeItemDTO().convertFrom(p)).collect(Collectors.toList()));
            }
            return dto;
        }
    }
}
