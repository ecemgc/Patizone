package com.patizone.core_service.messaging;

import com.patizone.core_service.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

  private final SessionRegistryService sessionRegistryService;
  private final JwtUtils jwtUtils;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String sessionId = headerAccessor.getSessionId();
    String authHeader = headerAccessor.getFirstNativeHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      try {
        String token = authHeader.substring(7);
        String email = jwtUtils.extractUsername(token);

        sessionRegistryService.registerUser(email, sessionId);
        log.info(" Kullanıcı bağlandı: {} (Session ID: {})", email, sessionId);
      } catch (Exception e) {
        log.error(" JWT doğrulama başarısız: {}", e.getMessage());
      }
    } else {
      log.warn(" WebSocket bağlantısında Authorization header eksik.");
    }
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    String sessionId = event.getSessionId();
    String email = sessionRegistryService.findUserEmailBySessionId(sessionId);

    if (email != null) {
      sessionRegistryService.removeUser(email);
      log.info("Kullanıcı bağlantısı kesildi: {} (Session ID: {})", email, sessionId);
    }
  }
}
