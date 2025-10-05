package com.naveen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.naveen.service.CartService;
import com.naveen.dto.CartDto;
import com.naveen.dto.CartItemRequest;
import com.naveen.entity.Cart;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartDto addProduct(@RequestBody CartItemRequest request) {
        Cart cart = cartService.addProductToCart(
            request.getUserId(),
            request.getProductId(),
            request.getQuantity()
        );
        return cartService.convertToDto(cart);
    }

    @GetMapping("/{userId}")
    public CartDto getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return cartService.convertToDto(cart);
    }

    @PutMapping("/update/{cartItemId}")
    public CartDto updateCartItem(@PathVariable Long cartItemId,
                                  @RequestParam int quantity) {
        Cart cart = cartService.updateCartItem(cartItemId, quantity);
        return cartService.convertToDto(cart);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public CartDto removeCartItem(@PathVariable Long cartItemId) {
        Cart cart = cartService.removeCartItem(cartItemId);
        return cartService.convertToDto(cart);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
