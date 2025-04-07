package com.patizone.core_service.security;

import com.patizone.core_service.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final CustomUserDetailService customUserDetailService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    // tokenı request headerdan aldık.
    String token = jwtUtils.getToken(request);

    if (!StringUtils.hasText(token)) {
      //token yok ise auth olamayacak sıradaki filtera gönderdik.
      filterChain.doFilter(request, response);
      return;
    }

    try {
      jwtUtils.validateToken(token);
    } catch (UnauthorizedException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"errorMessage\": \"" + e.getMessage() + "\"}");
      return;
    }

    //it takes email from the token
    String email = jwtUtils.extractUsername(token);
    UserDetails userDetails;
    try {
      //burada loadByUsername ile dbye bu extract edilmiş mail gönderiliyor ve var mı yok mu kontrol etmiş oluyoruz var ise UserDetailsteki userımız artık bu mailli user oluyor.
      userDetails = customUserDetailService.loadUserByUsername(email);
    } catch (UsernameNotFoundException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"errorMessage\": \"User not found.\"}");
      return;
    }

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          Collections.emptyList()
      );
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);

  }
}
