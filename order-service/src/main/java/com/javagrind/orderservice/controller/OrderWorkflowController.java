package com.javagrind.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.config.ConductorConfig;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.OrderWorkflowRequest;
import com.javagrind.orderservice.dto.request.OrderWorkflowDTO;
import com.javagrind.orderservice.dto.request.StartOrderWorkflowRequest;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderWorkflowController {
    private static final Logger logger = Logger.getLogger(OrderWorkflowController.class.getName());
    private final ConductorConfig conductorConfig;
    private final WorkflowClient workflowClient = conductorConfig.workflowClient();
    private final String workflowBaseUrl = conductorConfig.workflowBaseUrl();

    @PostMapping("/startOrderWorkflow")
    public ResponseEntity<Response<String>> createOrder(@RequestHeader("Authorization") String token, @RequestBody OrderWorkflowDTO request) throws JsonProcessingException, UnknownHostException {
        try{
            Map<String, Object> jsonData = new OrderWorkflowRequest(request,token.substring(7),workflowBaseUrl).getJsonData();
            StartWorkflowRequest workflowRequest = new StartOrderWorkflowRequest(jsonData).getWorkflowRequest();
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