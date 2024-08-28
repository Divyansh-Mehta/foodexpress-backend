package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.model.Order;
import com.foodexpress.food_delivery_backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/{orderId}/update-status")
    public ResponseEntity<Order> updateOrderStatus(@RequestParam String orderStatus, @PathVariable Long orderId) throws Exception {
        Order order = orderService.updateOrder(orderId, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestParam(required = false) String orderStatus, @PathVariable Long id) throws Exception {
        List<Order> order = orderService.getRestaurantOrder(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
