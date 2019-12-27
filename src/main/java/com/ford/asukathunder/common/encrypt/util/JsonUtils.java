package com.ford.asukathunder.common.encrypt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
/**
 * @ClassName: JsonUtils
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
public class JsonUtils {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static String toString(Object obj) {
        return toJson(obj);
    }

    public static String toJson(Object obj) {
        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, obj);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象【" + obj + "】时出错", e);
        }
    }

    public static <T> T toBean(Class<T> entityClass, String jsonString) {
        try {
            return mapper.readValue(jsonString, entityClass);
        } catch (Exception e) {
            throw new RuntimeException("JSON【" + jsonString + "】转对象时出错", e);
        }
    }

    /**
     * 用于对象通过其他工具已转为JSON的字符形式，这里不需要再加上引号
     *
     * @param obj
     * @return
     */
    public static String getJsonSuccess(Object obj) {
        String jsonString = null;
        if (obj == null) {
            jsonString = "{\"success\":true}";
        } else {
            jsonString = "{\"success\":true,\"data\":" + toJson(obj) + "}";
        }
        return jsonString;
    }

    public static String getJsonSuccess(Object obj, String message) {
        if (obj == null) {
            return "{\"success\":true,\"message\":\"" + message + "\"}";
        } else {
            try {
                return "{\"success\":true," + toString(obj) + ",\"message\":\"" + message + "\"}";
            } catch (Exception e) {
                throw new RuntimeException("序列化对象【" + obj + "】时出错", e);
            }
        }
    }

    public static String getJsonError(Object obj) {
        return getJsonError(obj, null);
    }

    public static String getJsonError(Object obj, String message) {
        if (obj == null) {
            return "{\"success\":false,\"message\":\"" + message + "\"}";
        } else {
            try {
                obj = parseIfException(obj);
                return "{\"success\":false,\"data\":" + toString(obj) + ",\"message\":\"" + message + "\"}";
            } catch (Exception e) {
                throw new RuntimeException("序列化对象【" + obj + "】时出错", e);
            }
        }
    }

    public static Object parseIfException(Object obj) {
        if (obj instanceof Exception) {
            return getErrorMessage((Exception) obj, null);
        }
        return obj;
    }

    public static String getErrorMessage(Exception e, String defaultMessage) {
        return defaultMessage != null ? defaultMessage : null;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

}
