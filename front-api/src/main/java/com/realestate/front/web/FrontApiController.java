package com.realestate.front.web;

import com.realestate.front.clients.OrderServiceClient;
import com.realestate.front.clients.ProductClient;
import com.realestate.front.model.Order;
import com.realestate.front.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FrontApiController {

    private final OrderServiceClient orderClient;
    private final ProductClient productClient;

    public FrontApiController(OrderServiceClient orderClient, ProductClient productClient) {
        this.orderClient = orderClient;
        this.productClient = productClient;
    }

    @GetMapping("/full-orders")
    public List<Order> getFullOrders() {
        // 1. Fetch recent orders from Order Service
        List<Order> orders = orderClient.getRecentOrders();

        // 2. Enhance each order with Product details from Product Service
        for (Order order : orders) {
            try {
                // Uses productId to find the matching Product
                Product product = productClient.getProductById(order.getProductId());
                order.setProductDetails(product);
            } catch (Exception e) {
                // Graceful fallback if product service is down or product missing
                System.err.println("Product " + order.getProductId() + " not found: " + e.getMessage());
                // productDetails remains null
            }
        }

        return orders;
    }
}
