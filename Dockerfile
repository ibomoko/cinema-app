FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/cinema-app-0.0.1-SNAPSHOT.jar cinema-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "cinema-app.jar"]
