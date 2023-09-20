#!/bin/bash
echo "Starting Netflix-Conductor Workflow Engine..."
docker-compose -f ./docker-compose-workflow-engine.yml up -d elasticsearch
sleep 15
docker-compose -f ./docker-compose-workflow-engine.yml up -d conductor-server
sleep 15
docker-compose -f ./docker-compose-workflow-engine.yml up -d conductor-ui
echo "All services started."