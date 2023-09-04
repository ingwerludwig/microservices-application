#!/bin/bash
echo "Starting Netflix-Conductor Workflow Engine..."
docker-compose -f ./docker-compose-workflow-engine.yml up -d 
echo "All services started."
