package com.realestate.client;

import com.realestate.client.entities.Client;
import com.realestate.client.entities.Reservation;
import com.realestate.client.repositories.ClientRepository;
import com.realestate.client.repositories.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataLoader {
    @Bean
    CommandLineRunner start(ClientRepository clientRepo, ReservationRepository resRepo) {
        return args -> {
            if (clientRepo.count() == 0) {
                Client c1 = new Client();
                c1.setName("John Doe");
                c1.setEmail("john@gmail.com");
                c1 = clientRepo.save(c1);
                
                // Old reservation (should be filtered out if days=30)
                Reservation r1 = new Reservation();
                r1.setClientId(c1.getId());
                r1.setPropertyId(1L);
                r1.setDate(LocalDate.now().minusDays(60));
                r1.setStatus("COMPLETED");
                resRepo.save(r1);

                // Recent reservation (should be shown)
                Reservation r2 = new Reservation();
                r2.setClientId(c1.getId());
                r2.setPropertyId(2L);
                r2.setDate(LocalDate.now().minusDays(5));
                r2.setStatus("PENDING");
                resRepo.save(r2);

                System.out.println("✅ Dummy Clients & Reservations Added!");
            }
        };
    }
}
