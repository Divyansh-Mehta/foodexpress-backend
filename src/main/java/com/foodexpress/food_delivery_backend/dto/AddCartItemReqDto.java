package com.foodexpress.food_delivery_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemReqDto {
    private Long foodId;
    private int quantity;
    private List<String> ingredients;
}
