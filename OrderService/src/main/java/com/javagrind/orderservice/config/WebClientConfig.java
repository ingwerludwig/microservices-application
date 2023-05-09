package com.javagrind.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.client.reactive.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public ReactorResourceFactory resourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        return factory;
    }


    @Bean
    public WebClient client(){
        HttpClient.Builder httpClient = HttpClient.newBuilder();

        ClientHttpConnector connector =
                new JdkClientHttpConnector(
                        httpClient.followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20)),
                        new JdkHttpClientResourceFactory()
                );

        WebClient webClient = WebClient.builder().clientConnector(connector).build();
        return webClient;
    }
}
