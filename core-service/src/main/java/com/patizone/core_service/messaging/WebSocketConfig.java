package com.patizone.core_service.messaging;

import com.patizone.core_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
  private final UserHandshakeHandler userHandshakeHandler;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
            .addInterceptors(jwtHandshakeInterceptor)
            .setHandshakeHandler(userHandshakeHandler)
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
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        Principal user = accessor.getUser();
        if (user != null) {
          Authentication auth = new UsernamePasswordAuthenticationToken(user, null, ((User) user).getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
        return message;
      }
    });
  }
}
