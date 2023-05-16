package com.javagrind.orderservice.serviceClient.ProductServiceClient;

import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.ProductServiceClient.FindProductByIdRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ProductServiceClientImpl implements ProductServiceClient{
    private final WebClient webClient;

    @Override
    public Response<Object> findProduct(String productId) {
        String requestedUri = "http://localhost:8081/api/product/getProductById";

        try {
            FindProductByIdRequest request = new FindProductByIdRequest(productId);

            ResponseEntity<Response<Object>> responseEntity = webClient.post()
                    .uri(requestedUri)
                    .body(Mono.just(request), FindProductByIdRequest.class)
                    .headers(httpHeaders -> {
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                    })
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<Response<Object>>() {})
                    .block();

            Response<Object> data = responseEntity.getBody();
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