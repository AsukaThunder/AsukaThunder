package com.ford.asukathunder.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回值
 * @ClassName: Response
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/10 上午 9:50
 **/
@Getter
@Setter
public class Response implements Serializable {
    /**
     * 默认返回代码 0
     */
    private String code = String.valueOf(ResultCode.SUCCESS.getCode());
    /**
     * 默认返回 ok
     */
    @JSONField(ordinal = 1)
    private String msg = ResultCode.SUCCESS.getValue();
    /**
     * 自定义对象
     */
    @JSONField(ordinal = 2)
    private Object data;

    public Response() {
    }


    public static Response success() {
        Response response = new Response();
        response.code = String.valueOf(ResultCode.SUCCESS.getCode());
        response.msg = ResultCode.SUCCESS.getValue();
        return response;

    }


    public static Response success(Object data) {
        Response response = new Response();
        response.code = String.valueOf(ResultCode.SUCCESS.getCode());
        response.msg = ResultCode.SUCCESS.getValue();
        response.data = data;
        return response;

    }


    public static Response success(String ret, String msg) {
        Response response = new Response();
        response.code = ret;
        response.msg = msg;
        return response;
    }


    public static Response success(String ret, String msg, Object data) {
        Response response = new Response();
        response.code = ret;
        response.msg = msg;
        response.data = data;
        return response;

    }

    public static Response failure() {
        Response response = new Response();
        response.code = String.valueOf(ResultCode.ERROR.getCode());
        response.msg = ResultCode.ERROR.getValue();
        return response;
    }

    public static Response failure(String msg) {
        Response response = new Response();
        response.code = String.valueOf(ResultCode.ERROR.getCode());
        response.msg = msg;
        return response;
    }
    //操作失败

    public static Response failure(String ret, String msg) {
        Response response = new Response();
        response.code = ret;
        response.msg = msg;
        return response;

    }

    public static Response failure(String ret, String msg, Object data) {
        Response response = new Response();
        response.code = ret;
        response.msg = msg;
        response.data = data;
        return response;
    }
}
