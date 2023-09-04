# About This Backend Microservice Project

This microservices contains golang services and spring boot service. <br>

We use Spring Cloud Technology for API Gateway <br>
And also we use Netflix Technologies : <br>
- Discovery Server using Netflix Eureka <br>
- Worfklow Engine for Orchestrating Microservices using Netflix Conductor <br>

## Spring Boot Based
- Discovery Server (use Netflix Eureka) <br>
- API Gateway (use Spring Cloud) <br>
- AuthService <br>
- ProductService <br>
- OrderService <br>
- PaymentService <br>

## Golang Based
- NotificationService <br>
- InvoiceService <br>

# Tech Stack
* [![Spring][Spring.com]][Spring-url]
* [![Golang][Golang.com]][Golang-url]
* [![Netflix OSS][Netflix.com]][Netflix-url]


# Tools we used
| Storage  | Containerization |
| ------------- | ------------- |
| * [![Redis][Redis.com]][Redis-url] | * [![Docker][Docker.com]][Docker-url]  |
| * [![Apache Kafka][Apachekafka.com]][Apachekafka-url]  | * [![Google Container Tools - Jib][Googlejib.com]][Googlejib-url]  |
| * [![PostgreSQL][Postgre.com]][Postgre-url]  |  |
| * [![MongoDB][Mongo.com]][Mongo-url]  |  |
| * [![Dynomite][Dynomite.com]][Dynomite-url] |  |


# Installation
   
1. Clone the repo
   ```sh
   git clone -b containerization https://github.com/ingwerludwig/microservices-application.git
   ```
   
2. Pull all required image
   ```sh
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
   ```

3. Then modify permission for shell file
   ```sh
   chmod +x workflow-engine-start.sh
   ```
   ```sh
   chmod +x core-service-start.sh
   ```

4. After that, run shell file sequentially
   ```sh
   ./core-service-start.sh
   ```
   ```sh
   ./workflow-engine-start.sh
   ```
   Notes : Will taking long in order to wait core services and api gateway (spring boot based) launched completely (around 10-12 mins) <br>
   to make every service ready and registered in Netflix Eureka Discovery Server and Spring Cloud Gateway <br>

   UI Port <br>
   http://localhost:8167 for Checking health and availability of All services <br>
   http://localhost:5001 for Using Netflix Conductor UI <br>


# Usage
For more examples, please look to the <a href="">documentation</a>

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
