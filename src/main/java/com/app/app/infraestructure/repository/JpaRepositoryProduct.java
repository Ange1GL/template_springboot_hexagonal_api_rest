package com.app.app.infraestructure.repository;

import com.app.app.infraestructure.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRepositoryProduct extends JpaRepository<ProductEntity, Integer> { }
