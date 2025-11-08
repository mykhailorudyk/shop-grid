package com.rudyk.shopgrid.ordersservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDto {

    private Long userId;
    private List<OrderItemRequestDto> orderItems;
}
