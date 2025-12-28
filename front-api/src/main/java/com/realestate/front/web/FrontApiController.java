package com.realestate.front.web;

import com.realestate.front.clients.ClientServiceClient;
import com.realestate.front.clients.EstateClient;
import com.realestate.front.model.Property;
import com.realestate.front.model.Reservation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FrontApiController {

    private final ClientServiceClient clientClient;
    private final EstateClient estateClient;

    public FrontApiController(ClientServiceClient clientClient, EstateClient estateClient) {
        this.clientClient = clientClient;
        this.estateClient = estateClient;
    }

    @GetMapping("/full-reservations")
    public List<Reservation> getFullReservations() {
        // 1. Get recent reservations from Client Service
        List<Reservation> reservations = clientClient.getRecentReservations();

        // 2. Loop through them and fetch Property details for each
        for (Reservation res : reservations) {
            try {
                Property prop = estateClient.getPropertyById(res.getPropertyId());
                res.setPropertyDetails(prop); // Merge the data!
            } catch (Exception e) {
                // Property not found, leave propertyDetails null
                System.out.println("Property " + res.getPropertyId() + " not found: " + e.getMessage());
            }
        }

        return reservations;
    }
}
