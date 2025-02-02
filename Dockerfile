#Simple without maven package
#FROM openjdk:17-slim
#COPY /target/*.jar /app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]

#ARG DEV=dev
#ARG PROD=prod
#ARG RAILWAY_SERVICE_NAME=dev
#ENV ENVIRONMENT=${ENVIRONMENT}

FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim AS image
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=${RAILWAY_SERVICE_NAME}"]
#ненужна если подключаем dependency docker-compose , или прописываем в environment;