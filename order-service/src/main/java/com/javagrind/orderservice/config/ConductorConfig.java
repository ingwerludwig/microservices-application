package com.javagrind.orderservice.config;

import com.netflix.conductor.client.http.WorkflowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ConductorConfig {
    @Value("${spring.conductor-server-base-url}")
    private String conductorServerUrl;

    @Bean
    public WorkflowClient workflowClient(){
        String conductorServerBaseUrl = conductorServerUrl;
        WorkflowClient workflowClient = new WorkflowClient();
        workflowClient.setRootURI(conductorServerBaseUrl);
        return workflowClient;
    }

    @Bean
    public String workflowBaseUrl(){
        return conductorServerUrl;
    }
}
