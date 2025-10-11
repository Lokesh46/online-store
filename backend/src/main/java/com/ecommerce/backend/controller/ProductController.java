package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){ this.productService = productService; }

    @GetMapping
    public List<ProductDTO> getProducts(@RequestParam(required = false) Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable int id){
        return productService.getProductById(id);
    }
    @GetMapping("/{id}/related")
    public List<ProductDTO> getRelatedProducts(@PathVariable int id) {
        return productService.getRelatedProducts(id);
    }
}
