package com.rudyk.shopgrid.ordersservice.client.product;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductsServiceClient {

    public static final String PRODUCTS_SERVICE_URL = "http://localhost:8088/api/v1/products/";
    private final WebClient webClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceClient.class);

    public boolean checkForProductAvailability(UUID productId, Integer quantity) {
        String url = PRODUCTS_SERVICE_URL + "{id}/availability?quantity={quantity}";
        try {
            Boolean isAvailable = webClient.get()
                    .uri(url, productId, quantity)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            return Boolean.TRUE.equals(isAvailable);
        } catch (WebClientResponseException.NotFound e) {
            LOGGER.warn("Product with id {} not found in Products service", productId);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error while checking product availability for product id {}: {}", productId, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
