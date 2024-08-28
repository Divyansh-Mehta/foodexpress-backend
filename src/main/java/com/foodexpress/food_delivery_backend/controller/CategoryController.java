package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.model.Category;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.service.CategoryService;
import com.foodexpress.food_delivery_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant/{id}")
    public  ResponseEntity<List<Category>> getRestaurantCategory(@PathVariable Long id) throws Exception{
        List<Category> categories = categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
