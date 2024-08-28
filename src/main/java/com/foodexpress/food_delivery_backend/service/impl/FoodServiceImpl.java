package com.foodexpress.food_delivery_backend.service.impl;

import com.foodexpress.food_delivery_backend.model.Category;
import com.foodexpress.food_delivery_backend.model.Food;
import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.repository.FoodRepository;
import com.foodexpress.food_delivery_backend.repository.RestaurantRepository;
import com.foodexpress.food_delivery_backend.service.FoodService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    public FoodServiceImpl(FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public Food createFood(Food food, Restaurant restaurant) {
        food.setRestaurant(restaurant);
        food.setCreatedAt(LocalDateTime.now());
        food.setAvailable(true);
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        restaurantRepository.save(restaurant);
        return savedFood;


    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegitarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if (isVegitarian){
            foods = filterByVegetarian(foods);
        }
        if (isNonVeg){
            foods = filterByNonveg(foods);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods);
        }
        if (foodCategory != null && foodCategory.isEmpty()){
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> food.getCategory().getName().equals(foodCategory)).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods) {
        return foods.stream().filter(food -> food.isSeasonal()).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods) {
        return foods.stream().filter(food -> !food.isVeg()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods) {
        return foods.stream().filter(food -> food.isVeg()).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> opt = foodRepository.findById(foodId);
        if (!opt.isPresent()){
            throw new Exception("Food does not exist");
        }
        return opt.get();
    }

    @Override
    public Food updateAvailability(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
