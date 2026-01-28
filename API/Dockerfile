# Build stage (Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Run stage (Java 21)
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render environment variables
ENV SPRING_DATASOURCE_URL=$DB_URL
ENV SPRING_DATASOURCE_USERNAME=$DB_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
