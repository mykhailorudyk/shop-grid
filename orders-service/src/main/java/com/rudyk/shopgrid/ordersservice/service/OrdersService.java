package com.rudyk.shopgrid.ordersservice.service;

import com.rudyk.shopgrid.ordersservice.dto.CreateOrderRequestDto;
import com.rudyk.shopgrid.ordersservice.dto.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrdersService {

    OrderResponseDto createOrder(CreateOrderRequestDto requestDto);

    OrderResponseDto getOrderById(UUID orderId);

    List<OrderResponseDto> getOrdersByUserId(UUID userId);

    void completeOrder(UUID orderId);

    void cancelOrder(UUID orderId);
}
