package com.rudyk.shopgrid.ordersservice.repository;

import com.rudyk.shopgrid.ordersservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
