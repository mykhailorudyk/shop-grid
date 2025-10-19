package com.rudyk.shopgrid.productsservice.service.impl;

import com.rudyk.shopgrid.common.exception.ResourceNotFoundException;
import com.rudyk.shopgrid.productsservice.dto.CreateProductRequestDto;
import com.rudyk.shopgrid.productsservice.dto.ProductResponseDto;
import com.rudyk.shopgrid.productsservice.dto.UpdateProductRequestDto;
import com.rudyk.shopgrid.productsservice.entity.Product;
import com.rudyk.shopgrid.productsservice.mapper.ProductMapper;
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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return ProductMapper.mapToDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::mapToDto)
                .toList();
    }

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {
        Product product = ProductMapper.mapToProduct(requestDto);
        productRepository.save(product);
        return ProductMapper.mapToDto(product);
    }

    @Override
    public ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        ProductMapper.mapToProduct(requestDto, product);
        productRepository.save(product);
        return ProductMapper.mapToDto(product);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productRepository.delete(product);
    }
}
