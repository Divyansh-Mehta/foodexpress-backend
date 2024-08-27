package com.foodexpress.food_delivery_backend.dto;

import lombok.Data;

@Data
public class SigninReqDto {
    private String email;
    private String password;
}
