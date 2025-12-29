package com.realestate.order;

import com.realestate.order.entities.Client;
import com.realestate.order.entities.Order;
import com.realestate.order.repositories.ClientRepository;
import com.realestate.order.repositories.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataLoader {
    @Bean
    CommandLineRunner start(ClientRepository clientRepo, OrderRepository orderRepo) {
        return args -> {
            if (clientRepo.count() == 0) {
                Client c1 = new Client();
                c1.setName("John Doe");
                c1.setEmail("john@gmail.com");
                c1 = clientRepo.save(c1);

                // Order 1: John orders 2x iPhone 15 Pro
                Order o1 = new Order();
                o1.setClientId(c1.getId());
                o1.setProductId(1L);
                o1.setQuantity(2);
                o1.setTotalPrice(2398.00); // 2 x 1199
                o1.setDate(LocalDate.now().minusDays(60));
                o1.setStatus("COMPLETED");
                orderRepo.save(o1);

                // Order 2: John orders 1x MacBook Pro M3
                Order o2 = new Order();
                o2.setClientId(c1.getId());
                o2.setProductId(2L);
                o2.setQuantity(1);
                o2.setTotalPrice(2499.00);
                o2.setDate(LocalDate.now().minusDays(5));
                o2.setStatus("PENDING");
                orderRepo.save(o2);

                System.out.println("✅ Dummy Clients & Orders Added!");
            }
        };
    }
}
