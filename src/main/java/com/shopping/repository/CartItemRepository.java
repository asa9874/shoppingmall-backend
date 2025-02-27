package com.shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
