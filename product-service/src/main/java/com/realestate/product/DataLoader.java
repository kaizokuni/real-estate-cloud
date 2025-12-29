package com.realestate.product;

import com.realestate.product.entities.Product;
import com.realestate.product.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner start(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Product p1 = new Product();
                p1.setName("iPhone 15 Pro");
                p1.setCategory("Electronics");
                p1.setPrice(1199.00);
                p1.setStockQuantity(50);
                p1.setDescription("Latest Apple smartphone with A17 Pro chip");
                repository.save(p1);

                Product p2 = new Product();
                p2.setName("MacBook Pro M3");
                p2.setCategory("Electronics");
                p2.setPrice(2499.00);
                p2.setStockQuantity(25);
                p2.setDescription("Professional laptop with M3 chip");
                repository.save(p2);

                Product p3 = new Product();
                p3.setName("Sony WH-1000XM5");
                p3.setCategory("Audio");
                p3.setPrice(349.00);
                p3.setStockQuantity(100);
                p3.setDescription("Premium noise-cancelling headphones");
                repository.save(p3);

                System.out.println("✅ Dummy Products Added to Database!");
            }
        };
    }
}
