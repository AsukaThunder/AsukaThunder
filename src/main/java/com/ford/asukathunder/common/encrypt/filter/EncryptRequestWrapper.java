package com.ford.asukathunder.common.encrypt.filter;

import com.ford.asukathunder.common.encrypt.util.AesEncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;
/**
 * @ClassName: EncryptRequestWrapper
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
public class EncryptRequestWrapper extends HttpServletRequestWrapper {

    private final String secretKey;
    private final String charset;
    // 多次读取body值
    private final byte[] body;

    public EncryptRequestWrapper(HttpServletRequest request, String secretKey, String charset) throws IOException {
        super(request);
        this.secretKey = secretKey;
        this.charset = charset;
        body = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public String getParameter(String name) {
        try {
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(super.getParameter(name))) {
                return AesEncryptUtils.aesDecrypt(super.getParameter(name), secretKey);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        // 遍历原始的请求参数
        try {
            for (Map.Entry<String, String[]> m : super.getParameterMap().entrySet()) {
                for (int i = 0; i < m.getValue().length; i++) {
                    // 加密值
                    if (StringUtils.isNotBlank(m.getValue()[i])) {
                        m.getValue()[i] = this.convertNullString(AesEncryptUtils.aesDecrypt(m.getValue()[i], secretKey));
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return super.getParameterMap();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        try {
            for (int i = 0; i < parameterValues.length; i++) {
                if (StringUtils.isNotBlank(parameterValues[i])) {
                    parameterValues[i] = this.convertNullString(AesEncryptUtils.aesDecrypt(parameterValues[i], secretKey));
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return parameterValues;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String content = IOUtils.toString(this.body, charset);
        // JSONFormat 数据格式的不进行解密操作
        String decryptBody = "";
        if (content.startsWith("{")) {
            decryptBody = content;
        } else {
            try {
                decryptBody = AesEncryptUtils.aesDecrypt(content, secretKey);
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
        return new MyServletInputStream(IOUtils.toInputStream(decryptBody, charset));
    }

    /**
     * 处理null字符串
     *
     * @param parameter
     * @return
     */
    private String convertNullString(String parameter) {
        if ("null".equals(parameter) || "undefined".equals(parameter)) {
            return null;
        }
        return parameter;
    }
}
