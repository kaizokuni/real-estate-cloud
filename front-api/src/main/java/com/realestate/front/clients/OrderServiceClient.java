package com.realestate.front.clients;

import com.realestate.front.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

// MUST match the application name in order-service/src/main/resources/application.yml
@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/orders/recent")
    List<Order> getRecentOrders();
}
