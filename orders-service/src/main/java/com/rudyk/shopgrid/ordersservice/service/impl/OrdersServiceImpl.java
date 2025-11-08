package com.rudyk.shopgrid.ordersservice.service.impl;

import com.rudyk.shopgrid.common.exception.ResourceNotFoundException;
import com.rudyk.shopgrid.ordersservice.client.product.ProductsServiceClient;
import com.rudyk.shopgrid.ordersservice.client.user.UsersServiceClient;
import com.rudyk.shopgrid.ordersservice.dto.CreateOrderRequestDto;
import com.rudyk.shopgrid.ordersservice.dto.OrderResponseDto;
import com.rudyk.shopgrid.ordersservice.entity.Order;
import com.rudyk.shopgrid.ordersservice.entity.OrderItem;
import com.rudyk.shopgrid.ordersservice.entity.OrderStatus;
import com.rudyk.shopgrid.ordersservice.mapper.OrderItemMapper;
import com.rudyk.shopgrid.ordersservice.mapper.OrderMapper;
import com.rudyk.shopgrid.ordersservice.repository.OrdersRepository;
import com.rudyk.shopgrid.ordersservice.service.OrdersService;
import com.rudyk.shopgrid.ordersservice.util.OrderServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.rudyk.shopgrid.ordersservice.constant.OrderConstants.ID_FIELD_NAME;
import static com.rudyk.shopgrid.ordersservice.constant.OrderConstants.ORDER_RESOURCE_NAME;

@Service
@RequestMapping
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductsServiceClient productsServiceClient;
    private final UsersServiceClient usersServiceClient;

    @Override
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        if (!usersServiceClient.checkIfUserExists(requestDto.getUserId())) {
            throw new ResourceNotFoundException(ORDER_RESOURCE_NAME, ID_FIELD_NAME, requestDto.getUserId());
        }

        List<OrderItem> orderItems = getOrderItems(requestDto);

        BigDecimal totalPrice = OrderServiceUtil.calculateOrderTotalPrice(orderItems);

        Order order = new Order();
        order.setUserId(requestDto.getUserId());
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        ordersRepository.save(order);
        return OrderMapper.mapToDto(order);
    }

    private List<OrderItem> getOrderItems(CreateOrderRequestDto requestDto) {
        return requestDto.getOrderItems()
                .stream()
                .filter(item -> productsServiceClient
                        .checkForProductAvailability(item.getProductId(), item.getQuantity()))
                .map(OrderItemMapper::mapToEntity)
                .toList();
    }

    @Override
    public OrderResponseDto getOrderById(UUID orderId) {
        return ordersRepository.findById(orderId)
                .map(OrderMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(ORDER_RESOURCE_NAME, ID_FIELD_NAME, orderId));
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(UUID userId) {
        return ordersRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::mapToDto)
                .toList();
    }

    @Override
    public void completeOrder(UUID orderId) {
        changeOrderStatus(orderId, OrderStatus.COMPLETED);
    }

    @Override
    public void cancelOrder(UUID orderId) {
        changeOrderStatus(orderId, OrderStatus.CANCELED);
    }

    private void changeOrderStatus(UUID orderId, OrderStatus completed) {
        ordersRepository.findById(orderId).ifPresentOrElse(order -> {
            order.setStatus(completed);
            ordersRepository.save(order);
        }, () -> {
            throw new ResourceNotFoundException(ORDER_RESOURCE_NAME, ID_FIELD_NAME, orderId);
        });
    }
}
