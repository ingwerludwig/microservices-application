package com.javagrind.paymentservice.serviceClient;

import com.javagrind.paymentservice.dto.MidtransResponse;
import com.javagrind.paymentservice.dto.Request.MidtransPaymentRequest.MidtransChargeRequest;
import com.javagrind.paymentservice.dto.Response;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MidtransServiceClientImpl implements MidtransServiceClient {
    private final WebClient.Builder webClientBuilder;

    @Override
    public Response<MidtransResponse> charge(MidtransChargeRequest request ) {
        String requestedUri = "https://api.sandbox.midtrans.com/v1/payment-links";

        try {
            String username = "SB-Mid-server-MBwpRLA628fwii77HUanuvlL";
            String password = "";
            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + encodedCredentials;

            ResponseEntity<Response<MidtransResponse>> responseEntity = webClientBuilder.build().post()
                    .uri(requestedUri)
                    .body(Mono.just(request), MidtransChargeRequest.class)
                    .headers(httpHeaders -> {
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                        httpHeaders.set(HttpHeaders.AUTHORIZATION, authHeaderValue);
                    })
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<Response<MidtransResponse>>() {})
                    .block();

            Response<MidtransResponse> data = responseEntity.getBody();
            System.err.println(data);
            return data;

        }catch(Exception ex){
            System.err.println(ex.getMessage());
            JSONObject jsonObject = new JSONObject(ex.getMessage());
            throw new WebClientException("WebClient Fetching Error in " + requestedUri + " API responding with Status Code " + jsonObject.getInt("statusCode")+ " reason is " + jsonObject.getString("message") + " and resulting null data") {
                @Override
                public Throwable getRootCause() {return super.getRootCause();}
            };
        }
    }
}