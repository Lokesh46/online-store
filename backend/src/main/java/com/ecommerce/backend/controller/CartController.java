package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.CartItemDTO;
import com.ecommerce.backend.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public CartItemDTO addToCart(@RequestBody Map<String, Object> body) {
        Integer productId = (Integer)body.get("productId");
        Integer quantity = (Integer)body.get("quantity");
        Integer userId = body.get("userId") == null ? 1 : (Integer) body.get("userId");
        return cartService.addToCart(userId, productId, quantity);
    }

    @GetMapping
    public List<CartItemDTO> getCart(@RequestParam(required = false) Integer userId) {
        if (userId == null) userId = 1;
        return cartService.getCart(userId);
    }

    @PostMapping("/update")
    public void update(@RequestBody Map<String,Object> body) {
        Integer userId = body.get("userId") == null ? 1 : (Integer)body.get("userId");
        Integer productId = (Integer)body.get("productId");
        Integer quantity = (Integer)body.get("quantity");
        cartService.updateQuantity(userId, productId, quantity);
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam Integer productId, @RequestParam(required=false) Integer userId) {
        if (userId == null) userId = 1;
        cartService.updateQuantity(userId, productId, 0); // delete
    }
}
