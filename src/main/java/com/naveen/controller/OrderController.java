package com.naveen.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.naveen.dto.OrderDto;
import com.naveen.entity.Address;
import com.naveen.entity.Order;
import com.naveen.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{userId}")
    public OrderDto placeOrder(@PathVariable Long userId,
                               @RequestBody PlaceOrderRequest request) {
        Order order = orderService.placeOrder(userId, request.getShippingAddress(), request.getPhoneNumber());
        return orderService.convertToDto(order);
    }

    @GetMapping("/{userId}")
    public List<OrderDto> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return orders.stream().map(orderService::convertToDto).collect(Collectors.toList());
    }
    
    public static class PlaceOrderRequest {
        private Address shippingAddress;  
        private String phoneNumber;       

        public Address getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(Address shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}
