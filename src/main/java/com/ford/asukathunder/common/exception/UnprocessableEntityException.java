package com.ford.asukathunder.common.exception;

import com.ford.asukathunder.util.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  * methods:[POST/PUT/PATCH]
 *  * 当创建一个对象时，发生一个验证错误。
 *  * status:422
 * @ClassName: UnprocessableEntityException
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:23
 **/
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends BaseException {

    public UnprocessableEntityException() {
    }

    public UnprocessableEntityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
