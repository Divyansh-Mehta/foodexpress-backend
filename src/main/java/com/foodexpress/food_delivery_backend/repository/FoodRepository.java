package com.foodexpress.food_delivery_backend.repository;

import com.foodexpress.food_delivery_backend.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    public List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food AS f  WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    public List<Food> searchFood(@Param("keyword") String keyword);
}
