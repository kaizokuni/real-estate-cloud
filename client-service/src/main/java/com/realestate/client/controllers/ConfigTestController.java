package com.realestate.client.controllers;

import com.realestate.client.entities.Reservation;
import com.realestate.client.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope // Allows updating config without restarting!
public class ConfigTestController {

    @Value("${mes-config-ms.commandes-last}")
    private int daysHistory;

    private final ReservationRepository reservationRepository;

    public ConfigTestController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Endpoint to verify the config is loaded
    @GetMapping("/my-config")
    public Map<String, Object> getConfig() {
        return Map.of("commandes-last", daysHistory);
    }

    // The Logic: Return reservations from the last X days
    @GetMapping("/reservations/recent")
    public List<Reservation> getRecentReservations() {
        LocalDate cutoffDate = LocalDate.now().minusDays(daysHistory);
        return reservationRepository.findByDateAfter(cutoffDate);
    }
}
