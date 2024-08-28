package com.foodexpress.food_delivery_backend.service.impl;

import com.foodexpress.food_delivery_backend.model.Ingredient;
import com.foodexpress.food_delivery_backend.model.IngredientCategory;
import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.repository.IngredientCategoryRepository;
import com.foodexpress.food_delivery_backend.repository.IngredientRepository;
import com.foodexpress.food_delivery_backend.service.IngredientsService;
import com.foodexpress.food_delivery_backend.service.RestaurantService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService {

    private final RestaurantService restaurantService;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(RestaurantService restaurantService, IngredientCategoryRepository ingredientCategoryRepository, IngredientRepository ingredientRepository) {
        this.restaurantService = restaurantService;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory category = new IngredientCategory();
        category.setName(name);
        category.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
        if(!opt.isPresent()){
            throw new Exception(("Ingredient category not found"));
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public Ingredient createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(ingredientCategoryId);

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        ingredient.setRestaurant(restaurant);
        ingredient.setCategory(category);

        return ingredientRepository.save(ingredient);
    }

    @Override
    public void updateStock(Long id) throws Exception {
        Optional<Ingredient> opt = ingredientRepository.findById(id);
        if (!opt.isPresent()){
            throw new Exception("Ingredient not found");
        }
        Ingredient ingredient = opt.get();
        ingredient.setInStock(!ingredient.isInStock());
        ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> findRestaurantIngredients(Long restaurantId) {
        return ingredientRepository.findByRestaurantId(restaurantId);
    }
}
