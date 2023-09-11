#!/bin/bash
echo "Starting Database and Redis..."
docker-compose -f docker-compose-core-service.yml up -d zookeeper order-db auth-db redis kafka
echo "All services started."