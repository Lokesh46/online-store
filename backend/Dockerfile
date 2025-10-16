# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

# Stage 2: Runtime image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Accept secrets from build args
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

# Set env vars for Spring Boot
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
