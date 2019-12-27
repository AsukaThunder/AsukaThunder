package com.ford.asukathunder.common.encrypt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: MD5EncryptUtils
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
public class MD5EncryptUtils {

    public static String  md5Encrypt(String string){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = string.getBytes();
            byte[] buff = md.digest(input);
            return HexUtils.bytes2Hex(buff);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
