package com.example.api_gateway_service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private Environment environment;

    @Autowired
    public AuthorizationHeaderFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String authorizationHeader =
                    request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (!StringUtils.hasText(authorizationHeader)) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            if (!authorizationHeader.startsWith("Bearer ")) {
                return onError(exchange, "Authorization header must start with Bearer", HttpStatus.UNAUTHORIZED);
            }

            String jwt = authorizationHeader.substring(7).trim();

            if (!StringUtils.hasText(jwt)) {
                return onError(exchange, "JWT token is empty", HttpStatus.UNAUTHORIZED);
            }

            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT token is invalid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    //JWT 유효성 체크
    private boolean isJwtValid(String jwt) {
        try {
            String secret = environment.getProperty("token.secret");
            log.info("secret:{}", secret);

            if (!StringUtils.hasText(secret)) {
                log.error("token.secret is not configured");
                return false;
            }

            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            String subject = claims.getSubject();
            return StringUtils.hasText(subject);
        } catch (Exception ex) {
            log.error("JWT validation failed", ex);
            return false;
        }
    }

    // Mono, Flux -> Spring WebFlux
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        //Mono타입으로 전달할 수 있는 함수
        return response.setComplete();
    }
}
