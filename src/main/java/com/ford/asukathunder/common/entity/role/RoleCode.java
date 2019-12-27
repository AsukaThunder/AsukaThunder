package com.ford.asukathunder.common.entity.role;

/**
 * 角色code
 * @ClassName: RoleCode
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:50
 **/
public enum  RoleCode {

    /**
     * 超级管理员
     */
    ROOT("超级管理员"),

    /**
     * 管理员
     */
    ADMIN("管理员");

    private String value;

    public String getValue() {
        return value;
    }

    RoleCode(String value) {
        this.value = value;
    }
}
