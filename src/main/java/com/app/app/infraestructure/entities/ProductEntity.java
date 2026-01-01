package com.app.app.infraestructure.entities;


import com.app.app.domain.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "stock")
    Integer stock;

    @Column(name = "subtotal")
    BigDecimal subtotal;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;


    public static ProductEntity fromDomainModel(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setStock(product.getStock());
        entity.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(product.getStock())));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

}
