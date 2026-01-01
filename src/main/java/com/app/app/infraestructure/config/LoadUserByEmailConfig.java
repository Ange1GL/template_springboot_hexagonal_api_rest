package com.app.app.infraestructure.config;

import com.app.app.application.service.LoadUserByEmailService;
import com.app.app.domain.port.in.LoadUserByEmailUseCase;
import com.app.app.domain.port.out.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadUserByEmailConfig {


    @Bean
    public LoadUserByEmailUseCase loadUserByEmailUseCase(
            UserRepository userRepository) {

        return new LoadUserByEmailService(userRepository);
    }
}
