package com.rudyk.shopgrid.ordersservice.client.user;

import com.rudyk.shopgrid.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsersServiceClient {

    private final WebClient webClient;
    private final String BASE_RESOURCE_URL = "http://localhost:8090/api/v1/users";
    private final Logger LOGGER = LoggerFactory.getLogger(UsersServiceClient.class);

    public boolean checkIfUserExists(UUID userId) {
        String url = BASE_RESOURCE_URL + "/{id}";
        try {
            Object user = webClient.get()
                    .uri(url, userId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            return Boolean.TRUE;
        } catch (WebClientResponseException.NotFound e) {
            return Boolean.FALSE;
        } catch (Exception e) {
            LOGGER.error("Error occurred while checking if user with id {} exists", userId, e);
            return Boolean.FALSE;
        }
    }

}
