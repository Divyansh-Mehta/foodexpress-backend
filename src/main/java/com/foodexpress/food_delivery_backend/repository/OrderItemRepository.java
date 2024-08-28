package com.foodexpress.food_delivery_backend.repository;

import com.foodexpress.food_delivery_backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
