package com.patizone.core_service.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  //private final JwtAuthChannelInterceptor jwtAuthChannelInterceptor;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws") // WebSocket bağlantı noktası
        .setAllowedOriginPatterns("*")
        .withSockJS(); // Tarayıcı uyumluluğu için SockJS kullan
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/queue", "/topic"); // Abone olunan yollar
    registry.setApplicationDestinationPrefixes("/app"); // Mesaj gönderme yolu
    registry.setUserDestinationPrefix("/user"); // Kullanıcı bazlı mesajlaşma için
  }

  @Override
  public void configureClientInboundChannel(
      org.springframework.messaging.simp.config.ChannelRegistration registration) {
    // registration.interceptors(jwtAuthChannelInterceptor);
  }
}
