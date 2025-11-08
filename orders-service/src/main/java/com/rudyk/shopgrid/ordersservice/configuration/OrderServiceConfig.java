package com.rudyk.shopgrid.ordersservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OrderServiceConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
