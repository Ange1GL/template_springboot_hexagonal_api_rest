package com.app.app.application.service;

import com.app.app.domain.model.Product;
import com.app.app.domain.port.in.RegisterProductUseCase;
import com.app.app.domain.port.out.ProductRepository;


public class RegisterProductService implements RegisterProductUseCase {

    private final ProductRepository productRepository;

    public RegisterProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void register(Product product) {
        productRepository.save(product);
    }
}
