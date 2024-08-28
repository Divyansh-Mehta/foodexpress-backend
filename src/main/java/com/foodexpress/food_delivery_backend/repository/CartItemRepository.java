package com.foodexpress.food_delivery_backend.repository;

import com.foodexpress.food_delivery_backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
