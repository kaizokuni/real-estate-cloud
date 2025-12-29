package com.realestate.order.repositories;

import com.realestate.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Custom query to find orders placed after a specific date
    List<Order> findByDateAfter(LocalDate date);
}
