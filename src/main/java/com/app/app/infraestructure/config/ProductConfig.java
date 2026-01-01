package com.app.app.infraestructure.config;

import com.app.app.application.service.RegisterProductService;
import com.app.app.domain.port.in.RegisterProductUseCase;
import com.app.app.domain.port.out.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public RegisterProductUseCase registerProductUseCase(
            ProductRepository productRepository
    ) {
        return new RegisterProductService(productRepository);
    }
}
