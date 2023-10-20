#!/bin/bash
echo "Starting Netflix-Conductor Workflow Engine..."
docker-compose -f ./docker-compose-workflow-engine.yml up -d conductor-redis
docker-compose -f ./docker-compose-workflow-engine.yml up -d conductor-elasticsearch
docker-compose -f ./docker-compose-workflow-engine.yml up -d conductor-server
sleep 15
docker-compose restart conductor-server
docker-compose -f ./docker-compose-workflow-engine.yml up -d conductor-ui
echo "All services started."