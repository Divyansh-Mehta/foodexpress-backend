package com.foodexpress.food_delivery_backend.service.impl;

import com.foodexpress.food_delivery_backend.model.Category;
import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.repository.CategoryRepository;
import com.foodexpress.food_delivery_backend.service.CategoryService;
import com.foodexpress.food_delivery_backend.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final
    RestaurantService restaurantService;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(RestaurantService restaurantService, CategoryRepository categoryRepository) {
        this.restaurantService = restaurantService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String name, Long userId) throws Exception{
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> opt = categoryRepository.findById(id);
        if(!opt.isPresent()){
            throw new Exception("Category not found");
        }
        return opt.get();
    }
}
