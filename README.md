# About This Backend Microservice Project

This microservices contains golang services and spring boot service. <br>

We use Spring Cloud Technology for API Gateway <br>
And also we use Netflix Technologies : <br>
- Discovery Server using Netflix Eureka <br>
- Worfklow Engine for Orchestrating Microservices using Netflix Conductor <br>
<br>

| Spring Boot Based           | Golang Based|
|-----------------------------|--------------|
| - Discovery Server (use Netflix Eureka)| - Notification Service|
| - API Gateway (use Spring Cloud) | - Invoice Service |
| - AuthService|              |
| - ProductService       |              |
| - OrderService |              |
|- PaymentService |              |

# Tech Stack
* [![Spring][Spring.com]][Spring-url]
* [![Golang][Golang.com]][Golang-url]
* [![Netflix OSS][Netflix.com]][Netflix-url]


# Tools we used
| Storage                                               | Containerization |
|-------------------------------------------------------| ------------- |
| * [![Redis][Redis.com]][Redis-url]                    | * [![Docker][Docker.com]][Docker-url]  |
| * [![Apache Kafka][Apachekafka.com]][Apachekafka-url] | * [![Google Container Tools - Jib][Googlejib.com]][Googlejib-url]  |
| * [![PostgreSQL][Postgre.com]][Postgre-url]           |  |
| * [![MongoDB][Mongo.com]][Mongo-url]                  |  |
| * [![ElasticSearch][ElasticSearch.com]][Dynomite-url]      |  |

# Prerequisite
Minimum RAM required for running All docker container is 5GB
<br>
# Installation
- ### Please check your local port to avoid collisions with this microservices port that consist of [UI PORT](#ui), [Storage PORT](#storage), and [Services PORT](#service) <br>
- ### If you want to develop Spring Application, go from [step 1-7](#step-1) <br>
- ### If you want to running project only with docker container, go from [step 8-10](#step-8) <br> <br>

<a id="step-1"></a>
### 1. Installation Java
   First thing first, install <a href="https://docs.spring.io/spring-boot/docs/1.0.2.RELEASE/reference/html/getting-started-installing-spring-boot.html">SpringBoot</a> and follow those instruction from that documentation
   <br>
### 2. Clone the repo
   ```sh 
   git clone https://github.com/ingwerludwig/microservices-application.git
   ```

### <br> 3. Import Project
   File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   <br><br>
### 4. Only initialize PostgreSQL Database and Redis first (docker-compose v2)
   ```sh
   chmod +x dev-core-service-start.sh
   ```
   ```sh
   ./dev-core-service-start.sh
   ```
### <br> 5. Refresh dependencies
   Right Click project directory -> Maven -> Reload Project
### <br> 6. Setup your env in application.properties
### <br> 7. Start SpringBoot Application Sequentially
   - Discovery-Server
   - APIGateway
   - then other service as your wish <br>
## If you want to running project only with docker container, go from this step

<a id="step-8"></a>
### <br> 8. Pull all required image
   ```sh
   docker pull webhippie/elasticsearch:6.8
   docker pull redis:6.2.3-alpine
   docker pull ingwerludwig/conductor-server:latest
   docker pull ingwerludwig/conductor-ui:latest
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
   ```

### <br> 9. Then modify permission for shell file
   ```sh
   chmod +x workflow-engine-start.sh
   ```
   ```sh
   chmod +x core-service-start.sh
   ```

### <br> 10. After that, run shell file sequentially
   ```sh
   ./core-service-start.sh
   ```
   ```sh
   ./workflow-engine-start.sh
   ```
   ### Notes : 
   Will taking long in order to wait core services and api gateway (spring boot based) launched completely (around 10-12 mins) to make every service ready and registered in Netflix Eureka Discovery Server and Spring Cloud Gateway <br>
   
   ## Please Check the terminal's container when starting because 15 docker container will be burdening your system! <br>

   <a id="ui"></a>
   ### UI Port <br>
   http://localhost:8167 for Using Netflix Eureka Discovery Server to check availability of All services<br>
   http://localhost:5000 for Using Netflix Conductor UI <br>
   http://localhost:8080 for Using Netflix Conductor Server <br>

   <a id="storage"></a>
   ### Storage Port <br>
   :2181 for Apache Zookeeper Confluent<br>
   :9092 for Apache Kafka Confluent<br>
   :5434 for PostgreSQL OrderDB<br>
   :5433 for PostgreSQL AuthDB<br>
   :6379 for Redis Authentication Service<br>
   :7379 for Redis Netflix Conductor Server<br>
   :9200 and :9300 for Elasticsearch<br>

   <a id="service"></a>
   ### Service Port <br>
   :8000 for API Gateway<br>
   :8080 for Netflix Conductor Server<br>
   :8081 for Auth Service<br>
   :8082 for Product Service<br>
   :8083 for Order Service<br>
   :8084 for Payment Service<br>

# Usage
For more examples, please look to the <a href="https://www.postman.com/myprivatepersonal/workspace/team-workspace">documentation</a> and use "dev" environment

# License
Distributed under the MIT License.

## Contact
Ingwer Ludwig - ingwerflash@gmail.com <br>
Farhan Dwyan - dwyanfarhan@gmail.com <br>

Project Link: <a href="https://github.com/ingwerludwig/microservices-application">Click here</a>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/ingwerludwig

[Postgre.com]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[Postgre-url]: https://www.postgresql.org/
[Spring.com]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[Golang.com]: https://img.shields.io/badge/Go-00ADD8?style=for-the-badge&logo=go&logoColor=white
[Golang-url]: https://go.dev
[Docker.com]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com
[Apachekafka.com]: https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka
[Apachekafka-url]: https://kafka.apache.org
[Redis.com]: https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white
[Redis-url]: https://redis.io
[Netflix.com]: https://img.shields.io/badge/Netflix-E50914?style=for-the-badge&logo=netflix&logoColor=white
[Netflix-url]: https://netflix.github.io
[Dynomite.com]: https://img.shields.io/badge/Amazon%20DynamoDB-4053D6?style=for-the-badge&logo=Amazon%20DynamoDB&logoColor=white
[Dynomite-url]: http://www.dynomitedb.com
[Googlejib.com]: https://img.shields.io/badge/google-4285F4?style=for-the-badge&logo=google&logoColor=white
[Googlejib-url]: https://github.com/GoogleContainerTools/jib
[Mongo.com]: https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white
[Mongo-url]: https://www.mongodb.com
[ElasticSearch.com]: https://img.shields.io/badge/-ElasticSearch-005571?style=for-the-badge&logo=elasticsearch
[ElasticSearch-url]: https://www.elastic.co
