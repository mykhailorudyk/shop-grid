package com.rudyk.shopgrid.ordersservice.client.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class ProductsServiceClient {

    public final String PRODUCTS_SERVICE_URL;
    public final String PRODUCTS_BASE_PATH = "/api/v1/products/";

    private final WebClient webClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceClient.class);

    public ProductsServiceClient(WebClient webClient,
                                 @Value("${app.services.products.url}") String productsServiceUrl) {
        this.webClient = webClient;
        this.PRODUCTS_SERVICE_URL = productsServiceUrl;
    }

    public boolean checkForProductAvailability(UUID productId, Integer quantity) {
        String url = PRODUCTS_SERVICE_URL + PRODUCTS_BASE_PATH + "{id}/availability?quantity={quantity}";
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
