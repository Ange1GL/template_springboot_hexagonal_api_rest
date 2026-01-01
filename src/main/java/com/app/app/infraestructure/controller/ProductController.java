package com.app.app.infraestructure.controller;

import com.app.app.domain.model.Product;
import com.app.app.domain.port.in.RegisterProductUseCase;
import com.app.app.infraestructure.dto.CreateProductRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final RegisterProductUseCase registerProductUseCase;

    public ProductController(RegisterProductUseCase registerProductUseCase) {
        this.registerProductUseCase = registerProductUseCase;
    }

    @PostMapping
    public void create(@RequestBody CreateProductRequest request) {
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.getStock()
        );

        registerProductUseCase.register(product);
    }
}