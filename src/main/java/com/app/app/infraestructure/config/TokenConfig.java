package com.app.app.infraestructure.config;

import com.app.app.application.service.JwtTokenService;
import com.app.app.domain.port.out.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Configuration
public class TokenConfig {
    @Bean
    TokenService tokenService(
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder
    ) {
        return new JwtTokenService(
                jwtEncoder,
                jwtDecoder
        );
    }
}
