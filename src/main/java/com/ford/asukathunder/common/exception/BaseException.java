package com.ford.asukathunder.common.exception;

import com.ford.asukathunder.util.ErrorCode;

/**
 * 异常基类
 * @ClassName: BaseException
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:04
 **/
public abstract class BaseException extends RuntimeException{
    /**
     * 错误码
     */
    private ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
