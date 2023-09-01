#!/bin/bash
echo "Creating or connecting to docker network microservice-core-service_default..."
docker network create microservice-core-service_default
echo "Starting Netflix-Conductor Workflow Engine..."
docker-compose -f ./docker-compose-workflow-engine.yml up -d 
echo "All services started."