package com.foodexpress.food_delivery_backend.service.impl;

import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.repository.UserRepository;
import com.foodexpress.food_delivery_backend.security.JwtProvider;
import com.foodexpress.food_delivery_backend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwt(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null){
            throw new Exception("User not found");
        }
        return user;
    }
}
