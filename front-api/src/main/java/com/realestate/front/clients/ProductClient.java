package com.realestate.front.clients;

import com.realestate.front.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// MUST match the application name in product-service/src/main/resources/application.yml
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
