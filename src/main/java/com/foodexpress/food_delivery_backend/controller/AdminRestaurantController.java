package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.dto.CreateRestaurantReqDto;
import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.service.RestaurantService;
import com.foodexpress.food_delivery_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    public AdminRestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantReqDto restaurantReqDto, @RequestHeader("Authorization") String jwt) throws Exception{
         User user = userService.findUserByJwtToken(jwt);
         Restaurant restaurant = restaurantService.createRestaurant(restaurantReqDto, user);
         return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantReqDto restaurantReqDto, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
//        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id, restaurantReqDto);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
//        User user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateRestaurantStatus(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
//        User user = userService.findUserByJwtToken(jwt);
        restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>("Status updated successfully", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
