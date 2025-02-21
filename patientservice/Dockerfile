FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/patientservice-0.0.0-19.933c4d93.dirty-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]