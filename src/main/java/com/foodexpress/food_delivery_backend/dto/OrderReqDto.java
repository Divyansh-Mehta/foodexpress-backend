package com.foodexpress.food_delivery_backend.dto;

import com.foodexpress.food_delivery_backend.model.Address;
import lombok.Data;

@Data
public class OrderReqDto {
    private Long RestaurantId;
    private Address deliveryAddress;
}
