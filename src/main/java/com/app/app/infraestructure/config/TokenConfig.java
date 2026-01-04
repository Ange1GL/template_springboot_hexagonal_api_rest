package com.app.app.infraestructure.config;

import com.app.app.infraestructure.adapter.JwtTokenService;
import com.app.app.domain.port.out.TokenService;
import com.app.app.domain.port.out.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Configuration
public class TokenConfig {
    @Bean
    TokenService tokenService(
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder,
            UserRepository userRepository
    ) {
        return new JwtTokenService(
                jwtEncoder,
                jwtDecoder,
                userRepository
        );
    }
}
