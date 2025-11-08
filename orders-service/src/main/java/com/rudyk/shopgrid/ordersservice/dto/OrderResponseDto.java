package com.rudyk.shopgrid.ordersservice.dto;

import com.rudyk.shopgrid.ordersservice.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder
public class OrderResponseDto {

    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private LocalDateTime orderCreatedAt;
    private List<OrderItemResponseDto> orderItems;
}
