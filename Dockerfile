# AS <NAME> to name this stage as maven
FROM maven:3.8.7-openjdk-18
LABEL MAINTAINER="socramteix@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app

# Compile and package the application to an executable JAR
RUN mvn package -Pcontainer

ENTRYPOINT ["java","-jar","target/quotation-management-0.0.1-SNAPSHOT.jar"]
