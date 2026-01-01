package com.app.app.infraestructure.config;

import com.app.app.application.service.AuthenticateUserService;
import com.app.app.application.service.RegisterUserService;
import com.app.app.domain.port.in.AuthenticateUserUseCase;
import com.app.app.domain.port.in.LoadUserByEmailUseCase;
import com.app.app.domain.port.in.RegisterUserCase;
import com.app.app.domain.port.out.PasswordHasher;
import com.app.app.domain.port.out.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AuthApplicationConfig {

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            LoadUserByEmailUseCase loadUserByEmailUseCase,
            PasswordHasher passwordHasher) {

        return new AuthenticateUserService(
                loadUserByEmailUseCase,
                passwordHasher
        );
    }


    @Bean
    public RegisterUserCase registerUserCase(
            PasswordHasher passwordHasher,
            UserRepository userRepository
    ) {
        return new RegisterUserService(
                passwordHasher,
                userRepository
        );
    }
}
