package com.rudyk.shopgrid.productsservice.mapper;

import com.rudyk.shopgrid.productsservice.dto.CreateProductRequestDto;
import com.rudyk.shopgrid.productsservice.dto.ProductResponseDto;
import com.rudyk.shopgrid.productsservice.dto.UpdateProductRequestDto;
import com.rudyk.shopgrid.productsservice.entity.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

    public ProductResponseDto mapToDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .build();
    }

    public Product mapToProduct(CreateProductRequestDto createProductRequestDto) {
        Product product = new Product();
        product.setName(createProductRequestDto.getName());
        product.setDescription(createProductRequestDto.getDescription());
        product.setPrice(createProductRequestDto.getPrice());
        product.setQuantityInStock(createProductRequestDto.getQuantityInStock());
        return product;
    }

    public void mapToProduct(UpdateProductRequestDto updateProductRequestDto, Product product) {
        product.setName(updateProductRequestDto.getName());
        product.setDescription(updateProductRequestDto.getDescription());
        product.setPrice(updateProductRequestDto.getPrice());
        product.setQuantityInStock(updateProductRequestDto.getQuantityInStock());
    }
}
