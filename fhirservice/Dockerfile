FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/fhirservice-0.0.0-20.787abb65.dirty-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]