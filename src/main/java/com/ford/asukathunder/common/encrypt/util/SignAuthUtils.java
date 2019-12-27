package com.ford.asukathunder.common.encrypt.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: SignAuthUtils
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
public class SignAuthUtils {

    /**
     * @param signTime       (分钟)
     * @return map {
     * access: true/false
     * message: 1.非法请求：缺少签名信息
     * 2.非法请求：已过期
     * 3.非法请求：参数被串改
     * 4.非法请求：验证sign出错
     * }
     * header : sign: {
     * md5:md5{
     * 1.url参数，顺序保持一致
     * 2.body ，顺序保持一致
     * 3.signTime
     * 4.顺序必须保持一致1  》 2 》 3
     * }
     * signTime: 当前时间
     * } // sign 是加密的
     */
    public static Map<String, Object> doFilter(ServletRequest servletRequest, String decryptKey, long signTime) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("access", false);
        maps.put("message", "");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String sign = request.getHeader("sign");
        if (StringUtils.isBlank(sign)) {
            maps.put("message", "非法请求：缺少签名信息");
            return maps;
        }

        /**
         * 验证sign
         * 1.根据解密Key 解密，得到sign Map 对象
         * 2.判断请求时间是否过期
         * 3.判断参数是否被串改
         */

        try {
            // 1
            String decryptSign = AesEncryptUtils.aesDecrypt(sign, decryptKey);
            Map<String, Object> signInfo = JsonUtils.getMapper().readValue(decryptSign, Map.class);

            // 2
            // 签名时间和服务器时间相差10分钟以上则认为是过期请求，此时间可以配置
            Long reqSignTime = (Long) signInfo.get("signTime");
            if ((System.currentTimeMillis() - reqSignTime) > signTime * 60000) {
                maps.put("message", "非法请求：已过期");
                return maps;
            }

            //3
            // 获取请求md5参数
            String md5 = (String) signInfo.get("md5");
            // 获取所有servlet请求参数以及body参数再用md5加密
            StringBuilder sb = new StringBuilder();
            // 所有url 参数值
            Map<String, String[]> params = request.getParameterMap();
            if (params != null) {
                Iterator<Map.Entry<String, String[]>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String[]> entry = it.next();
                    String[] vals = entry.getValue();
                    for (String str : vals) {
                        sb.append(str);
                    }
                }
            }
            // body 值
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            StringBuilder body = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            // 前端传来的空对象特殊处理{}
            String content = body.toString();
            if (StringUtils.isNotBlank(content) && !"{}".equals(content)) {
                sb.append(content);
            }
            // 签名时间
            sb.append(reqSignTime);
            // 对比md5值是否相同
            String nmd5 = MD5EncryptUtils.md5Encrypt(sb.toString()).toLowerCase();
            if (!md5.equals(nmd5)) {
                maps.put("message", "非法请求：参数被串改");
                return maps;
            }
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("message", "非法请求：验证sign出错");
            return maps;
        }

        // 签名验证通过
        maps.put("access", true);
        return maps;
    }

}
