package com.foodexpress.food_delivery_backend.service;

import com.foodexpress.food_delivery_backend.dto.OrderReqDto;
import com.foodexpress.food_delivery_backend.model.Order;
import com.foodexpress.food_delivery_backend.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderReqDto order, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
