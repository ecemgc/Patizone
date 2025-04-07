package com.patizone.core_service.config;

import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLoggingInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignLoggingInterceptor.class);

    @Bean
    public RequestInterceptor requestLoggingInterceptor() {
        return requestTemplate -> {
            logger.info("Feign Request: [{}] {}", requestTemplate.method(), requestTemplate.url());
            requestTemplate.headers().forEach((key, value) -> logger.info("Header: {}={}", key, value));
            if (requestTemplate.body() != null) {
                logger.info("Request Body: {}", new String(requestTemplate.body()));
            }
        };
    }
}