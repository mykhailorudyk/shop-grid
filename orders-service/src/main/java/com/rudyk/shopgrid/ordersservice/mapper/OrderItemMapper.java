package com.rudyk.shopgrid.ordersservice.mapper;

import com.rudyk.shopgrid.ordersservice.dto.OrderItemRequestDto;
import com.rudyk.shopgrid.ordersservice.dto.OrderItemResponseDto;
import com.rudyk.shopgrid.ordersservice.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemResponseDto mapToDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .priceAtPurchase(orderItem.getPriceAtPurchase())
                .build();
    }

    public static OrderItem mapToEntity(OrderItemRequestDto orderItemRequestDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(orderItemRequestDto.getProductId());
        orderItem.setQuantity(orderItemRequestDto.getQuantity());
        orderItem.setPriceAtPurchase(orderItemRequestDto.getPriceAtPurchase());
        return orderItem;
    }

}
