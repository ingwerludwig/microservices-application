package com.javagrind.orderservice.dto.request;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class StartOrderWorkflowRequest {
    private StartWorkflowRequest workflowRequest=new StartWorkflowRequest();

    public StartWorkflowRequest getWorkflowRequest(Map<String, Object> jsonData){
        this.workflowRequest.setName("OrderWorkflow");
        this.workflowRequest.setCorrelationId("order");
        this.workflowRequest.setVersion(1);
        this.workflowRequest.setInput(jsonData);
        return workflowRequest;
    }

}
