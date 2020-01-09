package com.ford.asukathunder.controller.role.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName: PageRoleDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:17
 **/
@Getter
@Setter
public class PageRoleDTO implements Serializable{

    private String roleId;

    private String roleCode;

    private String roleName;

    private String roleDesc;

    private Boolean enable;

    private Boolean canDelete;

    public Role convertTo(){
        PageRoleConverter pageRoleConverter = new PageRoleConverter();
        return pageRoleConverter.convert(this);
    }

    public PageRoleDTO convertFrom(Role role){
        PageRoleConverter pageRoleConverter = new PageRoleConverter();
        return pageRoleConverter.reverse().convert(role);
    }

    private static class PageRoleConverter extends Converter<PageRoleDTO,Role> {

        @Override
        protected Role doForward(PageRoleDTO pageRoleDTO) {
            return null;
        }

        @Override
        protected PageRoleDTO doBackward(Role role) {
            PageRoleDTO pageRoleDTO = new PageRoleDTO();
            pageRoleDTO.setRoleId(role.getRoleId());
            pageRoleDTO.setRoleCode(role.getRoleCode());
            pageRoleDTO.setRoleName(role.getRoleName());
            pageRoleDTO.setRoleDesc(role.getDescription());
            pageRoleDTO.setEnable(role.getEnable());
            pageRoleDTO.setCanDelete(role.getCanDelete());
            return pageRoleDTO;
        }
    }
    }
