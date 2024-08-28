package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.model.Food;
import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.service.FoodService;
import com.foodexpress.food_delivery_backend.service.RestaurantService;
import com.foodexpress.food_delivery_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/foods")
public class AdminFoodController {
    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    public AdminFoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurant/{id}")
    public ResponseEntity<Food> createFood(@RequestBody Food food, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        Food savedFood = foodService.createFood(food, restaurant);
        return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id) throws Exception{
        foodService.deleteFood(id);
        return new ResponseEntity<>("Food deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailability(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailability(id);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
}
