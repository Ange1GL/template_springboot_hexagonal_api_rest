package com.app.app.infraestructure.config;


import com.app.app.application.service.RefreshTokenUseCase;
import com.app.app.domain.port.out.TokenService;
import com.app.app.domain.port.out.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefresTokenUseCaseConfig {



    @Bean
    RefreshTokenUseCase refreshTokenUseCase(
         UserRepository userRepository,
         TokenService tokenService
    ) {
        return new RefreshTokenUseCase(
                userRepository,
                tokenService
        );
    }
}
