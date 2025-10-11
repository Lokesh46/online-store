package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CartItemDTO;
import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public CartService(CartRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public CartItemDTO addToCart(Integer userId, Integer productId, Integer quantity) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        Optional<CartItem> existing = cartRepo.findAll().stream()
                .filter(ci -> ci.getUserId().equals(userId)
                        && ci.getProduct().getId().equals(productId))
                .findFirst();

        CartItem item;
        if (existing.isPresent()) {
            item = existing.get();
            int oldQty = item.getQuantity();
            item.setQuantity(item.getQuantity() + quantity);
            product.setAvailableQuantity(product.getAvailableQuantity() - (item.getQuantity() - oldQty));
        } else {
            item = CartItem.builder()
                    .userId(userId)
                    .product(product)
                    .quantity(quantity)
                    .build();
            product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
        }

        productRepo.save(product);
        CartItem saved = cartRepo.save(item);
        return toDto(saved);
    }


    public List<CartItemDTO> getCart(Integer userId) {
        List<CartItem> items = cartRepo.findAll().stream()
                .filter(i -> i.getUserId().equals(userId))
                .toList();
        return items.stream().map(this::toDto).collect(Collectors.toList());
    }


    @Transactional
    public void updateQuantity(Integer userId, Integer productId, Integer quantity) {
        Optional<CartItem> opt = cartRepo.findAll().stream()
                .filter(ci -> ci.getUserId().equals(userId)
                        && ci.getProduct().getId().equals(productId))
                .findFirst();

        if (opt.isPresent()) {
            CartItem ci = opt.get();
            Product product = ci.getProduct();

            if (quantity <= 0) {
                product.setAvailableQuantity(product.getAvailableQuantity() + ci.getQuantity());
                productRepo.save(product);
                cartRepo.delete(ci);
            } else {
                int diff = quantity - ci.getQuantity();
                if (diff > 0) {
                    if (product.getAvailableQuantity() < diff) {
                        throw new RuntimeException("Insufficient stock for " + product.getName());
                    }
                    product.setAvailableQuantity(product.getAvailableQuantity() - diff);
                } else if (diff < 0) {
                    product.setAvailableQuantity(product.getAvailableQuantity() + Math.abs(diff));
                }
                ci.setQuantity(quantity);
                productRepo.save(product);
                cartRepo.save(ci);
            }
        }
    }

    private CartItemDTO toDto(CartItem ci) {
        BigDecimal unit = ci.getProduct().getPrice();
        BigDecimal total = unit.multiply(BigDecimal.valueOf(ci.getQuantity()));
        return new CartItemDTO(
                ci.getId(),
                ci.getUserId(),
                ci.getProduct().getId(),
                ci.getProduct().getName(),
                ci.getQuantity(),
                unit,
                total
        );
    }
}
