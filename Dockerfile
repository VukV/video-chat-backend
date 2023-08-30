# Use the official OpenJDK base image for Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

ARG VERSION=0.0.1-SNAPSHOT
ARG SERVICE_NAME="video-chat-backend"
ARG APP_NAME=$SERVICE_NAME-$VERSION

# Copy the application source code to the container
COPY . .

# Build the application using Gradle
RUN ./gradlew build

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "build/libs/$APP_NAME.jar"]
