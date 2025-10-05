package com.naveen.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naveen.entity.Cart;
import com.naveen.entity.CartItem;
import com.naveen.entity.Product;
import com.naveen.entity.ProductImage;
import com.naveen.repository.CartRepo;
import com.naveen.repository.CartItemRepo;
import com.naveen.repository.ProductRepository;
import com.naveen.dto.CartDto;
import com.naveen.dto.CartItemDto;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepository productRepo;

    // Add product to cart
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() <= 0) {
            throw new IllegalArgumentException("Product is out of stock");
        }

        Cart cart = cartRepo.findByUserId(userId).orElse(new Cart());
        if (cart.getId() == null) { // New cart
            cart.setUserId(userId);
            cart.setTotalAmount(0);
            cart.setCartItems(new ArrayList<>());
            cartRepo.save(cart);
        }

        boolean itemExists = false;
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity > product.getStock()) {
                    throw new IllegalArgumentException(
                        "Total quantity in cart exceeds available stock (" + product.getStock() + ")"
                    );
                }
                item.setQuantity(newQuantity);
                item.setSubtotal(item.getQuantity() * item.getPrice());
                cartItemRepo.save(item);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            if (quantity > product.getStock()) {
                throw new IllegalArgumentException(
                    "Quantity exceeds available stock (" + product.getStock() + ")"
                );
            }

            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            newItem.setSubtotal(quantity * product.getPrice());
            newItem.setCart(cart);
            cartItemRepo.save(newItem);
            cart.getCartItems().add(newItem);
        }

        recalculateTotal(cart);
        return cartRepo.save(cart);
    }

    // Get user's cart
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
    }

    // Update cart item quantity
    public Cart updateCartItem(Long cartItemId, int quantity) {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Product product = item.getProduct();

        if (quantity <= 0) {
            return removeCartItem(cartItemId); // remove if zero or negative
        }

        if (quantity > product.getStock()) {
            throw new IllegalArgumentException(
                "Quantity exceeds available stock (" + product.getStock() + ")"
            );
        }

        item.setQuantity(quantity);
        item.setSubtotal(quantity * item.getPrice());
        cartItemRepo.save(item);

        Cart cart = item.getCart();
        recalculateTotal(cart);
        return cartRepo.save(cart);
    }

    // Remove cart item
    public Cart removeCartItem(Long cartItemId) {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = item.getCart();
        cart.getCartItems().remove(item);
        cartItemRepo.delete(item);

        recalculateTotal(cart);
        return cartRepo.save(cart);
    }

    // Clear entire cart
    public void clearCart(Long userId) {
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepo.deleteAll(cart.getCartItems());
        cart.setCartItems(new ArrayList<>());
        cart.setTotalAmount(0);
        cartRepo.save(cart);
    }

    // Recalculate totalAmount
    private void recalculateTotal(Cart cart) {
        double total = 0;
        for (CartItem ci : cart.getCartItems()) {
            total += ci.getSubtotal();
        }
        cart.setTotalAmount(total);
    }

    // DTO conversion
    public CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());
        cartDto.setTotalAmount(cart.getTotalAmount());

        List<CartItemDto> itemDtos = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setCartItemId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            // Set first image URL if available
            List<ProductImage> images = item.getProduct().getImage();
            itemDto.setProductImage(images.isEmpty() ? null : images.get(0).getUrl());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            itemDto.setSubtotal(item.getSubtotal());
            itemDtos.add(itemDto);
        }

        cartDto.setItems(itemDtos);
        return cartDto;
    }
}
