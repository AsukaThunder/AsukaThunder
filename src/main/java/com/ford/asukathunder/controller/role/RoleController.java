package com.ford.asukathunder.controller.role;

import com.ford.asukathunder.common.encrypt.anno.Encrypt;
import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.exception.ResourceNotFoundException;
import com.ford.asukathunder.controller.role.dto.*;
import com.ford.asukathunder.service.RoleService;
import com.ford.asukathunder.util.ErrorCode;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: RoleController
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:17
 **/
@RestController
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
     * 角色service
     */
    @Resource
    private RoleService roleService;

    /**
     * 获取全部角色
     */
    @ApiOperation(value = "获取全部角色", produces = "application/json")
    @GetMapping(value = "/v1/roles", params = "action=dropdown")
    @Encrypt
    public List<RoleDropdownDTO> getRoles() {
        List<Role> roles = roleService.getKeduRoles();
        return roles.stream().map(role -> new RoleDropdownDTO().convertFrom(role)).collect(Collectors.toList());
    }

    @ApiOperation(value = "获取角色列表", produces = "application/json")
    @GetMapping("/v1/roles")
    @Encrypt
    public Page<PageRoleDTO> getRolePage(@RequestParam(value = "roleName", required = false) String roleName,
                                         @RequestParam(value = "roleCode", required = false) String roleCode,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createTime");
        Page<Role> rolePage = roleService.getRolePage(pageRequest, roleName, roleCode);
        return rolePage.map(role -> new PageRoleDTO().convertFrom(role));
    }

    /**
     * 根据角色ID，获取单个角色信息
     */
    @ApiOperation(value = "获取单个角色-包含关联关系", produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/v1/roles/{roleId}")
    @Encrypt
    public DetailRoleDTO findRole(@PathVariable String roleId) {
        Role role = roleService.findOne(roleId);
        if (null == role) {
            throw new ResourceNotFoundException(ErrorCode.EntityNotFound);
        }
        return new DetailRoleDTO().convertFrom(role);
    }

    /**
     * 新增一个角色
     */
    @ApiOperation(value = "新增一个角色", produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/v1/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRole(@Validated @RequestBody SaveRoleDTO roleDTO) {
        Role addRole = roleDTO.convertTo();
        roleService.addRole(addRole);
    }

    /**
     * 修改角色
     */
    @ApiOperation(value = "修改单个角色-基础信息", produces = "application/json;charset=UTF-8")
    @PutMapping(value = "/v1/roles/{roleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateRole(@PathVariable String roleId, @Validated @RequestBody UpdateRoleDTO roleDTO) {
        Role role = roleDTO.convertTo();
        roleService.updateRole(role, roleId);
    }

    /**
     * 删除一个角色
     */
    @ApiOperation(value = "删除一个角色", produces = "application/json;charset=UTF-8")
    @DeleteMapping(value = "/v1/roles/{roleId}")
    public void deleteRole(@PathVariable String roleId) {
        roleService.deleteById(roleId);
    }

    /**
     * 修改公共角色-权限列表,传入所有选择的permissionId
     *
     * @param roleId 角色id
     * @param permissionIds 权限id列表
     */
    @ApiOperation(value = "修改角色-权限关联,传入所有选择的permissionId", produces = "application/json")
    @PutMapping(value = "/v1/roles/{roleId}", params = "action=permissions", produces = "application/json")
    public void updateRolePermissions(@PathVariable("roleId") String roleId, @RequestBody List<String> permissionIds) {
        roleService.updateRolePermission(roleId, permissionIds);
    }

}

