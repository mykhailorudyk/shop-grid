package com.rudyk.shopgrid.ordersservice.service;

import com.rudyk.shopgrid.ordersservice.dto.CreateOrderRequestDto;
import com.rudyk.shopgrid.ordersservice.dto.OrderResponseDto;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

public interface OrdersService {

    OrderResponseDto createOrder(CreateOrderRequestDto requestDto, String authenticatedUserId);

    OrderResponseDto getOrderById(UUID orderId);

    List<OrderResponseDto> getOrdersByUserId(String userId, Jwt jwt);

    void completeOrder(UUID orderId);

    void cancelOrder(UUID orderId);
}
