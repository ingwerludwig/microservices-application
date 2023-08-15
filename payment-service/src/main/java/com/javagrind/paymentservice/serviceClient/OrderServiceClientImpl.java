package com.javagrind.paymentservice.serviceClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.javagrind.paymentservice.dto.MidtransResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransWebhookRequest;
import com.javagrind.paymentservice.dto.Request.OrderServiceClient.UpdateProductRequest;
import com.javagrind.paymentservice.dto.Response;
import com.javagrind.paymentservice.dto.UpdateOrderDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class OrderServiceClientImpl implements OrderServiceClient{

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceClientImpl.class);
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<UpdateOrderResponse> updateOrderWebhook(MidtransWebhookRequest request) throws JsonProcessingException {
        String requestedUri = "http://Product-Service/api/order/update";

        try {
            Integer status_code= Integer.valueOf(request.getStatus_Code());
            if(status_code>=200 && status_code <300) {
                return WebClient.builder()
                    .baseUrl(requestedUri)
                    .build()
                    .post()
                    .uri(requestedUri)
                    .body(BodyInserters.fromValue(request))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .map(this::parseResponse)
                    .doOnSuccess(response -> {
                        LOGGER.info("Updated Order : {}", response);
                    })
                    .doOnError(error ->
                            LOGGER.error("Error calling Update Order API: {}", error.getMessage(), error));
            }
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            JSONObject jsonObject = new JSONObject(ex.getMessage());
            throw new WebClientException("WebClient Fetching Error in " + requestedUri + " API responding with Status Code " + jsonObject.getInt("statusCode")+ " reason is " + jsonObject.getString("message") + " and resulting null data") {
                @Override
                public Throwable getRootCause() {return super.getRootCause();}
            };
        }
        return null;
    }

    private UpdateOrderResponse parseResponse(JsonNode rawResponse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        String id = rawResponse.get("id").asText();
        String productId = rawResponse.get("productId").asText();
        String user_id = rawResponse.get("user_id").asText();
        String productName = rawResponse.get("productName").asText();
        String description = rawResponse.get("description").asText();
        Long amounts = rawResponse.get("amounts").asLong();
        Long price = rawResponse.get("price").asLong();
        String paymentStatus = rawResponse.get("paymentStatus").asText();
        LocalDateTime expiredOn = LocalDateTime.parse(rawResponse.get("expiredOn").asText(), formatter);
        LocalDateTime createdAt = LocalDateTime.parse(rawResponse.get("createdAt").asText(), formatter);
        LocalDateTime updatedAt = LocalDateTime.parse(rawResponse.get("updatedAt").asText(), formatter);

        return new UpdateOrderResponse(id, productId, user_id,productName,description,amounts,price,paymentStatus,expiredOn,createdAt,updatedAt);
    }
}
