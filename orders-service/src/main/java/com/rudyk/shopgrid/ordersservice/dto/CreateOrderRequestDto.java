package com.rudyk.shopgrid.ordersservice.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateOrderRequestDto {

    private List<OrderItemRequestDto> orderItems;
}
