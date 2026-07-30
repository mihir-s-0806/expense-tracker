# Step 1: Build stage using Maven and OpenJDK 17
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Runtime stage using lightweight Temurin JRE 17
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/expense-tracker-1.0.0.jar app.jar

# Render assigns dynamic port & PostgreSQL active profile
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=postgres
EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]
