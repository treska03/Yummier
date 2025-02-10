FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the build output from the host to the container
COPY build/libs/backend.jar backend.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run your application
ENTRYPOINT ["java", "-jar", "backend.jar"]