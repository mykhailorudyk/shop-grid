package com.rudyk.shopgrid.productsservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter @Setter
public class Product {

    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;

}
