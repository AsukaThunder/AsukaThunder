package com.ford.asukathunder.interceptor;

import com.ford.asukathunder.common.annotation.PublicInterface;
import com.ford.asukathunder.common.entity.user.User;
import com.ford.asukathunder.common.exception.UnauthorizedException;
import com.ford.asukathunder.common.util.UserUtils;
import com.ford.asukathunder.service.UserService;
import com.ford.asukathunder.util.ErrorCode;
import com.ford.asukathunder.util.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: AuthenticationInterceptor
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/10 上午 10:57
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    /**
     * 存储URL
     */
    protected final static Set<String> ignoreUrls = ConcurrentHashMap.<String>newKeySet();

    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    static {

        ignoreUrls.add("/error");
        ignoreUrls.add("/images");
        ignoreUrls.add("/swagger-resources");
        ignoreUrls.add("/static");
        ignoreUrls.add("/webjars");
        ignoreUrls.add("/swagger-ui");
        ignoreUrls.add("/v2/api-docs");
        ignoreUrls.add("/loginCas");
        ignoreUrls.add("/cas/login");
        ignoreUrls.add("/cas/logout");
        ignoreUrls.add("/cas/proofreading");

    }


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断接口是否需要登录
        PublicInterface methodAnnotation = method.getAnnotation(PublicInterface.class);
        if (methodAnnotation != null) {
            return true;
        }
        if (isIntercept(request.getServletPath())) {
            // 从http请求头中取出token
            String token = request.getHeader("token");
            if (token == null) {
                throw new UnauthorizedException(ErrorCode.TokenNotExist);
            }

            // 判断token时候合法
            String userId = JwtToken.getUserId(token);
            logger.info("userId is {}", userId);
            User dbUser = userService.getValidUser(userId);
            if (null == dbUser) {
                throw new UnauthorizedException(ErrorCode.TokenInvalid);
            }
            // 放入LocalThread
            UserUtils.setUserId(userId);

            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * 判断是否需要拦截
     *
     * @param servletPath
     * @return
     */
    private boolean isIntercept(String servletPath) {
        logger.info("servletPath ..." + servletPath);
        for (String url : ignoreUrls) {
            if (servletPath.startsWith(url)) {
                return false;
            }
        }
        return true;
    }
}