package com.foodexpress.food_delivery_backend.service.impl;

import com.foodexpress.food_delivery_backend.dto.AddCartItemReqDto;
import com.foodexpress.food_delivery_backend.model.Cart;
import com.foodexpress.food_delivery_backend.model.CartItem;
import com.foodexpress.food_delivery_backend.model.Food;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.repository.CartItemRepository;
import com.foodexpress.food_delivery_backend.repository.CartRepository;
import com.foodexpress.food_delivery_backend.service.CartService;
import com.foodexpress.food_delivery_backend.service.FoodService;
import com.foodexpress.food_delivery_backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserService userService;

    private final FoodService foodService;

    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, UserService userService, FoodService foodService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.foodService = foodService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem addItemToCart(AddCartItemReqDto cartItemReqDto, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(cartItemReqDto.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem item: cart.getItems()){
            if(item.getFood().equals(food)){
                int newQuantity = item.getQuantity() + cartItemReqDto.getQuantity();
                return updateCartItemQuantity(item.getId(), newQuantity);
            }
        }
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setFood(food);
        item.setQuantity(cartItemReqDto.getQuantity());
        item.setIngredients(cartItemReqDto.getIngredients());
        item.setTotalPrice(cartItemReqDto.getQuantity() * food.getPrice());
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (!opt.isPresent()){
            throw new Exception("Cart item not found");
        }
        CartItem item = opt.get();
        item.setQuantity(quantity);
        item.setTotalPrice(quantity * item.getFood().getPrice());
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if(!opt.isPresent()){
            throw new Exception("Cart item not found");
        }
        CartItem item = opt.get();
        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public double calculateCartTotal(Cart cart) throws Exception {
        double total = 0;
        for (CartItem cartItem: cart.getItems()){
            total += cartItem.getTotalPrice();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> opt = cartRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Cart not found");
        }
        return opt.get();
    }

    @Override
    public Cart findCartByUserId(Long id) throws Exception {
        Cart cart = cartRepository.findByCustomerId(id);
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long id) throws Exception {
        Cart cart = cartRepository.findByCustomerId(id);

        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
