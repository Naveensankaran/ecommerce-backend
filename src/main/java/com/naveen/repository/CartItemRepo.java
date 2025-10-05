package com.naveen.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.naveen.entity.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);
}
