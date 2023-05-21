package com.javagrind.apigateway.filter;

import com.javagrind.apigateway.dto.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class JwtAuthGatewayFilter extends AbstractGatewayFilterFactory<JwtAuthGatewayFilter.Config> {

    @Autowired
    RouteFilter routeFilter;

    @Autowired
    WebClient.Builder webClientBuilder;

    public JwtAuthGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        String requestedUri = "http://Auth-Service/api/auth/validate";
        WebClient webClient = webClientBuilder.build();

        return (exchange, chain) -> {
            if (routeFilter.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization Header in Gateway Filter");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                String finalAuthHeader = authHeader;
                return webClient.get()
                        .uri(requestedUri)
                        .headers(httpHeaders -> {
                            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                            httpHeaders.setBearerAuth(finalAuthHeader);
                        })
                        .retrieve()
                        .onStatus(status -> status.isError(), response -> {
                            // Handle error status codes
                            // For example, throw an exception or return an error response
                            throw new RuntimeException("Failed token in Authorization Header on Gateway Filter");
                        })
                        .toEntity(new ParameterizedTypeReference<Response<Object>>() {})
                        .then(Mono.fromCallable(() -> exchange))
                        .flatMap(chain::filter);
            } else {
                return chain.filter(exchange);
            }
        };
    }



    static class Config {

    }

}