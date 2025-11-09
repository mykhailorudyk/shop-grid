package com.rudyk.shopgrid.ordersservice.client.user;

import com.rudyk.shopgrid.common.security.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class UsersServiceClient {

    private final String USERS_SERVICE_URL;
    private final String USERS_BASE_PATH = "/api/v1/users/";

    private final WebClient webClient;
    private final Logger LOGGER = LoggerFactory.getLogger(UsersServiceClient.class);

    public UsersServiceClient(WebClient webClient,
                              @Value("${app.services.users.url}") String usersServiceUrl) {
        this.webClient = webClient;
        this.USERS_SERVICE_URL = usersServiceUrl;
    }

    public boolean checkIfUserExists(String userId) {
        return JwtUtils.findJwtTokenFromContext().map(jwtToken -> {
            String url = USERS_SERVICE_URL + USERS_BASE_PATH + "{id}";
            try {
                webClient.get()
                    .uri(url, userId)
                    .headers(h -> h.setBearerAuth(jwtToken))
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
        }).orElse(false);
    }

}
