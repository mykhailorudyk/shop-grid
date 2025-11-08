package com.rudyk.shopgrid.ordersservice.mapper;

import com.rudyk.shopgrid.ordersservice.dto.OrderResponseDto;
import com.rudyk.shopgrid.ordersservice.entity.Order;

public class OrderMapper {

    public static OrderResponseDto mapToDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .orderCreatedAt(order.getOrderCreatedAt())
                .status(order.getStatus())
                .orderItems(order.getOrderItems().stream().map(OrderItemMapper::mapToDto).toList())
                .build();
    }

}
