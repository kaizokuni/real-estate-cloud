package com.realestate.client.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long clientId;
    private Long propertyId;
    private LocalDate date;
    private String status;

    public Reservation() {}

    public Reservation(Long id, Long clientId, Long propertyId, LocalDate date, String status) {
        this.id = id;
        this.clientId = clientId;
        this.propertyId = propertyId;
        this.date = date;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
