package com.app.app.domain.port.in;

import com.app.app.domain.model.Product;

public interface RegisterProductUseCase {
    void register(Product product);
}
