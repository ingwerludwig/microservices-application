package com.javagrind.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import static com.javagrind.orderservice.handler.WebClientCallExceptionHandler.errorHandler;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient(){
        WebClient.Builder webClientBuilder = WebClient.builder().filter(errorHandler());
        return webClientBuilder;
    }
}
