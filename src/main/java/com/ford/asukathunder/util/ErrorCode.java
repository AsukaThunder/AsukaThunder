package com.ford.asukathunder.util;

/**
 * 错误码枚举
 * @ClassName: ErrorCode
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:05
 **/
public enum ErrorCode {
    /**
     * 错误码详情
     */
    PermissionIsBoundWithRole(41004, "PermissionInUse", "权限和角色绑定，无法删除"),
    CurrentPermissionEnable(41005, "CurrentPermissionEnable", "已经启用的权限不能再次启用"),
    CurrentPermissionDisable(41006, "CurrentPermissionDisable", "已经禁用的权限不能再次禁用"),
    PermissionCodeDuplicate(40007, "PermissionCodeDuplite", "权限码重复"),
    PermissionCannotDelete(40008, "PermissionCannotDelete", "权限无法被删除"),
    PermissionNotFound(40009, "PermissionNotFound", "权限未找到"),
    RoleCodeDulipcate(40010, "RoleCodeDulipcate", "角色已存在"),
    RoleNotFound(40011, "RoleNotFound", "角色未找到"),
    RoleInUse(40012, "RoleInUse", "角色被使用，无法删除"),
    DataConflict(400, "DataConflict", "数据冲突"),
    EntityNotFound(404, "EntityNotFound", "实体类资源没有找到"),
    UserNotFoundException(404, "UserNotFound", "用户找不到"),
    Unauthorized(401, "unauthorized", "未授权"),
    UserOrPasswordError(401, "UserOrPasswordError", "用户名或密码错误"),
    NoRoleEnable(401, "NoRoleEnable", "未分配角色，请联系管理员"),
    TokenAnalysisFailed(401, "TokenAnalysisFailed", "Token解析失败"),
    TokenNotExist(401, "TokenNotExist", "Token不存在，请重新登录"),
    TokenInvalid(401, "TokenInvalid", "Token非法，请重新登录"),
    CodeNotFound(1001, "CodeNotFound", "Code不存在"),
    CodeTodayEmpty(1002, "CodeTodayEmpty", "查询Code时传入的today为空"),
    UsernameDuplicate(1004, "UserNameDuplicate", "用户账号重复"),
    MobilePhoneDuplicate(1005, "MobilePhoneDuplicate", "手机号码重复"),
    PasswordRepeatDifferent(1006, "PasswordRepeatDifferent", "两次密码输入不一致"),
    CannotModifyOtherPwd(1007, "CannotModifyOtherPwd", "无法修改他人密码"),
    OldPasswordError(1008, "OldPasswordError", "旧密码错误"),
    DocNotFound(1047,"DocNotFound","文件未找到"),
    SerialNoDuplicate(1048, "SerialNoDuplicate", "序列号重复"),
    AreaNotFound(1049, "AreaNotFound", "地区不存在");

    private int status;
    private String code;
    private String reason;

    private ErrorCode(int status, String code, String reason) {
        this.status = status;
        this.code = code;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
