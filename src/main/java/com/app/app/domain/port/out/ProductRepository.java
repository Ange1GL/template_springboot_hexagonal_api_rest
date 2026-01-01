package com.app.app.domain.port.out;

import com.app.app.domain.model.Product;

public interface ProductRepository
{
    void save(Product product);
}
