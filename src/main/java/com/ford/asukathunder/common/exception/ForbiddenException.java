package com.ford.asukathunder.common.exception;

import com.ford.asukathunder.util.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  * * methods:[*]
 *  * 表示没有权限访问接口。
 *  * status:403
 * @ClassName: ForbiddenException
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:11
 **/
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseException{
    public ForbiddenException() {
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
