package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.availableQuantity > 0")
    List<Product> findAllAvailable();

    @Query("select p from Product p where p.category.id in :ids and p.availableQuantity > 0")
    List<Product> findAvailableByCategoryIds(@Param("ids") List<Integer> ids);

    List<Product> findByCategoryAndIdNot(Category category, int productId);
}
