package com.ford.asukathunder.common.exception;

import com.ford.asukathunder.util.ErrorCode;

/**
 * methods:[*]
 * 表示用户没有登录（令牌、用户名、密码错误）。
 * status:401
 * @ClassName: UnauthorizedException
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 3:46
 **/
public class UnauthorizedException extends BaseException{
    public UnauthorizedException() {
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
