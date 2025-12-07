package com.rudyk.shopgrid.ordersservice.util;

import com.rudyk.shopgrid.ordersservice.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceUtil {

    public static BigDecimal calculateOrderTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
