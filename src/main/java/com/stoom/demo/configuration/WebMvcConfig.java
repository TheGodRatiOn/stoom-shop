package com.stoom.demo.configuration;

import com.stoom.demo.security.token.AccessTokenProvider;
import com.stoom.demo.security.token.RefreshTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SEC = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders(AccessTokenProvider.HEADER, RefreshTokenProvider.HEADER)
                .allowCredentials(true)
                .maxAge(MAX_AGE_SEC);
    }
}