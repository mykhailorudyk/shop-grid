package com.rudyk.shopgrid.ordersservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data @Builder
public class OrderItemResponseDto {

    private Long id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal priceAtPurchase;

}
