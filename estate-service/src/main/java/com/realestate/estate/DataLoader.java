package com.realestate.estate;

import com.realestate.estate.entities.Property;
import com.realestate.estate.repositories.PropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner start(PropertyRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Property p1 = new Property();
                p1.setTitle("Sunset Villa");
                p1.setType("VILLA");
                p1.setPrice(1500000.0);
                p1.setLocation("Casablanca");
                p1.setDescription("Sea view");
                repository.save(p1);

                Property p2 = new Property();
                p2.setTitle("City Apartment");
                p2.setType("APARTMENT");
                p2.setPrice(800000.0);
                p2.setLocation("Rabat");
                p2.setDescription("City center");
                repository.save(p2);

                System.out.println("✅ Dummy Properties Added to Database!");
            }
        };
    }
}
