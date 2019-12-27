package com.ford.asukathunder.controller.login.dto;

import com.ford.asukathunder.common.entity.role.Permission;
import com.google.common.base.Converter;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限详情
 * @ClassName: DetailLoginPermissionDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 3:25
 **/
@Data
public class DetailLoginPermissionDTO implements Serializable {
    /**
     * 权限id
     */
    private String permissionId;
    /**
     * 权限code
     */
    private String permissionCode;
    /**
     * 权限名称
     */
    private String permissionName;

    public Permission convertTo() {
        DetailPermissionConverter detailPermissionConverter = new DetailPermissionConverter();
        return detailPermissionConverter.convert(this);
    }

    public DetailLoginPermissionDTO convertFrom(Permission permission) {
        DetailPermissionConverter detailPermissionConverter = new DetailPermissionConverter();
        return detailPermissionConverter.reverse().convert(permission);
    }

    private static class DetailPermissionConverter extends Converter<DetailLoginPermissionDTO, Permission> {

        @Override
        protected Permission doForward(DetailLoginPermissionDTO detailPermissionDTO) {
            return null;
        }

        @Override
        protected DetailLoginPermissionDTO doBackward(Permission permission) {
            DetailLoginPermissionDTO dto = new DetailLoginPermissionDTO();
            dto.setPermissionCode(permission.getPermissionCode());
            dto.setPermissionId(permission.getPermissionId());
            dto.setPermissionName(permission.getPermissionName());
            return dto;
        }
    }
}
