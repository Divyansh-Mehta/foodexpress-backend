package com.foodexpress.food_delivery_backend.service;

import com.foodexpress.food_delivery_backend.model.Ingredient;
import com.foodexpress.food_delivery_backend.model.IngredientCategory;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public Ingredient createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception;

    public void updateStock(Long id) throws Exception;

    public List<Ingredient> findRestaurantIngredients(Long restaurantId);
}
