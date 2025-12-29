# Use a lightweight OpenJDK runtime
FROM eclipse-temurin:17-jdk-alpine

# Add a temporary volume
VOLUME /tmp

# Copy the generated JAR file into the container
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
