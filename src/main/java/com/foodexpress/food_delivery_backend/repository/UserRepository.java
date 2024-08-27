package com.foodexpress.food_delivery_backend.repository;

import com.foodexpress.food_delivery_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
