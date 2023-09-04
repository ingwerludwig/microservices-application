#!/bin/bash
echo "Starting Database and Redis..."
docker-compose -f docker-compose-core-service.yml up -d order-db auth-db redis
echo "All services started."