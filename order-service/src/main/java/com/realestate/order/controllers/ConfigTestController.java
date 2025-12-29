package com.realestate.order.controllers;

import com.realestate.order.entities.Order;
import com.realestate.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class ConfigTestController {

    @Value("${mes-config-ms.commandes-last:30}")
    private int daysHistory;

    private final OrderRepository orderRepository;

    public ConfigTestController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/my-config")
    public Map<String, Object> getConfig() {
        return Map.of("commandes-last", daysHistory);
    }

    // New Endpoint: Returns orders from the last X days
    @GetMapping("/orders/recent")
    public List<Order> getRecentOrders() {
        LocalDate cutoffDate = LocalDate.now().minusDays(daysHistory);
        return orderRepository.findByDateAfter(cutoffDate);
    }
}
