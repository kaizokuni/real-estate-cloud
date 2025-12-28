package com.realestate.front.clients;

import com.realestate.front.model.Client;
import com.realestate.front.model.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "client-service")
public interface ClientServiceClient {
    
    @GetMapping("/clients/{id}")
    Client getClientById(@PathVariable("id") Long id);

    // Matches the custom query we wrote in Client Service
    @GetMapping("/reservations/recent")
    List<Reservation> getRecentReservations();
}
