package com.realestate.product.repositories;

import com.realestate.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Exposes endpoints like GET /products, GET /products/{id}
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
}
