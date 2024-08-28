package com.foodexpress.food_delivery_backend.service;

import com.foodexpress.food_delivery_backend.dto.AddCartItemReqDto;
import com.foodexpress.food_delivery_backend.model.Cart;
import com.foodexpress.food_delivery_backend.model.CartItem;

public interface CartService {

    public CartItem addItemToCart(AddCartItemReqDto cartitemReqDto, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public double calculateCartTotal(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long id) throws Exception;

    public Cart clearCart(Long id) throws Exception;
}
