package com.foodexpress.food_delivery_backend.dto;

import lombok.Data;

@Data
public class CreateIngredientCategoryDto {
    private String name;
    private Long restaurantId;
}
