package com.rudyk.shopgrid.productsservice.service.impl;

import com.rudyk.shopgrid.productsservice.dto.CreateProductRequestDto;
import com.rudyk.shopgrid.productsservice.dto.ProductResponseDto;
import com.rudyk.shopgrid.productsservice.dto.UpdateProductRequestDto;
import com.rudyk.shopgrid.productsservice.repository.ProductRepository;
import com.rudyk.shopgrid.productsservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto getProductById(UUID id) {
        productRepository.findById(id)
                .orElseThrow();
        return null;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto requestDto) {
        return null;
    }

    @Override
    public String deleteProduct(UUID id) {
        return "";
    }
}
