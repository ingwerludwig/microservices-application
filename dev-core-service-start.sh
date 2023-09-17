#!/bin/bash
echo "Starting Database and Redis..."
docker-compose -f docker-compose-core-service.yml up -d zookeeper order-db auth-db redis
sleep 45
docker-compose -f docker-compose-core-service.yml up -d kafka
echo "All services started."