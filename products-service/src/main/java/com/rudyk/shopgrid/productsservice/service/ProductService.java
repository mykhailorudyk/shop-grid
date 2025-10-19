package com.rudyk.shopgrid.productsservice.service;

import com.rudyk.shopgrid.productsservice.dto.CreateProductRequestDto;
import com.rudyk.shopgrid.productsservice.dto.ProductResponseDto;
import com.rudyk.shopgrid.productsservice.dto.UpdateProductRequestDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponseDto getProductById(UUID id);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto createProduct(CreateProductRequestDto requestDto);

    ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto requestDto);

    String deleteProduct(UUID id);

}
