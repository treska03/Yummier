# Stage 1: Build the application
FROM gradle:jdk21-alpine AS builder
WORKDIR /app

# Copy source code and Gradle wrapper
COPY . .

# Ensure the Gradle wrapper is executable and build the jar
RUN chmod +x ./gradlew && \
    ./gradlew bootJar --no-daemon

# Stage 2: Create the runtime image
FROM openjdk:21-slim
WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/build/libs/backend.jar .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar", "--spring.profiles.active=local"]