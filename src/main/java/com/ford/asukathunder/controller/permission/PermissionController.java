package com.ford.asukathunder.controller.permission;

import com.ford.asukathunder.common.encrypt.anno.Encrypt;
import com.ford.asukathunder.common.entity.role.Permission;
import com.ford.asukathunder.common.exception.InvalidRequestException;
import com.ford.asukathunder.common.exception.ResourceNotFoundException;
import com.ford.asukathunder.common.exception.UnprocessableEntityException;
import com.ford.asukathunder.controller.permission.dto.DetailPermissionDTO;
import com.ford.asukathunder.controller.permission.dto.PermissionTreeItemDTO;
import com.ford.asukathunder.controller.permission.dto.SavePermissionDTO;
import com.ford.asukathunder.controller.permission.dto.UpdatePermissionDTO;
import com.ford.asukathunder.service.PermissionService;
import com.ford.asukathunder.util.ErrorCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: PermissionController
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:37
 **/
@RestController
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @ApiOperation("查询权限列表")
    @GetMapping(value = "/v1/permissions", produces = "application/json")
    @Encrypt
    public List<PermissionTreeItemDTO> getPermissionTree() {
        List<Permission> permissions = permissionService.getPermissionTree();
        if (CollectionUtils.isEmpty(permissions)) {
            return null;
        }else {
            return permissions.stream().map(permission -> new PermissionTreeItemDTO().convertFrom(permission)).collect(Collectors.toList());
        }
    }

    /**
     * 根据权限ID，获取单个权限信息
     */
    @ApiOperation(value = "获取权限", produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/v1/permissions/{permissionId}", produces = "application/json;charset=UTF-8")
    @Encrypt
    public DetailPermissionDTO findOnePermission(@PathVariable String permissionId) {
        Permission permission = permissionService.findById(permissionId);
        if (null == permission) {
            throw new ResourceNotFoundException(ErrorCode.PermissionNotFound);
        }
        return new DetailPermissionDTO().convertFrom(permission);
    }

    /**
     * 新增权限
     */
    @ApiOperation(value = "新增权限", produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/v1/permissions", produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPermission(@Validated @RequestBody SavePermissionDTO dto){
        Permission dbPermission = permissionService.findByCode(dto.getPermissionCode());
        if (null != dbPermission){
            throw new InvalidRequestException(ErrorCode.PermissionCodeDuplicate);
        }
        Permission permission = dto.convertTo();
        permissionService.addPermission(permission);
    }

    /**
     * 修改权限
     */
    @ApiOperation(value = "修改权限", produces = "application/json;charset=UTF-8")
    @PutMapping(value = "/v1/permissions/{permissionId}", produces = "application/json;charset=UTF-8")
    public void updatePermission(@PathVariable String permissionId, @Validated @RequestBody UpdatePermissionDTO dto){
        Permission permission = permissionService.findById(permissionId);
        if (null == permission){
            throw new ResourceNotFoundException(ErrorCode.PermissionNotFound);
        }
        Permission codePermission = permissionService.findByCode(dto.getPermissionCode(), permissionId);
        if (null != codePermission){
            throw new InvalidRequestException(ErrorCode.PermissionCodeDuplicate);
        }
        permissionService.updatePermission(permission, dto.convertTo());
    }

    /**
     * 禁用某个权限及其子权限
     */
    @ApiOperation(value = "禁用某个权限", produces = "application/json;charset=UTF-8")
    @PutMapping(value = "/v1/permissions/{permissionId}", params = "action=disable", produces = "application/json;charset=UTF-8")
    public void disablePermission(@PathVariable String permissionId) {
        Permission dbPermission = permissionService.findById(permissionId);
        if (null == dbPermission) {
            throw new ResourceNotFoundException(ErrorCode.PermissionNotFound);
        }
        if (!dbPermission.getEnable()) {
            //已经禁用了不可重复禁用
            throw new UnprocessableEntityException(ErrorCode.CurrentPermissionDisable);
        }
        permissionService.disablePermission(dbPermission);
    }

    /**
     * 启用某个权限及其子权限
     */
    @ApiOperation(value = "启用权限", produces = "application/json;charset=UTF-8")
    @PutMapping(value = "/v1/permissions/{permissionId}", params = "action=enable", produces = "application/json;charset=UTF-8")
    public void enablePermission(@PathVariable String permissionId) {
        Permission dbPermission = permissionService.findById(permissionId);
        if (null == dbPermission) {
            throw new ResourceNotFoundException(ErrorCode.PermissionNotFound);
        }
        if (dbPermission.getEnable()) {
            //已经启用则无法重复启用
            throw new UnprocessableEntityException(ErrorCode.CurrentPermissionEnable);
        }
        permissionService.enablePermission(dbPermission);
    }

    /**
     * 删除某个权限及其子权限
     */
    @ApiOperation(value = "删除权限", produces = "application/json;charset=UTF-8")
    @DeleteMapping(value = "/v1/permissions/{permissionId}", produces = "application/json;charset=UTF-8")
    public void deletePermission(@PathVariable String permissionId) {
        Permission permission = permissionService.findById(permissionId);
        if (null == permission) {
            throw new ResourceNotFoundException(ErrorCode.PermissionNotFound);
        }

        //判断是否可以删除
        if (!permission.getCanDelete()) {
            throw new UnprocessableEntityException(ErrorCode.PermissionCannotDelete);
        }

        if (!CollectionUtils.isEmpty(permission.getRolePermissionRef())) {
            throw new InvalidRequestException(ErrorCode.PermissionIsBoundWithRole);
        }
        permissionService.deleteById(permissionId);
    }
}

