package com.foodexpress.food_delivery_backend.dto;

import lombok.Data;

@Data
public class CreateIngredientReqDto {
    private String name;
    private Long categoryId;
    private Long restaurantId;
}
