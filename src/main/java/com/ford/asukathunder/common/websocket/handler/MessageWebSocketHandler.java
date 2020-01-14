package com.ford.asukathunder.common.websocket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class MessageWebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(MessageWebSocketHandler.class);

    private static ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketSession>> users = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = session.getAttributes().get("WEBSOCKET_USERID").toString();
        //websocket连接后记录连接信息
        if (users.keySet().contains(userId)) {
            CopyOnWriteArraySet<WebSocketSession> webSocketSessions = users.get(userId);
            webSocketSessions.add(session);
        } else {
            CopyOnWriteArraySet<WebSocketSession> webSocketSessions = new CopyOnWriteArraySet<>();
            webSocketSessions.add(session);
            users.put(userId, webSocketSessions);
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        removeUserSession(session);
        if (session.isOpen()) {
            session.close();
        }
        logger.error("::WebSocket handleTransportError:  remote Address: " + session.getRemoteAddress() + " message: " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        removeUserSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        session.sendMessage(new TextMessage(message.getPayload() + "---" + LocalDateTime.now()));
    }

    /**
     * websocket清除连接信息
     *
     * @param session
     */
    private void removeUserSession(WebSocketSession session) {
        String userId = session.getAttributes().get("WEBSOCKET_USERID").toString();
        if (users.keySet().contains(userId)) {
            CopyOnWriteArraySet<WebSocketSession> webSocketSessions = users.get(userId);
            webSocketSessions.remove(session);
            if (webSocketSessions.isEmpty()) {
                users.remove(userId);
            }
        }
    }
}
