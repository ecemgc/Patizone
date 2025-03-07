package com.patizone.core_service.security;

import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.UnauthorizedException;
import com.patizone.core_service.service.user.intf.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long jwtExpiration;

  private final UserService service;

  public String extractUsername(String token) {
    Claims claims = extractAllClaims(token);
    return claims.getSubject();
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public Date extractExpiration(String token) {
    Claims claims = extractAllClaims(token);
    return claims.getExpiration();
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(String email) {

    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MINUTES)))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public void validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
    } catch (ExpiredJwtException e) {
      throw new UnauthorizedException("Token is expired.");
    } catch (Exception e) {
      throw new UnauthorizedException("Token is not valid.");
    }
  }

  public String getToken(HttpServletRequest httpServletRequest) {
    final String bearerToken = httpServletRequest.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public User getUser(String authHeader) {
    if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
      throw new RuntimeException("Header not exist.");
    }
    String token = authHeader.substring(7);
    validateToken(token);
    String email = extractUsername(token);
    return service.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found."));

  }
}
