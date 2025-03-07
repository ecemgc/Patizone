package com.patizone.core_service.security;

import com.patizone.core_service.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthChannelInterceptor implements ChannelInterceptor {

  private final JwtUtils jwtUtils;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String token = accessor.getFirstNativeHeader("Authorization");

    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
      try {
        token = token.substring(7); // "Bearer " kısmını at
        Claims claims = jwtUtils.extractAllClaims(token);
        String email = claims.getSubject();

        // Doğrulama başarılı ise, kullanıcı bilgilerini session içine koy
        accessor.getSessionAttributes().put("email", email);
      } catch (JwtException e) {
        throw new UnauthorizedException("Invalid WebSocket Token");
      }
    } else {
      throw new UnauthorizedException("No JWT token found in WebSocket headers");
    }

    return message;
  }
}
