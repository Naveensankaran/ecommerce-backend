package com.naveen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naveen.dto.OrderDto;
import com.naveen.dto.OrderItemDto;
import com.naveen.entity.*;
import com.naveen.repository.*;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo; // Assuming a User repository exists

    // Place an order from cart
    public Order placeOrder(Long userId, Address shippingAddressFromRequest, String phoneNumberFromRequest) {
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot place order with empty cart");
        }

        // Re-validate stock
        for (CartItem ci : cart.getCartItems()) {
            Product product = ci.getProduct();
            if (ci.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException(
                    "Product " + product.getName() + " does not have enough stock"
                );
            }
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Use provided address if available; otherwise use saved user address
        Address shippingAddress = (shippingAddressFromRequest != null) ? 
            shippingAddressFromRequest : user.getAddress();

        String shippingAddressString = shippingAddress.getBuildingNo() + ", " +
                (shippingAddress.getApartmentName() != null ? shippingAddress.getApartmentName() + ", " : "") +
                shippingAddress.getStreet() + ", " +
                shippingAddress.getArea() + ", " +
                shippingAddress.getCity() + ", " +
                shippingAddress.getState() + " - " +
                shippingAddress.getPincode();

        // Use provided phone if available; otherwise use saved phone
        String orderPhone = (phoneNumberFromRequest != null && !phoneNumberFromRequest.isEmpty()) ?
            phoneNumberFromRequest : user.getPhoneNumber();

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setUserName(user.getName());
        order.setUserEmail(user.getEmail());
        order.setUserPhone(orderPhone);
        order.setShippingAddress(shippingAddressString);
        order.setTotalAmount(cart.getTotalAmount());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart.getCartItems()) {
            Product product = ci.getProduct();

            // Reduce stock
            product.setStock(product.getStock() - ci.getQuantity());
            productRepo.save(product);

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(product);
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getPrice());
            oi.setSubtotal(ci.getSubtotal());

            orderItems.add(oi);
        }

        order.setOrderItems(orderItems);
        orderRepo.save(order);

        // Clear cart
        cartItemRepo.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalAmount(0);
        cartRepo.save(cart);

        return order;
    }



    // Get orders by user
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    // Convert to DTO
    public OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setUserName(order.getUserName());
        dto.setUserEmail(order.getUserEmail());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setOrderDate(order.getOrderDate());

        List<OrderItemDto> items = new ArrayList<>();
        for (OrderItem oi : order.getOrderItems()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setOrderItemId(oi.getId());
            itemDto.setProductId(oi.getProduct().getId());
            itemDto.setProductName(oi.getProduct().getName());
            List<ProductImage> images = oi.getProduct().getImage();
            itemDto.setProductImage(images.isEmpty() ? null : images.get(0).getUrl());
            itemDto.setQuantity(oi.getQuantity());
            itemDto.setPrice(oi.getPrice());
            itemDto.setSubtotal(oi.getSubtotal());
            items.add(itemDto);
        }

        dto.setItems(items);
        return dto;
    }
}
