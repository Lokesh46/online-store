package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.backend.entity.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Integer> {
}
