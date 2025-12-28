package com.realestate.client.repositories;

import com.realestate.client.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Custom query to find reservations after a certain date
    List<Reservation> findByDateAfter(LocalDate date);
}
