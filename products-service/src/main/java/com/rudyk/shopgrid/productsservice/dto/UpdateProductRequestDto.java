package com.rudyk.shopgrid.productsservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequestDto {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;

}
