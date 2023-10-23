package com.javagrind.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.OrderWorkflowRequest;
import com.javagrind.orderservice.dto.request.OrderWorkflowDTO;
import com.javagrind.orderservice.dto.request.StartOrderWorkflowRequest;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Data
public class OrderWorkflowController {
    private static final Logger logger = Logger.getLogger(OrderWorkflowController.class.getName());

    @Value("${spring.conductor-server-base-url}")
    private String conductorServerUrl;

    @Value("${spring.service-url}")
    private String serviceUrl;

    @PostMapping("/startOrderWorkflow")
    public ResponseEntity<Response<String>> createOrder(@RequestHeader("Authorization") String token, @RequestBody OrderWorkflowDTO request) throws JsonProcessingException, UnknownHostException {
        try{
            WorkflowClient workflowClient = new WorkflowClient();
            workflowClient.setRootURI(this.getConductorServerUrl());

            Map<String, Object> jsonData = new OrderWorkflowRequest().getJsonData(request,token.substring(7),this.getServiceUrl());
            StartWorkflowRequest workflowRequest = new StartOrderWorkflowRequest().getWorkflowRequest(jsonData);
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