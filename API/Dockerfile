# Build stage
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Pass environment variables from Render
ENV SPRING_DATASOURCE_URL=$DB_URL
ENV SPRING_DATASOURCE_USERNAME=$DB_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
