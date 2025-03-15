package com.patizone.core_service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
@Slf4j
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(org.springframework.http.server.ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        String email = (String) attributes.get("email"); // Interceptor'dan gelen kullanıcı adı

        if (email != null) {
            log.info("WebSocket bağlanan kullanıcı: {}", email);
            return new WebSocketUser(email); // Principal nesnesini oluştur
        }

        return null;
    }
}