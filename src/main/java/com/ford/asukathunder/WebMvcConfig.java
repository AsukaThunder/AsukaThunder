package com.ford.asukathunder;

import com.ford.asukathunder.common.websocket.handler.MessageWebSocketHandler;
import com.ford.asukathunder.common.websocket.interceptor.MyHandshakeInterceptor;
import com.ford.asukathunder.interceptor.AuthenticationInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @ClassName: WebMvcConfig
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/10 上午 10:57
 **/
@Configuration
@EnableWebSocket
public class WebMvcConfig implements WebMvcConfigurer, WebSocketConfigurer {

    private static Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        logger.info("......注册......");
        //配置webSocket路径
        registry.addHandler(messageWebSocketHandler(), "/msg-websocket").addInterceptors(new MyHandshakeInterceptor()).setAllowedOrigins("*");
        //配置webSocket路径 支持前端使用socketJs
        registry.addHandler(messageWebSocketHandler(), "/sockjs/msg-websocket").setAllowedOrigins("*").addInterceptors(new MyHandshakeInterceptor()).withSockJS();
    }

    @Bean
    public MessageWebSocketHandler messageWebSocketHandler() {
        logger.info("......创建MessageWebSocketHandler......");
        return new MessageWebSocketHandler();
    }

}
