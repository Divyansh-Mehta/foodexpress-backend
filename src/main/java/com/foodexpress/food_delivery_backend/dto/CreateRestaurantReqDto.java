package com.foodexpress.food_delivery_backend.dto;

import com.foodexpress.food_delivery_backend.model.Address;
import com.foodexpress.food_delivery_backend.model.ContactInformation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRestaurantReqDto {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
