package com.rudyk.shopgrid.productsservice.repository;

import com.rudyk.shopgrid.productsservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
