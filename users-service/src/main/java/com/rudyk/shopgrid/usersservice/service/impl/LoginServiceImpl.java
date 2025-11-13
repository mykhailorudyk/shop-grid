package com.rudyk.shopgrid.usersservice.service.impl;

import com.rudyk.shopgrid.usersservice.dto.LoginRequestDto;
import com.rudyk.shopgrid.usersservice.service.LoginService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final WebClient webClient;

    @Value("${keycloak.login.token-url}")
    private String tokenUrl;

    @Value("${keycloak.login.client-id}")
    private String clientId;

    @Override
    @CircuitBreaker(name = "keycloak_cb", fallbackMethod = "loginFallback")
    public Object login(LoginRequestDto loginRequestDto) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("username", loginRequestDto.getUsername());
        formData.add("password", loginRequestDto.getPassword());

        try {
            return webClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(formData)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    private String loginFallback(LoginRequestDto loginRequestDto, Throwable throwable) {
        log.error("Keycloak is unavailable. Fallback method invoked for user: {}", loginRequestDto.getUsername());
        throw new ServiceUnavailableException("Keycloak is currently unavailable. Please try again later.");
    }

}
