package com.foodexpress.food_delivery_backend.dto;

import com.foodexpress.food_delivery_backend.model.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
