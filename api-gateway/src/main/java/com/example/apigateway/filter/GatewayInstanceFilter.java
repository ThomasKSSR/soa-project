package com.example.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayInstanceFilter implements GlobalFilter {

    @Value("${GATEWAY_ID:unknown}")
    private String gatewayId;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Log which gateway handled the request
        System.out.println("➡️ Request handled by: " + gatewayId);

        // Add response header (VISIBLE IN POSTMAN)
        exchange.getResponse()
                .getHeaders()
                .add("X-Gateway-Instance", gatewayId);

        return chain.filter(exchange);
    }
}

