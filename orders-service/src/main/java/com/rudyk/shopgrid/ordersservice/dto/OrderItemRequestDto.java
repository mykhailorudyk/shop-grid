package com.rudyk.shopgrid.ordersservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemRequestDto {

    private UUID productId;
    private Integer quantity;
    private BigDecimal priceAtPurchase;

}
