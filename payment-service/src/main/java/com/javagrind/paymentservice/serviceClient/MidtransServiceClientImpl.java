package com.javagrind.paymentservice.serviceClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.javagrind.paymentservice.dto.MidtransResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MidtransServiceClientImpl {
    private static final Logger LOGGER = LogManager.getLogger(MidtransServiceClientImpl.class);

    @Value("${midtrans.server.key}")
    private String server_key;

    @Value("${midtrans.payment.uri}")
    private String paymentUri;

    public Mono<MidtransResponse> charge(MidtransChargeRequest request) throws Exception {
        String password = null;
        String credentials = server_key + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + encodedCredentials;

        try {
            return WebClient.builder()
                    .baseUrl(paymentUri)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, authHeaderValue)
                    .build()
                    .post()
                    .uri("/")
                    .body(BodyInserters.fromValue(request))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .map(this::parseResponse)
                    .doOnSuccess(response -> {
                        LOGGER.info("Midtrans API response: {}", response);
                    })
                    .doOnError(error -> {
                        LOGGER.error("Error calling Midtrans API: {}", error.getMessage(), error);
                    });
        }catch(Exception ex){
            throw new Exception("Calling Midtrans Payment Failed error " + ex.getMessage() + " caused by : "+ ex.getCause());
        }


    }

    private MidtransResponse parseResponse(JsonNode rawResponse) {
        String orderId = rawResponse.get("order_id").asText();
        String paymentUrl = rawResponse.get("payment_url").asText();

        return new MidtransResponse(orderId, paymentUrl);
    }
}