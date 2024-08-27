package com.foodexpress.food_delivery_backend.repository;

import com.foodexpress.food_delivery_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
