package com.ford.asukathunder.common.exception;

import com.ford.asukathunder.util.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * methods:[*]
 * 用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
 * status:404
 * @ClassName: ResourceNotFoundException
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 2:09
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
