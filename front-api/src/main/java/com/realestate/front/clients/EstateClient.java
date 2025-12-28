package com.realestate.front.clients;

import com.realestate.front.model.Property;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// "estate-service" MUST match the name in Eureka
@FeignClient(name = "estate-service")
public interface EstateClient {
    
    @GetMapping("/properties/{id}")
    Property getPropertyById(@PathVariable("id") Long id);
}
