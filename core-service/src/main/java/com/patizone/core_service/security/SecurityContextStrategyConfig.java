package com.patizone.core_service.security;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextStrategyConfig {

    // care
    public void init() {
        // WebSocket mesajlarını işleyen thread'ler, auth context'i miras alsın
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}