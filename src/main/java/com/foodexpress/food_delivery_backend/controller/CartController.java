package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.dto.AddCartItemReqDto;
import com.foodexpress.food_delivery_backend.model.Cart;
import com.foodexpress.food_delivery_backend.model.CartItem;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.service.CartService;
import com.foodexpress.food_delivery_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/cartItem")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemReqDto cartItemReqDto, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(cartItemReqDto, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/cart-item/{id}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestParam int quantity, @PathVariable Long id) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(id, quantity);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
