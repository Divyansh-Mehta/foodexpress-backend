package com.foodexpress.food_delivery_backend.service;

import com.foodexpress.food_delivery_backend.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
