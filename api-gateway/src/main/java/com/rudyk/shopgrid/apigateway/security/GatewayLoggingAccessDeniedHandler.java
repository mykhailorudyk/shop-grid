package com.rudyk.shopgrid.apigateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Component
public class GatewayLoggingAccessDeniedHandler implements ServerAccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayLoggingAccessDeniedHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return exchange.getPrincipal()
                .map(Principal::getName)
                .defaultIfEmpty("anonymous")
                .doOnNext(username ->
                        LOGGER.warn("Access Denied (403): User '{}' attempted to access protected URI '{}'",
                                username, exchange.getRequest().getURI()))
                .flatMap(username -> {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return exchange.getResponse().setComplete();
                });
    }
}
