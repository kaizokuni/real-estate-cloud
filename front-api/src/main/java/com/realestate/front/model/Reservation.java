package com.realestate.front.model;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Long clientId;
    private Long propertyId;
    private LocalDate date;
    private String status;
    private Property propertyDetails;

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

    public Property getPropertyDetails() { return propertyDetails; }
    public void setPropertyDetails(Property propertyDetails) { this.propertyDetails = propertyDetails; }
}
