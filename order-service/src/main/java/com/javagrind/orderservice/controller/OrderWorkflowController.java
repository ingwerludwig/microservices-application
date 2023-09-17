package com.javagrind.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javagrind.orderservice.dto.Response;
import com.javagrind.orderservice.dto.request.CreateOrderRequest;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderWorkflowController {
    private static final Logger logger = Logger.getLogger(OrderWorkflowController.class.getName());

    @PostMapping("/startOrderWorkflow")
    public ResponseEntity<Response<String>> createOrder(@Valid @RequestBody CreateOrderRequest request) throws JsonProcessingException {

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("productId", request.getProductId());
        jsonData.put("userId", request.getUserId());
        jsonData.put("description", request.getDescription());
        jsonData.put("amounts", request.getAmounts());

        StartWorkflowRequest workflowRequest = new StartWorkflowRequest();
                workflowRequest.setName("OrderWorkflow");
                workflowRequest.setCorrelationId("order");
                workflowRequest.setVersion(1);
                workflowRequest.setPriority(0);
                workflowRequest.setInput(jsonData);

        String resultId = new WorkflowClient().startWorkflow(workflowRequest);

        if(resultId != null){
            Response<String> result = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Workflow Started", resultId);;
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        Response<String> result = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, "Workflow execution error", null);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
