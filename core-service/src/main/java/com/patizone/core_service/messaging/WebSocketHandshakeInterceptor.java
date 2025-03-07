/*

    private final AuthenticatedUserService authenticatedUserService;
    private final SessionRegistryService sessionRegistryService;

    public WebSocketHandshakeInterceptor(AuthenticatedUserService authenticatedUserService,
                                         SessionRegistryService sessionRegistryService) {
        this.authenticatedUserService = authenticatedUserService;
        this.sessionRegistryService = sessionRegistryService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Long userId = authenticatedUserService.getCurrentUser().getId();
        attributes.put("userId", userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
 */

/*




import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeRequest;
import org.springframework.web.socket.server.HandshakeResponse;

import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
  @Override
  public boolean beforeHandshake(HandshakeRequest request, HandshakeResponse response,
      WebSocketSession wsSession) throws Exception {
    // Token'ı header'dan al
    String token = request.getHeaders().getFirst("Authorization");

    if (token != null && validateToken(token)) {
      // Token doğrulandıysa, SecurityContext holder ile kullanıcı bilgilerini al
      SecurityContextHolder.getContext().setAuthentication(getAuthenticationFromToken(token));
      return true;
    }

    return false;
  }

  @Override
  public void afterHandshake(HandshakeRequest request, HandshakeResponse response,
      WebSocketSession wsSession) {
    // İstekten sonra yapılacak işlemler
  }

  private boolean validateToken(String token) {
    // Burada token'ı doğrulama işlemini yapmalısınız (JWT validation)
    return true; // Geçerli olduğunu varsayalım
  }

  private Authentication getAuthenticationFromToken(String token) {
    // JWT token'dan Authentication nesnesi oluşturulmalı
    return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
  }
}














import org.springframework.context.annotation.Configuration;
    import org.springframework.web.socket.config.annotation.EnableWebSocket;
    import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
    import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
    import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new YourWebSocketHandler(), "/socket")
        .addInterceptors(new WebSocketHandshakeInterceptor())
        .setAllowedOrigins("*");  // İzin verilen origin'leri ayarlayın
  }
}

 */