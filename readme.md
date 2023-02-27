# Quotation Management
# Quotation Management
This is a spring-boot based model application. The main objective of this application is store/get
the Stock information from database. Rules:
- Should be developed using Spring Boot. ✔️
- Create a Stock Quote; ✔️
- Read a Stock Quote by stockId ✔️
- Read all Stock QuotesValidate stockId in another application called "stock-manager" before save in DB ✔️
- Implement cache to be checked before validade id in stock-manager ✔️
- Expose one endpoint to accept notification from stock-manager for clean cache ✔️
- Create unit test for any resource needed
    - Create a Stock ✔️
    - Get a Stock by stockId ✔️
    - Get all stocks ✔️
    - Delete cache ✔️


### Technologies, tools and frameworks used in this project:
- IntelliJ IDEA
- Spring initializer
- Database:
  - MySQL for production
  - **H2 for testing purpose**
- Junit with Mockito
- Lambok
- Webflux
- Cache
- **Docker**
- JDK 17
- Maven
- Postman

### Pre-requisites to run:
You will need two images to run the services below which also will be consumed by
  application to be developed.
- MySQL: https://hub.docker.com/_/mysql
- stock-manager: https://hub.docker.com/repository/docker/adautomendes/stock-manager

### How to run
1. docker network create inatel
2. docker container run --name mysql --network=inatel -e
   MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306
   -p 33060:33060 -d mysql
3. docker container run --name stockmanager --network=inatel -p
   8080:8080 -d adautomendes/stock-manager
4. docker build -t qm:latest . --network=inatel
5. docker run --name quotation-management --network=inatel -p 8081:8081 -d qm:latest

