package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.service.RestaurantService;
import com.foodexpress.food_delivery_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    public RestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt, @RequestParam String keyword) throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurant(){
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable Long id) throws Exception{
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<String> addToFavorite(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        restaurantService.addtoFavorites(id, user);
        return new ResponseEntity<>("Successfully added to favorite", HttpStatus.OK);
    }
}
