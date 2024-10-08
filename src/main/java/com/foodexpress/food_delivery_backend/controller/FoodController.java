package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.model.Food;
import com.foodexpress.food_delivery_backend.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword){
        List<Food> foods = foodService.searchFood(keyword);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @PathVariable Long id,
            @RequestParam(required = false) boolean isVegetarian,
            @RequestParam(required = false) boolean isNonVeg,
            @RequestParam(required = false) boolean isSeasonal,
            @RequestParam(required = false) String food_category){
        List<Food> foods = foodService.getRestaurantsFood(id, isVegetarian, isNonVeg, isSeasonal, food_category);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
