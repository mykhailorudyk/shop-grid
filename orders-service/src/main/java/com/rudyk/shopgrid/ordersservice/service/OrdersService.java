package com.rudyk.shopgrid.ordersservice.service;

import com.rudyk.shopgrid.ordersservice.dto.CreateOrderRequestDto;
import com.rudyk.shopgrid.ordersservice.dto.OrderResponseDto;

import java.util.List;

public interface OrdersService {

    OrderResponseDto createOrder(CreateOrderRequestDto requestDto);

    OrderResponseDto getOrderById(Long orderId);

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    void completeOrder(Long orderId);

    void cancelOrder(Long orderId);
}
