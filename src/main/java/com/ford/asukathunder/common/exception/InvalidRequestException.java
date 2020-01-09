package com.ford.asukathunder.common.exception;

import com.ford.asukathunder.util.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * methods:['POST','PUT','PATCH']
 * 用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
 * status:400
 * @ClassName: InvalidRequestException
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:45
 **/
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends BaseException {

    public InvalidRequestException() {
    }

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
