package com.ford.asukathunder.service.impl;

import com.ford.asukathunder.common.entity.role.Permission;
import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.entity.role.RolePermissionRef;
import com.ford.asukathunder.common.exception.ResourceNotFoundException;
import com.ford.asukathunder.common.exception.UnprocessableEntityException;
import com.ford.asukathunder.repository.PermissionRepository;
import com.ford.asukathunder.repository.RolePermissionRefRepository;
import com.ford.asukathunder.repository.RoleRepository;
import com.ford.asukathunder.repository.UserRoleRefRepository;
import com.ford.asukathunder.service.RoleService;
import com.ford.asukathunder.util.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RoleServiceImpl
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:20
 **/
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleRepository roleRepository;

    @Resource
    RolePermissionRefRepository refRepository;

    @Resource
    PermissionRepository permissionRepository;

    @Resource
    UserRoleRefRepository userRoleRefRepository;

    /**
     * 查看角色列表
     */
    @Override
    public List<Role> getKeduRoles() {
        return roleRepository.findByEnableTrue();
    }

    /**
     * 查看角色页t
     */
    @Override
    public Page<Role> getRolePage(Pageable pageable, String roleName, String roleCode) {
        Specification<Role> spec = (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotEmpty(roleName)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(roleName).as(String.class), "%" + roleName + "%"));
            }
            if (StringUtils.isNotEmpty(roleCode)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(roleCode).as(String.class), "%" + roleCode + "%"));
            }

            criteriaQuery.distinct(true).where(predicate);
            return criteriaQuery.getRestriction();
        };
        return roleRepository.findAll(spec, pageable);
    }

    /**
     * 查找单个角色
     */

    @Override
    public Role findOne(String roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (null == role) {
            throw new ResourceNotFoundException(ErrorCode.EntityNotFound);
        }
        return role;
    }

    /**
     * 增加一个角色
     */
    @Override
    public void addRole(Role role) {
        Role existRole = roleRepository.findByRoleCode(role.getRoleCode());
        if (existRole != null && existRole.getRoleCode().equals(role.getRoleCode())) {
            throw new UnprocessableEntityException(ErrorCode.RoleCodeDulipcate);
        }
        roleRepository.save(role);
    }

    /**
     * 更新角色
     */
    @Override
    public void updateRole(Role role, String roleId) {
        Role exist = roleRepository.findById(roleId).orElse(null);

        if (null == exist) {
            throw new ResourceNotFoundException(ErrorCode.RoleNotFound);
        }

        // 如果由启用->禁用，需要删除所有绑定该角色的关联关系
        if (exist.getEnable() && !role.getEnable() && !CollectionUtils.isEmpty(exist.getUserRoleRef())) {
            userRoleRefRepository.deleteInBatch(exist.getUserRoleRef());
        }

        exist.setDescription(role.getDescription());
        exist.setRoleCode(role.getRoleCode());
        exist.setRoleName(role.getRoleName());
        exist.setEnable(role.getEnable());

        if (!exist.getRoleId().equals(role.getRoleId())) {
            throw new UnprocessableEntityException(ErrorCode.RoleCodeDulipcate);
        }
        roleRepository.save(exist);
    }

    @Override
    public void deleteById(String roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (null == role) {
            throw new ResourceNotFoundException(ErrorCode.RoleNotFound);
        }
        if (!CollectionUtils.isEmpty(role.getUserRoleRef())) {
            throw new UnprocessableEntityException(ErrorCode.RoleInUse);
        }

        roleRepository.delete(role);
    }

    @Override
    public void updateRolePermission(String roleId, List<String> permissionIds) {
        Role role = this.findOne(roleId);
        if (null == role) {
            throw new ResourceNotFoundException(ErrorCode.RoleNotFound);
        }

        // 获取当前roleId的角色-权限关系List
        List<RolePermissionRef> existRolePermission = refRepository.findByRole(role);

        // 获取需要删除的元素
        List<RolePermissionRef> delItems = this.getDeleteRolePermissions(existRolePermission, permissionIds);
        refRepository.deleteInBatch(delItems);

        // 获取额外勾选(新增)的元素
        List<RolePermissionRef> addItems = this.getAddGlobalRolePermissions(role, permissionIds);
        refRepository.saveAll(addItems);
    }

    /**
     * 获取待删除的关联关系list
     *
     * @param permissionIds id
     * @return list
     */
    private List<RolePermissionRef> getDeleteRolePermissions(List<RolePermissionRef> exist, List<String> permissionIds) {
        List<RolePermissionRef> delItems = new ArrayList<>();
        for (RolePermissionRef rp : exist) {
            boolean isDelete = true;
            if (!CollectionUtils.isEmpty(permissionIds)) {
                for (String permissionId : permissionIds) {
                    if (rp.getPermission().getPermissionId().equals(permissionId)) {
                        isDelete = false;
                        break;
                    }
                }
            }
            if (isDelete) {
                delItems.add(rp);
            }
        }
        return delItems;
    }

    /**
     * 对比传入的permissionIds和数据库内的当前角色关联的permissionId
     * 获取待新增的关联关系list
     *
     * @param role 角色
     * @param permissionIds 权限id
     * @return List
     */
    private List<RolePermissionRef> getAddGlobalRolePermissions(Role role, List<String> permissionIds) {
        List<RolePermissionRef> addItems = new ArrayList<>();
        for (String permissionId : permissionIds) {
            boolean isAdd = true;
            for (RolePermissionRef rp : role.getRolePermissionRef()) {
                if (rp.getPermission().getPermissionId().equals(permissionId)) {
                    isAdd = false;
                    break;
                }
            }
            if (isAdd) {
                RolePermissionRef newRolePermission = new RolePermissionRef();
                Permission p = permissionRepository.findById(permissionId).orElse(null);
                newRolePermission.setPermission(p);
                newRolePermission.setRole(role);
                addItems.add(newRolePermission);
            }
        }
        return addItems;
    }

}
