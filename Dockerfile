FROM openjdk:17-slim

WORKDIR /app

COPY target/fedelabsv4-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
