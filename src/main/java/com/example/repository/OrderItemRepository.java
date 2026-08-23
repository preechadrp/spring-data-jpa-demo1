package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.OrderItem;
import com.example.model.compositekey.OrderItemId;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
