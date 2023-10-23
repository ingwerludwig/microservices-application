#!/bin/bash
echo "Starting Database and Redis..."
docker-compose -f docker-compose-core-service.yml up -d zookeeper order-db auth-db auth-redis kafka
echo "All services started."