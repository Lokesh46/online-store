package com.ecommerce.backend.bootstrap;

import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository catRepo;
    private final ProductRepository prodRepo;

    public DataLoader(CategoryRepository catRepo, ProductRepository prodRepo) {
        this.catRepo = catRepo;
        this.prodRepo = prodRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (catRepo.count() > 0 || prodRepo.count() > 0) return; // avoid duplicates on restart

        // ---------------------- CATEGORIES ----------------------
        Category electronics = Category.builder().name("Electronics").build();

        Category computers = Category.builder().name("Computers").parent(electronics).build();
        Category laptops = Category.builder().name("Laptops").parent(computers).build();
        Category gamingLaptops = Category.builder().name("Gaming Laptops").parent(laptops).build();
        Category accessories = Category.builder().name("Accessories").parent(computers).build();

        Category phones = Category.builder().name("Phones").parent(electronics).build();
        Category smartphones = Category.builder().name("Smartphones").parent(phones).build();
        Category featurePhones = Category.builder().name("Feature Phones").parent(phones).build();

        Category tvs = Category.builder().name("Televisions").parent(electronics).build();

        electronics.setChildren(Arrays.asList(computers, phones, tvs));
        computers.setChildren(Arrays.asList(laptops, accessories));
        laptops.setChildren(Collections.singletonList(gamingLaptops));
        phones.setChildren(Arrays.asList(smartphones, featurePhones));

        catRepo.save(electronics);

        // ---------------------- PRODUCTS ----------------------

        // Laptops
        prodRepo.save(Product.builder().name("Dell XPS 13").price(new BigDecimal("999.99")).availableQuantity(5).category(laptops).build());
        prodRepo.save(Product.builder().name("MacBook Pro 16").price(new BigDecimal("2399.99")).availableQuantity(3).category(laptops).build());
        prodRepo.save(Product.builder().name("HP Spectre x360").price(new BigDecimal("1199.99")).availableQuantity(7).category(laptops).build());

        // Gaming Laptops
        prodRepo.save(Product.builder().name("ASUS ROG Strix G15").price(new BigDecimal("1499.99")).availableQuantity(4).category(gamingLaptops).build());
        prodRepo.save(Product.builder().name("MSI Raider GE78").price(new BigDecimal("1899.00")).availableQuantity(2).category(gamingLaptops).build());

        // Accessories
        prodRepo.save(Product.builder().name("Logitech MX Master 3 Mouse").price(new BigDecimal("99.99")).availableQuantity(12).category(accessories).build());
        prodRepo.save(Product.builder().name("Dell USB-C Hub").price(new BigDecimal("59.99")).availableQuantity(15).category(accessories).build());
        prodRepo.save(Product.builder().name("Apple Magic Keyboard").price(new BigDecimal("129.99")).availableQuantity(5).category(accessories).build());

        // Smartphones
        prodRepo.save(Product.builder().name("iPhone 14 Pro").price(new BigDecimal("999.00")).availableQuantity(10).category(smartphones).build());
        prodRepo.save(Product.builder().name("Samsung Galaxy S24 Ultra").price(new BigDecimal("1099.00")).availableQuantity(8).category(smartphones).build());
        prodRepo.save(Product.builder().name("OnePlus 12").price(new BigDecimal("799.00")).availableQuantity(6).category(smartphones).build());

        // Feature Phones
        prodRepo.save(Product.builder().name("Nokia 3310 Classic").price(new BigDecimal("59.99")).availableQuantity(25).category(featurePhones).build());
        prodRepo.save(Product.builder().name("Samsung Guru").price(new BigDecimal("49.99")).availableQuantity(30).category(featurePhones).build());

        // Televisions
        prodRepo.save(Product.builder().name("Sony Bravia 55-inch 4K").price(new BigDecimal("999.00")).availableQuantity(4).category(tvs).build());
        prodRepo.save(Product.builder().name("LG OLED C3 65-inch").price(new BigDecimal("1899.00")).availableQuantity(2).category(tvs).build());
        prodRepo.save(Product.builder().name("Samsung QLED 50-inch").price(new BigDecimal("799.00")).availableQuantity(6).category(tvs).build());

        System.out.println("✅ Sample data successfully loaded into the database!");
    }
}
