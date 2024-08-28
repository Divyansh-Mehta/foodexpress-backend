package com.foodexpress.food_delivery_backend.repository;

import com.foodexpress.food_delivery_backend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByRestaurantId(Long id);
}
