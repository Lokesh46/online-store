package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final CategoryService categoryService;
    public ProductService(ProductRepository productRepo, CategoryService categoryService){
        this.productRepo = productRepo; this.categoryService = categoryService;
    }

    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        List<Product> products;
        if (categoryId == null) {
            products = productRepo.findAllAvailable();
        } else {
            List<Integer> ids = categoryService.getDescendantCategoryIds(categoryId);
            products = productRepo.findAvailableByCategoryIds(ids);
        }
        return products.stream().map(p -> new ProductDTO(p.getId(), p.getName(), p.getPrice(), p.getAvailableQuantity(), p.getCategory().getId())).collect(Collectors.toList());
    }

    public List<ProductDTO> getRelatedProducts(int productId) {
        Optional<Product> productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty()) return Collections.emptyList();

        Product product = productOpt.get();
        List<Product> related = productRepo.findByCategoryAndIdNot(product.getCategory(), productId);

        Collections.shuffle(related);
        return related.stream()
                .limit(4)
                .map(p -> new ProductDTO(p.getId(), p.getName(), p.getPrice(), p.getAvailableQuantity(), p.getCategory().getId()))
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(int id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAvailableQuantity(),
                product.getCategory().getId()
        );
    }


}
