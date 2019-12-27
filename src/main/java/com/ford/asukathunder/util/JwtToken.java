package com.ford.asukathunder.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ford.asukathunder.common.exception.UnauthorizedException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @ClassName: JwtToken
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 3:48
 **/
public class JwtToken {
    /**
     * token秘钥，请勿泄露，请勿随便修改
     */
    private static final String SECRET = "archimede==";
    /**
     * token 过期时间: 48小时
     */
    private static final int CALENDARFIELD = Calendar.HOUR;
    private static final int CALENDARINTERVAL = 48;
    /**
     * token 无过期时间，默认99年
     */
    private static final int CALENDARINTERVAL_NO = 2376;

    /**
     * JWT生成Token
     * JWT构成: header, payload, signature
     *
     * @param userId 登录成功后用户userId
     */
    public static String createToken(String userId,boolean noExpireDate) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        if(noExpireDate){
            nowTime.add(CALENDARFIELD, CALENDARINTERVAL_NO);
        } else {
            nowTime.add(CALENDARFIELD, CALENDARINTERVAL);
        }

        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        /**
         * build token
         * param backups {iss:Service, aud:APP}
         *
         * withHeader : header
         * withClaim : payload
         * withIssuedAt : sign time
         * withExpiresAt : expire time
         * sign :signature
         */
        return JWT.create().withHeader(map)
                .withClaim("iss", "Service")
                .withClaim("aud", "hais")
                .withClaim("user_id", userId)
                .withIssuedAt(iatDate)
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Optional.ofNullable(claims).orElseThrow(IllegalArgumentException::new);
            return claims;
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.TokenAnalysisFailed);
        }
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static String getUserId(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim userIdClaim = claims.get("user_id");
        if (null == userIdClaim || StringUtils.isEmpty(userIdClaim.asString())) {
            throw new UnauthorizedException(ErrorCode.Unauthorized);
        }
        return userIdClaim.asString();
    }

}
