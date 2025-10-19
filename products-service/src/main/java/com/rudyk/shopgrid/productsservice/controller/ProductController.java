package com.rudyk.shopgrid.productsservice.controller;

import com.rudyk.shopgrid.productsservice.dto.CreateProductRequestDto;
import com.rudyk.shopgrid.productsservice.dto.ProductResponseDto;
import com.rudyk.shopgrid.productsservice.dto.UpdateProductRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") UUID id) {

    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {

    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto) {

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody UpdateProductRequestDto requestDto) {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") UUID id) {

    }

}
