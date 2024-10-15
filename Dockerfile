FROM eclipse-temurin:23-jre-alpine

WORKDIR /app

COPY target/tyg-investiment-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
