package com.ford.asukathunder.common.websocket.interceptor;

import com.ford.asukathunder.common.exception.UnauthorizedException;
import com.ford.asukathunder.util.ErrorCode;
import com.ford.asukathunder.util.JwtToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class MyHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(MyHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = servletRequest.getServletRequest().getParameter("token");
            if (StringUtils.isEmpty(token)) {
                throw new UnauthorizedException(ErrorCode.TokenInvalid);
            }
            String userId = JwtToken.getUserId(token);
            logger.info("userId is {}", userId);
            attributes.put("WEBSOCKET_USERID", userId);
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
