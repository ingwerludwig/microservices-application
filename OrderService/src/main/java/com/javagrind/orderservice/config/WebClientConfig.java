package com.javagrind.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import static com.javagrind.orderservice.handler.WebClientCallExceptionHandler.errorHandler;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        WebClient webClient = WebClient.builder().filter(errorHandler()).build();
        return webClient;
    }
}
