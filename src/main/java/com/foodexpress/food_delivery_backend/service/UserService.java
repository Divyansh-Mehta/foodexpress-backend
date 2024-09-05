package com.foodexpress.food_delivery_backend.service;

import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.model.User;

import java.util.List;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

    public List<Restaurant> findUserFavRestaunrat(String jwt) throws Exception;
}
