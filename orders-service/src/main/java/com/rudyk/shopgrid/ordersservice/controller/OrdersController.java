package com.rudyk.shopgrid.ordersservice.controller;

import com.rudyk.shopgrid.ordersservice.dto.CreateOrderRequestDto;
import com.rudyk.shopgrid.ordersservice.dto.OrderResponseDto;
import com.rudyk.shopgrid.ordersservice.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        return new ResponseEntity<>(ordersService.createOrder(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok().body(ordersService.getOrderById(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(ordersService.getOrdersByUserId(userId));
    }

    @PostMapping("{id}/complete")
    public ResponseEntity<Void> completeOrder(@PathVariable("id") UUID orderId) {
        ordersService.completeOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable("id") UUID orderId) {
        ordersService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
