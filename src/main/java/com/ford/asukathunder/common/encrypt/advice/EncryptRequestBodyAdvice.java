package com.ford.asukathunder.common.encrypt.advice;


import com.ford.asukathunder.common.encrypt.anno.Decrypt;
import com.ford.asukathunder.common.encrypt.auto.EncryptProperties;
import com.ford.asukathunder.common.encrypt.util.AesEncryptUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 只针对body数据
 * 关于request 解密放入到servletrequest wrapper 中实现
 * @ClassName: EncryptRequestBodyAdvice
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
public class EncryptRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(EncryptRequestBodyAdvice.class);

    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        if (methodParameter.getMethod().isAnnotationPresent(Decrypt.class) && !encryptProperties.isDebug()) {
            try {
                return new DecryptHttpInputMessage(httpInputMessage, encryptProperties.getKey(), encryptProperties.getCharset());
            } catch (Exception e) {
                logger.error("数据解密失败", e);
            }
        }
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    class DecryptHttpInputMessage implements HttpInputMessage {
        private Logger logger = LoggerFactory.getLogger(EncryptRequestBodyAdvice.class);
        private HttpHeaders headers;
        private InputStream body;

        public DecryptHttpInputMessage(HttpInputMessage inputMessage, String key, String charset) throws Exception {
            this.headers = inputMessage.getHeaders();
            String content = IOUtils.toString(inputMessage.getBody(), charset);
            long startTime = System.currentTimeMillis();
            // JSONFormat 数据格式的不进行解密操作
            String decryptBody = "";
            if (content.startsWith("{")) {
                decryptBody = content;
            } else {
                decryptBody = AesEncryptUtils.aesDecrypt(content, key);
            }
            long endTime = System.currentTimeMillis();
            logger.debug("Decrypt Time:" + (endTime - startTime));
            this.body = IOUtils.toInputStream(decryptBody, charset);
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
