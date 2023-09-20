package com.javagrind.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.OrderWorkflowRequest;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderWorkflowController {
    private static final Logger logger = Logger.getLogger(OrderWorkflowController.class.getName());

    @Autowired
    private Environment environment;

    @PostMapping("/startOrderWorkflow")
    public ResponseEntity<Response<String>> createOrder(@RequestHeader("Authorization") String token, @RequestBody OrderWorkflowRequest request) throws JsonProcessingException, UnknownHostException {
        String bearerToken = token.substring(7);
        String ipAddress = environment.getProperty("spring.conductor.hostname");

        if(ipAddress.equals("hostname")){
            ipAddress = "192.168.0.137";
        }

        try{
            Map<String, Object> jsonData = new HashMap<>();
            jsonData.put("productId", request.getProductId());
            jsonData.put("userId", request.getUserId());
            jsonData.put("description", request.getDescription());
            jsonData.put("amounts", request.getAmounts());
            jsonData.put("token", bearerToken);
            jsonData.put("baseUrl", ipAddress);

            StartWorkflowRequest workflowRequest = new StartWorkflowRequest();
            workflowRequest.setName("OrderWorkflow");
            workflowRequest.setCorrelationId("order");
            workflowRequest.setVersion(1);
            workflowRequest.setInput(jsonData);

            //Conductor Server Container IP
            String conductorServerBaseUrl = "http://"+ipAddress+":8080/api/";
            WorkflowClient workflowClient = new WorkflowClient();
            workflowClient.setRootURI(conductorServerBaseUrl);

            String resultId = workflowClient.startWorkflow(workflowRequest);

            if(resultId != null){
                Response<String> result = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Workflow Started", resultId);;
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
            Response<String> result = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, "Workflow execution error", null);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            System.err.println("Error : " + e.getMessage() + " caused by : " + e.getCause());
        }

        return null;
    }
}
