package com.app.app.infraestructure.adapter;

import com.app.app.domain.model.Product;
import com.app.app.domain.port.out.ProductRepository;
import com.app.app.infraestructure.entities.ProductEntity;
import com.app.app.infraestructure.repository.JpaRepositoryProduct;
import org.springframework.stereotype.Component;

@Component
public class JpaProductRepositoryAdapter implements ProductRepository {

    private final JpaRepositoryProduct jpaRepositoryProduct;

    public JpaProductRepositoryAdapter(JpaRepositoryProduct jpaRepositoryProduct) {
        this.jpaRepositoryProduct = jpaRepositoryProduct;
    }

    @Override
    public void save(Product product) {
        ProductEntity entity = ProductEntity.fromDomainModel(product);
        jpaRepositoryProduct.save(entity);
    }
}
