package com.rudyk.shopgrid.ordersservice.repository;

import com.rudyk.shopgrid.ordersservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserId(UUID userId);

}
