package com.ford.asukathunder.controller.login.dto;

/**
 * @ClassName: LoginDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:50
 **/
public class LoginDTO {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    private boolean fromMobile = false;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFromMobile() {
        return fromMobile;
    }

    public void setFromMobile(boolean fromMobile) {
        this.fromMobile = fromMobile;
    }
}
