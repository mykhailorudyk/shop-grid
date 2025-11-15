package com.rudyk.shopgrid.usersservice.service.impl;

import com.rudyk.shopgrid.usersservice.dto.LoginRequestDto;
import com.rudyk.shopgrid.usersservice.dto.LogoutRequestDto;
import com.rudyk.shopgrid.usersservice.dto.RefreshTokenRequestDto;
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
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final WebClient webClient;

    @Value("${keycloak.login.token-url}")
    private String loginTokenUrl;

    @Value("${keycloak.logout.url}")
    private String logoutUrl;

    @Value("${keycloak.login.client-id}")
    private String clientId;

    @Value("${keycloak.login.client-secret}")
    private String clientSecret;

    @Override
    @CircuitBreaker(name = "keycloak_cb", fallbackMethod = "loginFallback")
    public Object login(LoginRequestDto loginRequestDto) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", loginRequestDto.getUsername());
        formData.add("password", loginRequestDto.getPassword());

        try {
            return webClient.post()
                    .uri(loginTokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(formData)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = "keycloak_cb", fallbackMethod = "loginFallback")
    public Object refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = extractRefreshToken(refreshTokenRequestDto.getRefreshToken());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        try {
            return webClient.post()
                    .uri(loginTokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(formData)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new SecurityException("Invalid refresh token", e);
        }
    }

    @Override
    public void logout(LogoutRequestDto logoutRequestDto) {
        String refreshToken = extractRefreshToken(logoutRequestDto.getRefreshToken());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("token", refreshToken);
        formData.add("token_type_hint", "refresh_token");

        try {
            webClient.post()
                    .uri(logoutUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(formData)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            log.info("Token successfully revoked");
        } catch (WebClientResponseException e) {
            throw new SecurityException("Failed to revoke token (it might be already invalid or expired)", e);
        }
    }

    private static String extractRefreshToken(String refreshTokenInput) {
        String refreshToken = refreshTokenInput;
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        return refreshToken;
    }

    private String loginFallback(LoginRequestDto loginRequestDto, Throwable throwable) {
        log.error("Keycloak is unavailable. Fallback method invoked for user: {}", loginRequestDto.getUsername());
        throw new ServiceUnavailableException("Keycloak is currently unavailable. Please try again later.");
    }

}
