package com.ford.asukathunder.common.config;

/**
 * 响应code
 * @ClassName: ResultCode
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/10 上午 9:50
 **/
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(0, "ok"),
    /**
     *
     */
    NOTICE(1, "notice"),
    /**
     * 失败
     */
    ERROR(-1, "unknown exception");

    private int code;
    private String value;

    private ResultCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
