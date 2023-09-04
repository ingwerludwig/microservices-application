1. Clone this branch
git clone -b containerization https://github.com/ingwerludwig/microservices-application.git

2. Pull All required docker image
docker pull docker.elastic.co/elasticsearch/elasticsearch:5.6.8
docker pull flaviostutz/dynomite:latest
docker pull flaviostutz/conductor-server
docker pull flaviostutz/conductor-ui
docker pull confluentinc/cp-kafka
docker pull confluentinc/cp-zookeeper
docker pull redis
docker pull postgres
docker pull ingwerludwig/auth-service
docker pull ingwerludwig/api-gateway
docker pull ingwerludwig/discovery-server
docker pull ingwerludwig/auth-service
docker pull ingwerludwig/order-service
docker pull ingwerludwig/payment-service
docker pull ingwerludwig/product-service

3. From this repository, make sure this files are available on your local machine
- docker-compose-core-service.yml
- docker-compose-workflow-engine.yml
- core-service-start.sh
- workflow-engine-start.sh

4. Change permission shell file in terminal
chmod +x workflow-engine-start.sh
chmod +x core-service-start.sh

5. Run shell file sequentially
    1. ./core-service-start.sh
    2. ./workflow-engine-start.sh


— Notes : Will taking long in order to wait core services and api gateway (spring boot based) launched completely (around 10-12 mins)
to make every service ready and registered in Netflix Eureka Discovery Server and Spring Cloud Gateway

UI Port
http://localhost:8167 for Checking health and availability of All services
http://localhost:5001 for Using Netflix Conductor UI
