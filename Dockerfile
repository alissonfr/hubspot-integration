# build
FROM maven:3.9.9-amazoncorretto-21-alpine AS builder
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests

# runtime
FROM amazoncorretto:21.0.4-alpine3.18
WORKDIR /app
COPY --from=builder /app/target/hubspotintegration-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
