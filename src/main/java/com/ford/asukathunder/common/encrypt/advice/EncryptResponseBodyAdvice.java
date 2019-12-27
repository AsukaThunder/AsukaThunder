package com.ford.asukathunder.common.encrypt.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.asukathunder.common.encrypt.anno.Encrypt;
import com.ford.asukathunder.common.encrypt.auto.EncryptProperties;
import com.ford.asukathunder.common.encrypt.util.AesEncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName: EncryptResponseBodyAdvice
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private Logger logger = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EncryptProperties encryptProperties;

    private final static ThreadLocal<Boolean> encryptLocal = new ThreadLocal<Boolean>();

    public static void setEncryptStatus(boolean status) {
        encryptLocal.set(status);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Boolean status = encryptLocal.get();
        if (status != null && !status) {
            encryptLocal.remove();
            return o;
        }

        long startTime = System.currentTimeMillis();
        boolean encrypt = false;
        if (methodParameter.getMethod().isAnnotationPresent(Encrypt.class) && !encryptProperties.isDebug()) {
            encrypt = true;
        }
        if (encrypt) {
            try {
                String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
                if (!StringUtils.hasText(encryptProperties.getKey())) {
                    throw new NullPointerException("请配置spring.encrypt.key");
                }
                String result = AesEncryptUtils.aesEncrypt(content, encryptProperties.getKey());
                long endTime = System.currentTimeMillis();
                logger.debug("Encrypt Time:" + (endTime - startTime));
                return result;
            } catch (Exception e) {
                logger.error("加密数据异常", e);
            }
        }

        return o;
    }
}
