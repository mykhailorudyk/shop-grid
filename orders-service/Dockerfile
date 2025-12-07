FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn -pl orders-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY monitoring/otel/opentelemetry-javaagent.jar /app/otel-agent.jar
COPY --from=builder /app/orders-service/target/*.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-javaagent:/app/otel-agent.jar", "-jar", "app.jar"]