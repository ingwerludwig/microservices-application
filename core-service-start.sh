#!/bin/bash
echo "Starting core services..."
docker-compose -f ./docker-compose-core-service.yml up -d zookeeper order-db auth-db redis kafka discovery-server
echo "Starting and registering API Gateway..."
sleep 240
docker-compose -f ./docker-compose-core-service.yml up -d api-gateway
sleep 240
echo "Starting and registering all microservices..."
# Start the remaining services
docker-compose -f ./docker-compose-core-service.yml up -d auth-service product-service order-service payment-service
echo "All services started."