package com.foodexpress.food_delivery_backend.service;

import com.foodexpress.food_delivery_backend.dto.CreateRestaurantReqDto;
import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.model.User;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantReqDto restaurantReqDto, User user);

    public Restaurant updateRestaurant(Long id, CreateRestaurantReqDto updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public void addtoFavorites(Long resId, User user) throws Exception;

    public void updateRestaurantStatus(Long id) throws Exception;
}
