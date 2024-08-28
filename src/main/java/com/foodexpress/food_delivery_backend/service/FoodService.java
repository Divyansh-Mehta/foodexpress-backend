package com.foodexpress.food_delivery_backend.service;

import com.foodexpress.food_delivery_backend.model.Category;
import com.foodexpress.food_delivery_backend.model.Food;
import com.foodexpress.food_delivery_backend.model.Restaurant;

import java.util.List;

public interface FoodService {
    public Food createFood(Food food, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegiterian, boolean isNonVeg, boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailability(Long foodId) throws Exception;
}
