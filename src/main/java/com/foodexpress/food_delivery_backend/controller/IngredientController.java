package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.dto.CreateIngredientReqDto;
import com.foodexpress.food_delivery_backend.dto.CreateIngredientCategoryDto;
import com.foodexpress.food_delivery_backend.model.Ingredient;
import com.foodexpress.food_delivery_backend.model.IngredientCategory;
import com.foodexpress.food_delivery_backend.service.IngredientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/ingredients")
public class IngredientController {

    private final IngredientsService ingredientsService;

    public IngredientController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody CreateIngredientCategoryDto ingredientCategoryDto
            ) throws Exception{
        IngredientCategory category = ingredientsService.createIngredientCategory(ingredientCategoryDto.getName(), ingredientCategoryDto.getRestaurantId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredientCategory(
            @RequestBody CreateIngredientReqDto ingredientReqDto
    ) throws Exception{
        Ingredient  ingredient = ingredientsService.createIngredientItem(ingredientReqDto.getRestaurantId(), ingredientReqDto.getName(), ingredientReqDto.getCategoryId());
        return new ResponseEntity<>(ingredient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<String> updateIngredientStock(@PathVariable Long id) throws Exception{
        ingredientsService.updateStock(id);
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Ingredient>> getRestaurantIngredients(@PathVariable Long id){
        List<Ingredient> ingredients = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception{
        List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }


}
